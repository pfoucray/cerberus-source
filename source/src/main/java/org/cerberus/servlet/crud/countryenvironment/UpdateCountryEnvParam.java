/* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cerberus.crud.entity.CountryEnvDeployType;
import org.cerberus.crud.entity.CountryEnvLink;
import org.cerberus.crud.entity.CountryEnvParam;
import org.cerberus.crud.entity.CountryEnvironmentDatabase;
import org.cerberus.crud.entity.CountryEnvironmentParameters;
import org.cerberus.engine.entity.MessageEvent;
import org.cerberus.crud.factory.IFactoryCountryEnvDeployType;
import org.cerberus.crud.factory.IFactoryCountryEnvLink;
import org.cerberus.crud.factory.IFactoryCountryEnvironmentDatabase;
import org.cerberus.crud.factory.IFactoryCountryEnvironmentParameters;
import org.cerberus.crud.service.ICountryEnvDeployTypeService;
import org.cerberus.crud.service.ICountryEnvLinkService;
import org.cerberus.crud.service.ICountryEnvParamService;
import org.cerberus.crud.service.ICountryEnvironmentDatabaseService;
import org.cerberus.crud.service.ICountryEnvironmentParametersService;
import org.cerberus.crud.service.ILogEventService;
import org.cerberus.crud.service.impl.LogEventService;
import org.cerberus.enums.MessageEventEnum;
import org.cerberus.exception.CerberusException;
import org.cerberus.util.ParameterParserUtil;
import org.cerberus.util.StringUtil;
import org.cerberus.util.answer.Answer;
import org.cerberus.util.answer.AnswerItem;
import org.cerberus.util.answer.AnswerUtil;
import org.cerberus.util.servlet.ServletUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author bcivel
 */
@WebServlet(name = "UpdateCountryEnvParam", urlPatterns = {"/UpdateCountryEnvParam"})
public class UpdateCountryEnvParam extends HttpServlet {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(UpdateCountryEnvParam.class);

    private final String OBJECT_NAME = "CountryEnvParam";

    @Autowired
    private ICountryEnvironmentParametersService countryEnvironmentParametersService;

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
            throws ServletException, IOException, CerberusException, JSONException {
        JSONObject jsonResponse = new JSONObject();
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        Answer ans = new Answer();
        MessageEvent msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_UNEXPECTED);
        msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", ""));
        ans.setResultMessage(msg);
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
        String charset = request.getCharacterEncoding();

        ICountryEnvironmentDatabaseService cebService = appContext.getBean(ICountryEnvironmentDatabaseService.class);
        ICountryEnvironmentParametersService ceaService = appContext.getBean(ICountryEnvironmentParametersService.class);
        ICountryEnvDeployTypeService cedService = appContext.getBean(ICountryEnvDeployTypeService.class);
        ICountryEnvLinkService celService = appContext.getBean(ICountryEnvLinkService.class);

        response.setContentType("application/json");

        // Calling Servlet Transversal Util.
        ServletUtil.servletStart(request);

        /**
         * Parsing and securing all required parameters.
         */
        // Parameter that are already controled by GUI (no need to decode) --> We SECURE them
        String system = policy.sanitize(request.getParameter("system"));
        String country = policy.sanitize(request.getParameter("country"));
        String environment = policy.sanitize(request.getParameter("environment"));
        String type = policy.sanitize(request.getParameter("type"));
        String chain = policy.sanitize(request.getParameter("chain"));
        boolean maintenanceAct = ParameterParserUtil.parseBooleanParam(request.getParameter("maintenanceAct"), true);
        // Parameter that needs to be secured --> We SECURE+DECODE them
        String description = ParameterParserUtil.parseStringParamAndDecodeAndSanitize(request.getParameter("description"), "", charset);
        String maintenanceStr = ParameterParserUtil.parseStringParamAndDecodeAndSanitize(request.getParameter("maintenanceStr"), "01:00:00", charset);
        String maintenanceEnd = ParameterParserUtil.parseStringParamAndDecodeAndSanitize(request.getParameter("maintenanceEnd"), "01:00:00", charset);
        // Parameter that we cannot secure as we need the html --> We DECODE them
        String distribList = ParameterParserUtil.parseStringParamAndDecode(request.getParameter("distribList"), "", charset);
        String eMailBodyRevision = ParameterParserUtil.parseStringParamAndDecode(request.getParameter("eMailBodyRevision"), "", charset);
        String eMailBodyChain = ParameterParserUtil.parseStringParamAndDecode(request.getParameter("eMailBodyChain"), "", charset);
        String eMailBodyDisableEnvironment = ParameterParserUtil.parseStringParamAndDecode(request.getParameter("eMailBodyDisableEnvironment"), "", charset);

