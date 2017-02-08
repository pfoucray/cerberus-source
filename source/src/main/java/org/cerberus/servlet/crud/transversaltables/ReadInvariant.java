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
package org.cerberus.servlet.crud.transversaltables;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cerberus.crud.entity.Invariant;
import org.cerberus.engine.entity.MessageEvent;
import org.cerberus.crud.service.IInvariantService;
import org.cerberus.crud.service.impl.InvariantService;
import org.cerberus.enums.MessageEventEnum;
import org.cerberus.exception.CerberusException;
import org.cerberus.util.ParameterParserUtil;
import org.cerberus.util.answer.AnswerItem;
import org.cerberus.util.answer.AnswerList;
import org.cerberus.util.answer.AnswerUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet that handles the requests that need to read values from the invariant
 * table.
 *
 * @author FNogueira
 */
public class ReadInvariant extends HttpServlet {

    private IInvariantService invariantService;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String echo = request.getParameter("sEcho");
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf8");

        /**
         * Parsing and securing all required parameters.
         */
        // Nothing to do here as no parameter to check.
        //ReadInvariant with no id >> List of the invariant
        //ReadInvariant with parameter id (basically the key) >> the invariant needed
        //type=public or private? //TODO?
        try {
            AnswerItem answer;
            JSONObject jsonResponse = new JSONObject();
            String access = request.getParameter("access");
            if (request.getParameter("idName") == null && access != null) {
                if(!Strings.isNullOrEmpty(request.getParameter("columnName"))){
                    answer = findDistinctValuesOfColumn(appContext, request, request.getParameter("columnName"), access);
                    jsonResponse = (JSONObject) answer.getItem();
                }else {
                    answer = findInvariantList(appContext, access, request, response);
                    jsonResponse = (JSONObject) answer.getItem();
                }
            } else if (request.getParameter("value") == null) {
                //loads the list of invariants
                String idName = policy.sanitize(request.getParameter("idName"));
                answer = findInvariantListByIdName(appContext, access, idName);
                jsonResponse = (JSONObject) answer.getItem();
            } else {
                String idName = policy.sanitize(request.getParameter("idName"));
                String value = policy.sanitize(request.getParameter("value"));
                try {
                    answer = findInvariantListBykey(appContext, idName, value);
                    jsonResponse.put("invariant",convertInvariantToJSONObject((Invariant)answer.getItem()));
                }catch(CerberusException e){
                    answer = new AnswerItem();
                    MessageEvent msg = new MessageEvent(MessageEventEnum.ACTION_FAILED);
                    answer.setResultMessage(msg);
                    jsonResponse.put("invariant",convertInvariantToJSONObject((Invariant)answer.getItem()));
                }
            }

            jsonResponse.put("messageType", answer.getResultMessage().getMessage().getCodeString());
            jsonResponse.put("message", answer.getResultMessage().getDescription());
            jsonResponse.put("sEcho", echo); //TODO:FN check if this makes sense

            response.getWriter().print(jsonResponse.toString());

        } catch (JSONException e) {
            org.apache.log4j.Logger.getLogger(ReadInvariant.class.getName()).log(org.apache.log4j.Level.ERROR, null, e);
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
        processRequest(request, response);
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
        processRequest(request, response);
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

    private AnswerItem findInvariantListByIdName(ApplicationContext appContext, String access, String idName) throws JSONException {
        AnswerList answerService;
        AnswerItem answer = new AnswerItem();
        JSONObject object = new JSONObject();

        //finds the list of invariants by idname     
        invariantService = appContext.getBean(InvariantService.class);
        answerService = invariantService.readByIdname(idName);
        JSONArray jsonArray = new JSONArray();

        if (answerService.isCodeEquals(MessageEventEnum.DATA_OPERATION_OK.getCode())) {
            //if the service returns an OK message then we can get the item and convert it to JSONformat
            for (Invariant inv : (List<Invariant>) answerService.getDataList()) {
                jsonArray.put(convertInvariantToJSONObject(inv));
            }
        }

        object.put("contentTable", jsonArray);
        object.put("iTotalRecords", answerService.getTotalRows());
        object.put("iTotalDisplayRecords", answerService.getTotalRows());

        answer.setItem(object);
        answer.setResultMessage(answerService.getResultMessage());

        return answer;
    }

    private AnswerItem findInvariantListBykey(ApplicationContext appContext, String idName, String value) throws JSONException, CerberusException {
        AnswerItem answer = new AnswerItem();

        //finds the list of invariants by idname
        invariantService = appContext.getBean(InvariantService.class);

        answer.setItem(invariantService.findInvariantByIdValue(idName,value));

        MessageEvent msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_OK);
        msg.setDescription(msg.getDescription().replace("%ITEM%", "Invariant").replace("%OPERATION%", "SELECT"));

        answer.setResultMessage(msg);

        return answer;
    }

