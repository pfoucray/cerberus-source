package org.cerberus.servlet.engine.threadpool;

import org.cerberus.crud.entity.CountryEnvironmentParameters;
import org.cerberus.engine.entity.threadpool.ExecutionWorkerThread;
import org.cerberus.engine.entity.threadpool.ManageableThreadPoolExecutor;
import org.cerberus.engine.threadpool.IExecutionThreadPoolService;
import org.cerberus.servlet.api.PostableHttpServlet;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.List;
import java.util.Map;

/**
 * @author abourdon
 */
@WebServlet(name = "ReadExecutionPool", urlPatterns = {"/ReadExecutionPool"})
public class ReadExecutionPool extends PostableHttpServlet<CountryEnvironmentParameters.Key, Map<ManageableThreadPoolExecutor.TaskState, List<ExecutionWorkerThread>>> {

    private IExecutionThreadPoolService executionThreadPoolService;

    @Override
    public void init() throws ServletException {
        super.init();
        executionThreadPoolService = WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean(IExecutionThreadPoolService.class);
    }

    @Override
    protected Map<ManageableThreadPoolExecutor.TaskState, List<ExecutionWorkerThread>> processRequest(final CountryEnvironmentParameters.Key key) throws RequestProcessException {
        final Map<ManageableThreadPoolExecutor.TaskState, List<ExecutionWorkerThread>> tasks = executionThreadPoolService.getTasks(key);
        if (tasks == null) {
            throw new RequestProcessException(HttpStatus.NOT_FOUND);
        }
        return tasks;
    }

    @Override
    protected String getUsageDescription() {
        // TODO describe the Json object structure
        return "Need to have the thread pool key from which read information";
    }

    @Override
    protected Class<CountryEnvironmentParameters.Key> getRequestType() {
        return CountryEnvironmentParameters.Key.class;
    }

}
