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
package org.cerberus.servlet.crud.usermanagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cerberus.crud.entity.User;
import org.cerberus.crud.service.ILogEventService;
import org.cerberus.crud.service.IParameterService;
import org.cerberus.crud.service.IUserService;
import org.cerberus.crud.service.impl.ParameterService;
import org.cerberus.crud.service.impl.UserService;
import org.cerberus.exception.CerberusException;
import org.cerberus.service.email.IEmailGeneration;
import org.cerberus.service.email.impl.EmailGeneration;
import org.cerberus.util.ParameterParserUtil;
import org.cerberus.util.answer.Answer;
import org.cerberus.util.answer.AnswerItem;
import org.cerberus.util.answer.AnswerUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author bcivel
 */
public class ForgotPassword extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
            IUserService userService = appContext.getBean(UserService.class);
            IEmailGeneration generateEmailService = appContext.getBean(EmailGeneration.class);
            IParameterService parameterService = appContext.getBean(ParameterService.class);
            String system = "";
            JSONObject jsonResponse = new JSONObject();

            String login = ParameterParserUtil.parseStringParam(request.getParameter("login"), "");

            /**
             * Check if notification parameter is set to Y. If not, return an
             * error
             */
            String sendNotification = parameterService.findParameterByKey("cerberus_notification_accountcreation_activatenotification", system).getValue();

            if (!sendNotification.equalsIgnoreCase("Y")) {
                jsonResponse.put("messageType", "Error");
                jsonResponse.put("message", "This functionality is not activated. Please contact your Cerberus Administrator.");
                response.getWriter().print(jsonResponse);
                response.getWriter().flush();
                return;
            }

            /**
             * If email not found in database, send error message
             */
            AnswerItem ai = userService.readByKey(login);
            User user = (User) ai.getItem();

            if (user == null) {
                jsonResponse.put("messageType", "Error");
                jsonResponse.put("message", "Login submitted is unknown !");
                response.getWriter().print(jsonResponse);
                response.getWriter().flush();
                return;
            }

            /**
             * Update user setting a new value in requestresetpassword
             */
            userService.requestResetPassword(user);

            /**
             * Send an email with the hash as a parameter
             */
            Answer mailSent = generateEmailService.SendForgotPasswordNotification(user);

            if (!mailSent.isCodeStringEquals("OK")) {
                jsonResponse.put("messageType", "Error");
                jsonResponse.put("message", "An error occured sending the notification. Detail : " + mailSent.getMessageDescription());
                response.getWriter().print(jsonResponse);
                response.getWriter().flush();
                return;
            }

            /**
             * Adding Log entry.
             */
            ILogEventService logEventService = appContext.getBean(ILogEventService.class);
            logEventService.createPrivateCalls("/ForgotPassword", "CREATE", "User : " + login + " asked for password recovery", request);

            /**
             * Build Response Message
             */
            jsonResponse.put("messageType", "OK");
            jsonResponse.put("message", "An e-mail has been sent to the mailbox " + user.getEmail() + ".");
            response.getWriter().print(jsonResponse);
            response.getWriter().flush();
        } catch (CerberusException myexception) {
            response.getWriter().print(myexception.getMessageError().getDescription());
        } catch (JSONException ex) {
            Logger.getLogger(ForgotPassword.class.getName()).log(Level.SEVERE, null, ex);
            response.setContentType("application/json");
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

}