    private AnswerItem findInvariantList(ApplicationContext appContext, String access, HttpServletRequest request, HttpServletResponse response) throws JSONException {
        AnswerItem item = new AnswerItem();
        JSONObject jsonResponse = new JSONObject();

        invariantService = appContext.getBean(IInvariantService.class);

        int startPosition = Integer.valueOf(ParameterParserUtil.parseStringParam(request.getParameter("iDisplayStart"), "0"));
        int length = Integer.valueOf(ParameterParserUtil.parseStringParam(request.getParameter("iDisplayLength"), "0"));
        /*int sEcho  = Integer.valueOf(request.getParameter("sEcho"));*/

        String searchParameter = ParameterParserUtil.parseStringParam(request.getParameter("sSearch"), "");
        int columnToSortParameter = Integer.parseInt(ParameterParserUtil.parseStringParam(request.getParameter("iSortCol_0"), "0"));
        String sColumns = ParameterParserUtil.parseStringParam(request.getParameter("sColumns"),
                "idname,value,sort,description,VeryShortDesc, gp1,gp2,gp3");
        String columnToSort[] = sColumns.split(",");
        String columnName = columnToSort[columnToSortParameter];
        String sort = ParameterParserUtil.parseStringParam(request.getParameter("sSortDir_0"), "asc");
        AnswerList answerService;

        Map<String, List<String>> individualSearch = new HashMap<String, List<String>>();
        for (int a = 0; a < columnToSort.length; a++) {
            if (null!=request.getParameter("sSearch_" + a) && !request.getParameter("sSearch_" + a).isEmpty()) {
                List<String> search = new ArrayList(Arrays.asList(request.getParameter("sSearch_" + a).split(",")));
                individualSearch.put(columnToSort[a], search);
            }
        }

        if("PUBLIC".equals(access)){
            answerService = invariantService.readByPublicByCriteria(startPosition, length, columnName, sort, searchParameter, individualSearch);
        }else if("PRIVATE".equals(access)){
            answerService = invariantService.readByPrivateByCriteria(startPosition, length, columnName, sort, searchParameter, individualSearch);
        }else{
            answerService = invariantService.readByCriteria(startPosition, length, columnName, sort, searchParameter, "");
        }

        JSONArray jsonArray = new JSONArray();
        //boolean userHasPermissions = request.isUserInRole("Integrator"); //TODO:need to chec
        if (answerService.isCodeEquals(MessageEventEnum.DATA_OPERATION_OK.getCode())) {//the service was able to perform the query, then we should get all values
            for (Invariant inv : (List<Invariant>) answerService.getDataList()) {
                jsonArray.put(convertInvariantToJSONObject(inv));
            }
        }

        //jsonResponse.put("hasPermissions", userHasPermissions);
        jsonResponse.put("contentTable", jsonArray);
        jsonResponse.put("iTotalRecords", answerService.getTotalRows());
        jsonResponse.put("iTotalDisplayRecords", answerService.getTotalRows());

        item.setItem(jsonResponse);
        item.setResultMessage(answerService.getResultMessage());
        return item;

    }

    private AnswerItem findDistinctValuesOfColumn(ApplicationContext appContext, HttpServletRequest request, String columnName, String access) throws JSONException {
        AnswerItem answer = new AnswerItem();
        JSONObject object = new JSONObject();

        invariantService = appContext.getBean(IInvariantService.class);

        String searchParameter = ParameterParserUtil.parseStringParam(request.getParameter("sSearch"), "");
        String sColumns = ParameterParserUtil.parseStringParam(request.getParameter("sColumns"), "idname,value,sort,description,VeryShortDesc, gp1,gp2,gp3");
        String columnToSort[] = sColumns.split(",");
        String column = ParameterParserUtil.parseStringParam(request.getParameter("columnName"), "");
        String sort = ParameterParserUtil.parseStringParam(request.getParameter("sSortDir_0"), "asc");

        Map<String, List<String>> individualSearch = new HashMap<String, List<String>>();
        for (int a = 0; a < columnToSort.length; a++) {
            if (null!=request.getParameter("sSearch_" + a) && !request.getParameter("sSearch_" + a).isEmpty()) {
                List<String> search = new ArrayList(Arrays.asList(request.getParameter("sSearch_" + a).split(",")));
                individualSearch.put(columnToSort[a], search);
            }
        }

        AnswerList applicationList;
        if("PUBLIC".equals(access)){
            applicationList = invariantService.readDistinctValuesByPublicByCriteria(columnName, sort, searchParameter, individualSearch, column);
        }else{
            applicationList = invariantService.readDistinctValuesByPrivateByCriteria(columnName, sort, searchParameter, individualSearch, column);
        }

        object.put("distinctValues", applicationList.getDataList());

        answer.setItem(object);
        answer.setResultMessage(applicationList.getResultMessage());
        return answer;
    }

    private JSONObject convertInvariantToJSONObject(Invariant invariant) throws JSONException {
        Gson gson = new Gson();
        JSONObject result = new JSONObject(gson.toJson(invariant));
        return result;
    }
}
