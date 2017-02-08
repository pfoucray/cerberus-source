/*
 * Cerberus  Copyright (C) 2013  vertigo17
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This file is part of Cerberus.
 *
 * Cerberus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cerberus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Cerberus.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.cerberus.servlet.crud.countryenvironment;

import com.google.gson.Gson;
import org.cerberus.crud.entity.ApplicationObject;
import org.cerberus.crud.service.IApplicationObjectService;
import org.cerberus.engine.entity.MessageEvent;
import org.cerberus.enums.MessageEventEnum;
import org.cerberus.exception.CerberusException;
import org.cerberus.util.ParameterParserUtil;
import org.cerberus.util.answer.AnswerItem;
import org.cerberus.util.answer.AnswerList;
import org.cerberus.util.answer.AnswerUtil;
import org.cerberus.util.servlet.ServletUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 *
 * @author vertigo
 */
@WebServlet(name = "ReadApplicationObject", urlPatterns = {"/ReadApplicationObject"})
public class ReadApplicationObject extends HttpServlet {

    private IApplicationObjectService applicationObjectService;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws CerberusException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, CerberusException {
        String echo = request.getParameter("sEcho");
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf8");

        // Calling Servlet Transversal Util.
        ServletUtil.servletStart(request);

        // Default message to unexpected error.
        MessageEvent msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_UNEXPECTED);
        msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", ""));

        //Get Parameters
        String columnName = ParameterParserUtil.parseStringParam(request.getParameter("columnName"), "");

        /**
         * Parsing and securing all required parameters.
         */
        // Nothing to do here as no parameter to check.
        //
        // Global boolean on the servlet that define if the user has permition to edit and delete object.
        boolean userHasPermissions = request.isUserInRole("Integrator");

        // Init Answer with potencial error from Parsing parameter.
        AnswerItem answer = new AnswerItem(new MessageEvent(MessageEventEnum.DATA_OPERATION_OK));

