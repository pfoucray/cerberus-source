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

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Level;
import org.cerberus.crud.entity.TestCase;
import org.cerberus.crud.entity.TestCaseStep;
import org.cerberus.crud.service.ITestCaseService;
import org.cerberus.enums.MessageEventEnum;
import org.cerberus.exception.CerberusException;
import org.cerberus.log.MyLogger;
import org.cerberus.crud.service.ITestCaseStepService;
import org.cerberus.util.ParameterParserUtil;
import org.cerberus.util.answer.AnswerItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author bcivel
 */
@WebServlet(name = "GetStepInLibrary", urlPatterns = {"/GetStepInLibrary"})
public class GetStepInLibrary extends HttpServlet {

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
            throws ServletException, IOException, CerberusException {
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
//        String system = policy.sanitize(request.getParameter("system"));
        String system = ParameterParserUtil.parseStringParamAndSanitize(request.getParameter("system"), null);
        String test = policy.sanitize(request.getParameter("test"));
        String testCase = policy.sanitize(request.getParameter("testCase"));
        String withTestCase = policy.sanitize(request.getParameter("withTestCase"));

        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        ITestCaseStepService testCaseStepService = appContext.getBean(ITestCaseStepService.class);
        ITestCaseService testCaseService = appContext.getBean(ITestCaseService.class);

        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            List<TestCaseStep> tcsList;
            if (test.equals("") && testCase.equals("")){
                tcsList = testCaseStepService.getStepLibraryBySystem(system);
            } else if (testCase.equals("")) {
                tcsList = testCaseStepService.getStepLibraryBySystemTest(system, test);
            } else {
                tcsList = testCaseStepService.getStepLibraryBySystemTestTestCase(system, test, testCase);
            }
            for (TestCaseStep list : tcsList) {
                JSONObject tcs = new JSONObject();
                tcs.put("test", list.getTest());
                tcs.put("testCase", list.getTestCase());
                tcs.put("step", list.getStep());
                tcs.put("sort", list.getSort());
                tcs.put("description", list.getDescription());
                if (list.getTestCaseObj()!=null){
                tcs.put("tcdesc", list.getTestCaseObj().getDescription());
                tcs.put("tcapp", list.getTestCaseObj().getApplication());
                }
                array.put(tcs);
            }
            jsonObject.put("testCaseStepList", array);

            response.setContentType("application/json");
            response.getWriter().print(jsonObject.toString());
        } catch (JSONException exception) {
            MyLogger.log(GetTestCaseForTest.class.getName(), Level.WARN, exception.toString());
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
            Logger.getLogger(GetStepInLibrary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
            Logger.getLogger(GetStepInLibrary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

}