        // Getting list of database from JSON Call
        JSONArray objDatabaseArray = new JSONArray(request.getParameter("database"));
        List<CountryEnvironmentDatabase> cebList;
        cebList = getCountryEnvironmentDatabaseFromParameter(request, appContext, system, country, environment, objDatabaseArray);

        // Getting list of application from JSON Call
        JSONArray objApplicationArray = new JSONArray(request.getParameter("application"));
        List<CountryEnvironmentParameters> ceaList;
        ceaList = getCountryEnvironmentApplicationFromParameter(request, appContext, system, country, environment, objApplicationArray);

        // Getting list of database from JSON Call
        JSONArray objDeployTypeArray = new JSONArray(request.getParameter("deployType"));
        List<CountryEnvDeployType> cedList;
        cedList = getCountryEnvironmentDeployTypeFromParameter(request, appContext, system, country, environment, objDeployTypeArray);

        // Getting list of database from JSON Call
        JSONArray objDepArray = new JSONArray(request.getParameter("dependencies"));
        List<CountryEnvLink> celList;
        celList = getCountryEnvironmentLinkFromParameter(request, appContext, system, country, environment, objDepArray);

        // Prepare the final answer.
        MessageEvent msg1 = new MessageEvent(MessageEventEnum.GENERIC_OK);
        Answer finalAnswer = new Answer(msg1);