        try {
            JSONObject jsonResponse = new JSONObject();
            if (request.getParameter("application") != null && request.getParameter("object") != null) {
                answer = findApplicationObject(request.getParameter("application"), request.getParameter("object"), appContext, userHasPermissions, request);
                jsonResponse = (JSONObject) answer.getItem();
            } else if (request.getParameter("id") != null) {
                int id = -1;
                boolean int_error = false;
                try{
                    id = Integer.getInteger(request.getParameter("id"));
                }catch(Exception e){
                    int_error = true;
                }
                if(!int_error){
                    answer = findApplicationObject(id, appContext, userHasPermissions, request);
                    jsonResponse = (JSONObject) answer.getItem();
                }
            } else if (request.getParameter("application") == null) {
                answer = findApplicationObjectList(null, appContext, userHasPermissions, request);
                jsonResponse = (JSONObject) answer.getItem();
            } else if (request.getParameter("iDisplayStart") == null){
                answer = findApplicationObjectList(request.getParameter("application"), appContext, userHasPermissions);
                jsonResponse = (JSONObject) answer.getItem();
            } else {
                answer = findApplicationObjectList(request.getParameter("application"), appContext, userHasPermissions, request);
                jsonResponse = (JSONObject) answer.getItem();
            }

            jsonResponse.put("messageType", answer.getResultMessage().getMessage().getCodeString());
            jsonResponse.put("message", answer.getResultMessage().getDescription());
            jsonResponse.put("sEcho", echo);

            response.getWriter().print(jsonResponse.toString());

        } catch (JSONException e) {
            org.apache.log4j.Logger.getLogger(ReadApplicationObject.class.getName()).log(org.apache.log4j.Level.ERROR, null, e);
            //returns a default error message with the json format that is able to be parsed by the client-side
            response.getWriter().print(AnswerUtil.createGenericErrorAnswer());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (CerberusException ex) {
            Logger.getLogger(ReadApplicationObject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (CerberusException ex) {
            Logger.getLogger(ReadApplicationObject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private AnswerItem findApplicationObjectList(String application, ApplicationContext appContext, boolean userHasPermissions, HttpServletRequest request) throws JSONException {

        AnswerItem item = new AnswerItem();
        JSONObject object = new JSONObject();
        applicationObjectService = appContext.getBean(IApplicationObjectService.class);

        int startPosition = Integer.valueOf(ParameterParserUtil.parseStringParam(request.getParameter("iDisplayStart"), "0"));
        int length = Integer.valueOf(ParameterParserUtil.parseStringParam(request.getParameter("iDisplayLength"), "0"));
        /*int sEcho  = Integer.valueOf(request.getParameter("sEcho"));*/

        String searchParameter = ParameterParserUtil.parseStringParam(request.getParameter("sSearch"), "");
        int columnToSortParameter = Integer.parseInt(ParameterParserUtil.parseStringParam(request.getParameter("iSortCol_0"), "2"));
        String sColumns = ParameterParserUtil.parseStringParam(request.getParameter("sColumns"), "Application,Object");
        String columnToSort[] = sColumns.split(",");
        String columnName = columnToSort[columnToSortParameter];
        String sort = ParameterParserUtil.parseStringParam(request.getParameter("sSortDir_0"), "asc");

        Map<String, List<String>> individualSearch = new HashMap<>();
        for (int a = 0; a < columnToSort.length; a++) {
            if (null != request.getParameter("sSearch_" + a) && !request.getParameter("sSearch_" + a).isEmpty()) {
                List<String> search = new ArrayList(Arrays.asList(request.getParameter("sSearch_" + a).split(",")));
                individualSearch.put(columnToSort[a], search);
            }
        }

        AnswerList resp = applicationObjectService.readByApplicationByCriteria(application, startPosition, length, columnName, sort, searchParameter, individualSearch);

        JSONArray jsonArray = new JSONArray();
        if (resp.isCodeEquals(MessageEventEnum.DATA_OPERATION_OK.getCode())) {//the service was able to perform the query, then we should get all values
            for (ApplicationObject applicationObject : (List<ApplicationObject>) resp.getDataList()) {
                jsonArray.put(convertApplicationObjectToJSONObject(applicationObject));
            }
        }

        object.put("hasPermissions", userHasPermissions);
        object.put("contentTable", jsonArray);
        object.put("iTotalRecords", resp.getTotalRows());
        object.put("iTotalDisplayRecords", resp.getTotalRows());

        item.setItem(object);
        item.setResultMessage(resp.getResultMessage());
        return item;
    }

    private AnswerItem findApplicationObjectList(String application, ApplicationContext appContext, boolean userHasPermissions) throws JSONException {

        AnswerItem item = new AnswerItem();
        JSONObject object = new JSONObject();
        applicationObjectService = appContext.getBean(IApplicationObjectService.class);


        AnswerList resp = applicationObjectService.readByApplication(application);

        JSONArray jsonArray = new JSONArray();
        if (resp.isCodeEquals(MessageEventEnum.DATA_OPERATION_OK.getCode())) {//the service was able to perform the query, then we should get all values
            for (ApplicationObject applicationObject : (List<ApplicationObject>) resp.getDataList()) {
                jsonArray.put(convertApplicationObjectToJSONObject(applicationObject));
            }
        }

        object.put("hasPermissions", userHasPermissions);
        object.put("contentTable", jsonArray);
        object.put("iTotalRecords", resp.getTotalRows());
        object.put("iTotalDisplayRecords", resp.getTotalRows());

        item.setItem(object);
        item.setResultMessage(resp.getResultMessage());
        return item;
    }

    private AnswerItem findApplicationObject(String application, String objecta, ApplicationContext appContext, boolean userHasPermissions, HttpServletRequest request) throws JSONException {

        AnswerItem item = new AnswerItem();
        JSONObject object = new JSONObject();
        applicationObjectService = appContext.getBean(IApplicationObjectService.class);

        AnswerItem resp = applicationObjectService.readByKey(application, objecta);

        JSONObject jsonObject = null;
        if (resp.isCodeEquals(MessageEventEnum.DATA_OPERATION_OK.getCode()) && resp.getItem() != null) {//the service was able to perform the query, then we should get all values
            jsonObject = convertApplicationObjectToJSONObject((ApplicationObject)resp.getItem());
        }

        object.put("hasPermissions", userHasPermissions);
        object.put("contentTable", jsonObject);

        item.setItem(object);
        item.setResultMessage(resp.getResultMessage());
        return item;
    }

    private AnswerItem findApplicationObject(int id, ApplicationContext appContext, boolean userHasPermissions, HttpServletRequest request) throws JSONException {

        AnswerItem item = new AnswerItem();
        JSONObject object = new JSONObject();
        applicationObjectService = appContext.getBean(IApplicationObjectService.class);

        AnswerItem resp = applicationObjectService.readByKey(id);

        JSONObject jsonObject = new JSONObject();
        if (resp.isCodeEquals(MessageEventEnum.DATA_OPERATION_OK.getCode())) {//the service was able to perform the query, then we should get all values
            jsonObject = convertApplicationObjectToJSONObject((ApplicationObject)resp.getItem());
        }

        object.put("hasPermissions", userHasPermissions);
        object.put("contentTable", jsonObject);

        item.setItem(object);
        item.setResultMessage(resp.getResultMessage());
        return item;
    }

    private JSONObject convertApplicationObjectToJSONObject(ApplicationObject application) throws JSONException {

        Gson gson = new Gson();
        JSONObject result = new JSONObject(gson.toJson(application));
        return result;
    }

}
