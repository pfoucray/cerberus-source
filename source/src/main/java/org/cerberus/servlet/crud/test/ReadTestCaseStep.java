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
package org.cerberus.servlet.crud.test;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cerberus.engine.entity.MessageEvent;
import org.cerberus.crud.entity.TestCaseStep;
import org.cerberus.crud.entity.TestCaseStepAction;
import org.cerberus.crud.entity.TestCaseStepActionControl;
import org.cerberus.crud.service.ITestCaseStepActionControlService;
import org.cerberus.crud.service.ITestCaseStepActionService;
import org.cerberus.crud.service.ITestCaseStepService;
import org.cerberus.crud.service.impl.TestCaseStepActionControlService;
import org.cerberus.crud.service.impl.TestCaseStepActionService;
import org.cerberus.crud.service.impl.TestCaseStepService;
import org.cerberus.enums.MessageEventEnum;
import org.cerberus.util.ParameterParserUtil;
import org.cerberus.util.answer.AnswerItem;
import org.cerberus.util.answer.AnswerList;
import org.cerberus.util.answer.AnswerUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author FNogueira
 */
public class ReadTestCaseStep extends HttpServlet {

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
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());

        response.setContentType("application/json");
        response.setCharacterEncoding("utf8");

        try {

            JSONObject jsonResponse = new JSONObject();

            AnswerItem answer = new AnswerItem(new MessageEvent(MessageEventEnum.DATA_OPERATION_OK));
            String test = request.getParameter("test");
            String testCase = request.getParameter("testcase");
            int step = Integer.parseInt(request.getParameter("step"));
            boolean getUses = ParameterParserUtil.parseBooleanParam(request.getParameter("getUses"), false);

            if(getUses){
                jsonResponse = getStepUsesByKey(test,testCase,step,appContext,response);
            }else{
                jsonResponse = getStepByKey(test,testCase,step,appContext,response);
            }

            jsonResponse.put("messageType", "OK");
            jsonResponse.put("message", answer.getResultMessage().getDescription());

            response.getWriter().print(jsonResponse.toString());

        } catch (JSONException e) {
            org.apache.log4j.Logger.getLogger(ReadTestCaseStep.class.getName()).log(org.apache.log4j.Level.ERROR, e.getMessage(), e);
            //returns a default error message with the json format that is able to be parsed by the client-side
            response.getWriter().print(AnswerUtil.createGenericErrorAnswer());
        }

    }

    private JSONObject getStepUsesByKey(String test, String testcase, int step, ApplicationContext appContext, HttpServletResponse response) throws JSONException{
        JSONObject jsonResponse = new JSONObject();

        ITestCaseStepService stepService = appContext.getBean(TestCaseStepService.class);

        AnswerList answer = stepService.readByLibraryUsed(test,testcase,step);
        JSONArray res = new JSONArray();

        for(Object obj : answer.getDataList()) {
            TestCaseStep testCaseStep = (TestCaseStep) obj;
            Gson gson = new Gson();
            JSONObject result = new JSONObject(gson.toJson(testCaseStep));
            res.put(result);
        }

        jsonResponse.put("step",res);

        return jsonResponse;
    }

    private JSONObject getStepByKey(String test, String testcase, int step, ApplicationContext appContext, HttpServletResponse response) throws JSONException{
        JSONObject jsonResponse = new JSONObject();

        ITestCaseStepService stepService = appContext.getBean(TestCaseStepService.class);
        ITestCaseStepActionService stepActionService = appContext.getBean(TestCaseStepActionService.class);
        ITestCaseStepActionControlService stepActionControlService = appContext.getBean(TestCaseStepActionControlService.class);

        TestCaseStep testCaseStep = stepService.findTestCaseStep(test, testcase, step);

        Gson gson = new Gson();
        JSONObject result = new JSONObject(gson.toJson(testCaseStep));
        jsonResponse.put("step", result);
        //jsonResponse.put("step", testCaseStep);

        List<TestCaseStepAction> tcsActionList = stepActionService.getListOfAction(test, testcase, step);

        if (tcsActionList != null) {
            JSONArray list = new JSONArray();
            for (TestCaseStepAction t : tcsActionList) {
                JSONObject obj = new JSONObject(gson.toJson(t));
                obj.put("controlList", new JSONArray());
                obj.put("objType", "action");
                list.put(obj);
            }
            jsonResponse.put("tcsActionList", list);
        }

        List<TestCaseStepActionControl> tcsActionControlList = stepActionControlService.findControlByTestTestCaseStep(test, testcase, step);

        if (tcsActionControlList != null) {
            JSONArray list2 = new JSONArray();
            for (TestCaseStepActionControl t : tcsActionControlList) {
                JSONObject obj = new JSONObject(gson.toJson(t));
                list2.put(obj);
            }
            jsonResponse.put("tcsActionControlList", list2);
        }

        return jsonResponse;
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

}