        /**
         * Checking all constrains before calling the services.
         */
        if (StringUtil.isNullOrEmpty(system)) {
            msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_EXPECTED);
            msg.setDescription(msg.getDescription().replace("%ITEM%", OBJECT_NAME)
                    .replace("%OPERATION%", "Update")
                    .replace("%REASON%", "System is missing"));
            ans.setResultMessage(msg);
            finalAnswer = AnswerUtil.agregateAnswer(finalAnswer, (Answer) ans);
        } else if (StringUtil.isNullOrEmpty(country)) {
            msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_EXPECTED);
            msg.setDescription(msg.getDescription().replace("%ITEM%", OBJECT_NAME)
                    .replace("%OPERATION%", "Update")
                    .replace("%REASON%", "Country is missing"));
            ans.setResultMessage(msg);
            finalAnswer = AnswerUtil.agregateAnswer(finalAnswer, (Answer) ans);
        } else if (StringUtil.isNullOrEmpty(environment)) {
            msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_EXPECTED);
            msg.setDescription(msg.getDescription().replace("%ITEM%", OBJECT_NAME)
                    .replace("%OPERATION%", "Update")
                    .replace("%REASON%", "Environment is missing"));
            ans.setResultMessage(msg);
            finalAnswer = AnswerUtil.agregateAnswer(finalAnswer, (Answer) ans);
        } else {
            /**
             * All data seems cleans so we can call the services.
             */
            ICountryEnvParamService cepService = appContext.getBean(ICountryEnvParamService.class);

            AnswerItem resp = cepService.readByKey(system, country, environment);
            if (!(resp.isCodeEquals(MessageEventEnum.DATA_OPERATION_OK.getCode()) && resp.getItem()!=null)) {
                /**
                 * Object could not be found. We stop here and report the error.
                 */
                finalAnswer = AnswerUtil.agregateAnswer(finalAnswer, (Answer) resp);

            } else {
                /**
                 * The service was able to perform the query and confirm the
                 * object exist, then we can update it.
                 */
                CountryEnvParam cepData = (CountryEnvParam) resp.getItem();
                cepData.setDescription(description);
                cepData.setDistribList(distribList);
                cepData.seteMailBodyRevision(eMailBodyRevision);
                cepData.setType(type);
                cepData.seteMailBodyChain(eMailBodyChain);
                cepData.seteMailBodyDisableEnvironment(eMailBodyDisableEnvironment);
                if (request.getParameter("maintenanceAct") != null) {
                    cepData.setMaintenanceAct(maintenanceAct);
                }
                cepData.setMaintenanceStr(maintenanceStr);
                cepData.setMaintenanceEnd(maintenanceEnd);
                cepData.setChain(chain);

                ans = cepService.update(cepData);
                finalAnswer = AnswerUtil.agregateAnswer(finalAnswer, (Answer) ans);

                if (ans.isCodeEquals(MessageEventEnum.DATA_OPERATION_OK.getCode())) {
                    /**
                     * Update was successful. Adding Log entry.
                     */
                    ILogEventService logEventService = appContext.getBean(LogEventService.class);
                    logEventService.createPrivateCalls("/UpdateCountryEnvParam", "UPDATE", "Updated CountryEnvParam : ['" + system + "','" + country + "','" + environment + "']", request);
                }

                // Update the Database with the new list.
                ans = cebService.compareListAndUpdateInsertDeleteElements(system, country, environment, cebList);
                finalAnswer = AnswerUtil.agregateAnswer(finalAnswer, (Answer) ans);
                // Update the Database with the new list.
                ans = ceaService.compareListAndUpdateInsertDeleteElements(system, country, environment, ceaList);
                finalAnswer = AnswerUtil.agregateAnswer(finalAnswer, (Answer) ans);
                // Update the Database with the new list.
                ans = cedService.compareListAndUpdateInsertDeleteElements(system, country, environment, cedList);
                finalAnswer = AnswerUtil.agregateAnswer(finalAnswer, (Answer) ans);
                // Update the Database with the new list.
                ans = celService.compareListAndUpdateInsertDeleteElements(system, country, environment, celList);
                finalAnswer = AnswerUtil.agregateAnswer(finalAnswer, (Answer) ans);

            }
        }

        /**
         * Formating and returning the json result.
         */
        jsonResponse.put("messageType", finalAnswer.getResultMessage().getMessage().getCodeString());
        jsonResponse.put("message", finalAnswer.getResultMessage().getDescription());

        response.getWriter().print(jsonResponse);
        response.getWriter().flush();
    }

    private List<CountryEnvironmentDatabase> getCountryEnvironmentDatabaseFromParameter(HttpServletRequest request, ApplicationContext appContext, String system, String country, String environment, JSONArray json) throws JSONException {
        List<CountryEnvironmentDatabase> cebList = new ArrayList();
        IFactoryCountryEnvironmentDatabase cebFactory = appContext.getBean(IFactoryCountryEnvironmentDatabase.class);

        for (int i = 0; i < json.length(); i++) {
            JSONObject tcsaJson = json.getJSONObject(i);

            boolean delete = tcsaJson.getBoolean("toDelete");
            String database = tcsaJson.getString("database");
            String connectionPool = tcsaJson.getString("connectionPoolName");
            String soapUrl = tcsaJson.getString("soapUrl");
            String csvUrl = tcsaJson.getString("csvUrl");

            if (!delete) {
                CountryEnvironmentDatabase ceb = cebFactory.create(system, country, environment, database, connectionPool, soapUrl, csvUrl);
                cebList.add(ceb);
            }
        }
        return cebList;
    }

    private List<CountryEnvironmentParameters> getCountryEnvironmentApplicationFromParameter(HttpServletRequest request, ApplicationContext appContext, String system, String country, String environment, JSONArray json) throws JSONException {
        List<CountryEnvironmentParameters> ceaList = new ArrayList();
        ICountryEnvironmentParametersService ceaService = appContext.getBean(ICountryEnvironmentParametersService.class);
        IFactoryCountryEnvironmentParameters ceaFactory = appContext.getBean(IFactoryCountryEnvironmentParameters.class);

        for (int i = 0; i < json.length(); i++) {
            JSONObject tcsaJson = json.getJSONObject(i);

            boolean delete = tcsaJson.getBoolean("toDelete");
            String application = tcsaJson.getString("application");
            String ip = tcsaJson.getString("ip");
            String domain = tcsaJson.getString("domain");
            String url = tcsaJson.getString("url");
            String urlLogin = tcsaJson.getString("urlLogin");
            String var1 = tcsaJson.getString("var1");
            String var2 = tcsaJson.getString("var2");
            String var3 = tcsaJson.getString("var3");
            String var4 = tcsaJson.getString("var4");
            String strPoolSize = tcsaJson.getString("poolSize");
            int poolSize;
            if (strPoolSize.isEmpty()) {
                poolSize = ceaService.defaultPoolSize();
            }
            else {
                try {
                    poolSize = Integer.parseInt(strPoolSize);
                } catch (NumberFormatException e) {
                    LOGGER.warn("Unable to parse pool size: " + strPoolSize + ". Applying default value");
                    poolSize = ceaService.defaultPoolSize();
                }
            }

            if (!delete) {
                CountryEnvironmentParameters cea = ceaFactory.create(system, country, environment, application, ip, domain, url, urlLogin, var1, var2, var3, var4, poolSize);
                ceaList.add(cea);
            }
        }
        return ceaList;
    }

    private List<CountryEnvDeployType> getCountryEnvironmentDeployTypeFromParameter(HttpServletRequest request, ApplicationContext appContext, String system, String country, String environment, JSONArray json) throws JSONException {
        List<CountryEnvDeployType> cedList = new ArrayList();
        IFactoryCountryEnvDeployType cedFactory = appContext.getBean(IFactoryCountryEnvDeployType.class);

        for (int i = 0; i < json.length(); i++) {
            JSONObject tcsaJson = json.getJSONObject(i);

            boolean delete = tcsaJson.getBoolean("toDelete");
            String deployType = tcsaJson.getString("deployType");
            String jenkinsAgent = tcsaJson.getString("jenkinsAgent");

            if (!delete) {
                CountryEnvDeployType ced = cedFactory.create(system, country, environment, deployType, jenkinsAgent);
                cedList.add(ced);
            }
        }
        return cedList;
    }

    private List<CountryEnvLink> getCountryEnvironmentLinkFromParameter(HttpServletRequest request, ApplicationContext appContext, String system, String country, String environment, JSONArray json) throws JSONException {
        List<CountryEnvLink> ceiList = new ArrayList();
        IFactoryCountryEnvLink ceiFactory = appContext.getBean(IFactoryCountryEnvLink.class);

        for (int i = 0; i < json.length(); i++) {
            JSONObject tcsaJson = json.getJSONObject(i);

            boolean delete = tcsaJson.getBoolean("toDelete");
            String systemLink = tcsaJson.getString("systemLink");
            String countryLink = tcsaJson.getString("countryLink");
            String environmentLink = tcsaJson.getString("environmentLink");

            if (!delete) {
                CountryEnvLink cei = ceiFactory.create(system, country, environment, systemLink, countryLink, environmentLink);
                ceiList.add(cei);
            }
        }
        return ceiList;
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
            Logger.getLogger(UpdateCountryEnvParam.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(UpdateCountryEnvParam.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UpdateCountryEnvParam.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(UpdateCountryEnvParam.class.getName()).log(Level.SEVERE, null, ex);
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
