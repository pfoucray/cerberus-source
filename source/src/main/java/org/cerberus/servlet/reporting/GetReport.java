package org.cerberus.servlet.reporting;

import org.cerberus.entity.TCase;
import org.cerberus.entity.TestCaseExecution;
import org.cerberus.factory.IFactoryTCase;
import org.cerberus.factory.impl.FactoryTCase;
import org.cerberus.service.ITestCaseExecutionService;
import org.cerberus.service.ITestCaseService;
import org.cerberus.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "GetReport", urlPatterns = {"/GetReport"})
public class GetReport extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        ITestCaseService testCaseService = applicationContext.getBean(ITestCaseService.class);
        ITestCaseExecutionService testCaseExecutionService = applicationContext.getBean(ITestCaseExecutionService.class);

        TCase tCase2 = this.getTestCaseFromRequest(req);

        TCase tCase = new TCase();
        tCase.setGroup("AUTOMATED");
        tCase.setApplication("VCCRM");
        tCase.setStatus("WORKING");
        tCase.setPriority(-1);
        //TODO only keep the last parameter
        String environment = this.getValue(req, "Environment");
        //TODO only keep the last parameter
        String build = this.getValue(req, "Build");
        //TODO only keep the last parameter
        String revision = this.getValue(req, "Revision");
        String ip = this.getValue(req, "Ip");
        String port = this.getValue(req, "Port");
        String tag = this.getValue(req, "Tag");
        String browserVersion = this.getValue(req, "BrowserFullVersion");
        List<TCase> list = testCaseService.findTestCaseByAllCriteria(tCase, "", "VC");

        JSONArray data = new JSONArray();
        try {

            for (TCase tc : list) {
                JSONArray object = new JSONArray();
                object.put(tc.getTest());
                object.put(tc.getTestCase());
                object.put(tc.getApplication());
                object.put(tc.getShortDescription());
                object.put(tc.getPriority());
                object.put(tc.getStatus());
                for (String country : req.getParameterValues("Country[]")) {
                    for (String browser : req.getParameterValues("Browser[]")) {
                        TestCaseExecution tce = testCaseExecutionService.findLastTCExecutionByCriteria(tc.getTest(), tc.getTestCase(),
                                environment, country, build, revision, browser, browserVersion, ip, port, tag);
                        if (tce != null) {
                            object.put(tce.getControlStatus());
                            Date date = new Date(tce.getStart());
                            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            object.put(formatter.format(date));
                        } else {
                            object.put("");
                            object.put("");
                        }
                    }
                }
                object.put(tc.getComment());
                object.put("for BUILD/REV");
                object.put(tc.getGroup());

                data.put(object);

            }

            JSONObject json = new JSONObject();

            json.put("aaData", data);
            json.put("iTotalRecords", data.length());
            json.put("iTotalDisplayRecords", data.length());
            resp.setContentType("application/json");
            resp.getWriter().print(json.toString());

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private TCase getTestCaseFromRequest(HttpServletRequest req) {
        String test = this.getValues(req, "Test");
        String project = this.getValues(req, "Project");
        String application = this.getValues(req, "Application");
        String active = this.getValues(req, "TcActive");
        //TODO only keep the last parameter
        int priority = -1;
        String temp= req.getParameter("Priority");
        if (temp != null && !temp.equalsIgnoreCase("All") && StringUtil.isNumeric(temp)) {
            priority = Integer.parseInt(temp);
        }
        String status = this.getValues(req, "Status");
        String group = this.getValues(req, "Group");
        String targetBuild = this.getValues(req, "TargetBuild");
        String targetRev = this.getValues(req, "TargetRev");
        String creator = this.getValues(req, "Creator");
        String implementer = this.getValues(req, "Implementer");
        String comment = this.getValue(req, "Comment");

        IFactoryTCase factoryTCase = new FactoryTCase();
        return factoryTCase.create(test, null, null, null, creator, implementer, null, project, null, null, application, null, null, null, priority, group,
                status, null, null, null, active, null, null, null, null, null, null, targetBuild, targetRev, comment, null, null, null, null);
    }

    private String getValue(HttpServletRequest req, String valueName) {
        String value = null;
        if (req.getParameter(valueName) != null && !req.getParameter(valueName).equalsIgnoreCase("All")) {
            value = req.getParameter(valueName+"[]");
        }
        return value;
    }

    private String getValues(HttpServletRequest req, String valueName) {
        StringBuilder whereClause = new StringBuilder();
        String[] values = req.getParameterValues(valueName+"[]");

        if (values != null) {
            if (values.length == 1) {
                if (!"All".equalsIgnoreCase(values[0]) && !"".equalsIgnoreCase(values[0].trim())) {
                    whereClause.append("'").append(values[0]).append("'");
                }
            } else {
                whereClause.append(" ( '").append(values[0]);
                for (int i = 1; i < values.length ; i++) {
                    whereClause.append("', '").append(values[i]);
                }
                whereClause.append("' ) ");
            }
            return whereClause.toString();
        }
        return null;
    }
}
