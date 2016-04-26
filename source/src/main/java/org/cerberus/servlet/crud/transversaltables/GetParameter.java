/**
 * Cerberus  Copyright (C) 2013 - 2016  vertigo17
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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.cerberus.crud.entity.Parameter;
import org.cerberus.exception.CerberusException;
import org.cerberus.log.MyLogger;
import org.cerberus.crud.service.IParameterService;
import org.cerberus.crud.service.impl.ParameterService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author ip100003
 */
@WebServlet(name = "GetParameter", urlPatterns = {"/GetParameter"})
public class GetParameter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String echo = request.getParameter("sEcho");

        String mySystem = request.getParameter("system");
        Logger.getLogger(GetParameter.class.getName()).log(Level.DEBUG, "System : '" + mySystem + "'.");

        JSONArray data = new JSONArray(); //data that will be shown in the table

        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        IParameterService parameterService = appContext.getBean(ParameterService.class);
        try {
            JSONObject jsonResponse = new JSONObject();
            try {
                for (Parameter myParameter : parameterService.findAllParameter()) {
                    JSONArray row = new JSONArray();
                    row.put(myParameter.getParam()).put(myParameter.getValue()).put(myParameter.getValue()).put(myParameter.getDescription());
                    data.put(row);
                }
            } catch (CerberusException ex) {
                response.setContentType("text/html");
                response.getWriter().print(ex.getMessageError().getDescription());
            }
            jsonResponse.put("aaData", data);
            jsonResponse.put("sEcho", echo);
            jsonResponse.put("iTotalRecords", data.length());
            jsonResponse.put("iTotalDisplayRecords", data.length());
            response.setContentType("application/json");
            response.getWriter().print(jsonResponse.toString());
        } catch (JSONException e) {
            MyLogger.log(GetParameter.class.getName(), Level.FATAL, "" + e);
            response.setContentType("text/html");
            response.getWriter().print(e.getMessage());
        }
    }
}
