package org.cerberus.servlet.api;

import org.apache.log4j.Logger;
import org.cerberus.util.StringUtil;
import org.cerberus.util.validity.Validity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Base class for any single point {@link JsonHttpServlet}.
 * <p>
 * A single point {@link JsonHttpServlet} is a specific {@link JsonHttpServlet} that can be only reached through a given {@link HttpMethod}
 *
 * @param <REQUEST>  the request type, according to the associated {@link HttpMethod} (request parameters for {@link HttpMethod#GET}, request body for {@link HttpMethod#POST}, ...)
 * @param <RESPONSE> the response type
 */
public abstract class SinglePointHttpServlet<REQUEST extends Validity, RESPONSE> extends JsonHttpServlet {

    /**
     * Raise when an error occurred during the request parsing
     *
     * @see #parseRequest(HttpServletRequest)
     */
    public static class RequestParsingException extends Exception {

        public RequestParsingException(final String message) {
            super(message);
        }

        public RequestParsingException(final String message, final Throwable cause) {
            super(message, cause);
        }

    }

    /**
     * Raised when an error occurred during request processing
     *
     * @see #processRequest(Validity)
     */
    public static class RequestProcessException extends Exception {

        private HttpStatus statusToReturn;

        public RequestProcessException(final HttpStatus statusToReturn) {
            this.statusToReturn = statusToReturn;
        }

        public RequestProcessException(final HttpStatus statusToReturn, final String message) {
            super(message);
            this.statusToReturn = statusToReturn;
        }

        public RequestProcessException(final HttpStatus statusToReturn, final String message, final Throwable cause) {
            super(message, cause);
            this.statusToReturn = statusToReturn;
        }

        public HttpStatus getStatusToReturn() {
            return statusToReturn;
        }

    }

    /**
     * The associated {@link Logger} to this class
     */
    private static final Logger LOG = Logger.getLogger(SinglePointHttpServlet.class);

    /**
     * Handle the given {@link HttpServletRequest} according to the associated {@link HttpMethod} to this {@link SinglePointHttpServlet}
     * <p>
     * Any {@link SinglePointHttpServlet}'s implementation should call this method during its specific {@link javax.servlet.http.HttpServlet}'s action ({@link javax.servlet.http.HttpServlet#doGet(HttpServletRequest, HttpServletResponse)}, {@link javax.servlet.http.HttpServlet#doPost(HttpServletRequest, HttpServletResponse)}, ...)
     *
     * @param req  the associated {@link HttpServletRequest} to this request
     * @param resp the associated {@link HttpServletResponse} to this request
     * @throws IOException if an I/O error occurred
     */
    protected void handleRequest(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        // First, parse and valid request
        REQUEST request;
        try {
            request = parseRequest(req);
            if (!isRequestValid(request)) {
                throw new RequestParsingException("Invalid request " + request);
            }
        } catch (RequestParsingException e) {
            handleRequestParsingError(e, req, resp);
            return;
        }

        // Then, process request
        try {
            final RESPONSE response = processRequest(request);
            resp.getWriter().print(getObjectMapper().writeValueAsString(response));
            resp.getWriter().flush();
        } catch (final RequestProcessException e) {
            handleRequestProcessError(e, req, resp);
        } catch (final Exception e) {
            LOG.warn("Handle unexpected exception", e);
            handleRequestProcessError(new RequestProcessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e), req, resp);
        }
    }

    /**
     * Get the associated {@link HttpMethod} to this {@link SinglePointHttpServlet}
     *
     * @return the associated {@link HttpMethod} to this {@link SinglePointHttpServlet}
     */
    protected abstract HttpMethod getHttpMethod();

    /**
     * Effectively parse the given {@link HttpServletRequest}
     *
     * @param req the {@link HttpServletRequest} to parse
     * @return the REQUEST type underlying the given {@link HttpServletRequest}
     * @throws RequestParsingException if an error occurred during request parsing
     */
    protected abstract REQUEST parseRequest(final HttpServletRequest req) throws RequestParsingException;

    /**
     * Effectively process the given REQUEST
     *
     * @param request the REQUEST to parse
     * @return the RESPONSE if request has been successfully processed
     * @throws RequestProcessException if an error occurred during request processing
     */
    protected abstract RESPONSE processRequest(final REQUEST request) throws RequestProcessException;

    /**
     * Get the usage description of this {@link SinglePointHttpServlet}
     *
     * @return the usage description of this {@link SinglePointHttpServlet}
     */
    protected abstract String getUsageDescription();

    private void handleRequestParsingError(final RequestParsingException e, final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpStatus.BAD_REQUEST.value());
        if (!StringUtil.isNullOrEmpty(getUsageDescription())) {
            resp.getWriter().print(getUsageDescription());
        }
        resp.getWriter().flush();
    }

    private void handleRequestProcessError(final RequestProcessException e, final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        resp.setStatus(e.getStatusToReturn().value());
        if (!StringUtil.isNullOrEmpty(e.getMessage())) {
            resp.getWriter().print(e.getMessage());
        }
        resp.getWriter().flush();
    }

    private boolean isRequestValid(REQUEST request) {
        return request != null && request.isValid();
    }

}
