package com.Billing_system_pahana_edu.controller;

import com.Billing_system_pahana_edu.util.DBUtil;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

import static org.junit.Assert.*;

public class CustomerControllerTest {
    private CustomerController controller;
    private DummyRequest request;
    private DummyResponse response;

    @Before
    public void setUp() {
        controller = new CustomerController();
        request = new DummyRequest();
        response = new DummyResponse();
    }


    private static class DummyRequest extends HttpServletRequestWrapper {
        private final Map<String, String> params = new HashMap<>();

        public DummyRequest() { super(new HttpServletRequestAdapter()); }

        public void setParameter(String key, String value) { params.put(key, value); }

        @Override
        public String getParameter(String name) { return params.get(name); }

        @Override
        public RequestDispatcher getRequestDispatcher(String path) {
            return new RequestDispatcher() {
                @Override public void forward(ServletRequest req, ServletResponse res) {}
                @Override public void include(ServletRequest req, ServletResponse res) {}
            };
        }
    }


    private static class DummyResponse extends HttpServletResponseWrapper {
        public DummyResponse() { super(new HttpServletResponseAdapter()); }
        @Override public PrintWriter getWriter() { return new PrintWriter(System.out); }
    }


    private static class HttpServletRequestAdapter implements HttpServletRequest {
        public Object getAttribute(String name) { return null; }
        public Enumeration<String> getAttributeNames() { return null; }
        public String getCharacterEncoding() { return null; }
        public void setCharacterEncoding(String env) {}
        public int getContentLength() { return 0; }
        public long getContentLengthLong() { return 0; }
        public String getContentType() { return null; }
        public ServletInputStream getInputStream() { return null; }
        public String getParameter(String name) { return null; }
        public Enumeration<String> getParameterNames() { return null; }
        public String[] getParameterValues(String name) { return new String[0]; }
        public Map<String, String[]> getParameterMap() { return null; }
        public String getProtocol() { return null; }
        public String getScheme() { return null; }
        public String getServerName() { return null; }
        public int getServerPort() { return 0; }
        public BufferedReader getReader() { return null; }
        public String getRemoteAddr() { return null; }
        public String getRemoteHost() { return null; }
        public void setAttribute(String name, Object o) {}
        public void removeAttribute(String name) {}
        public Locale getLocale() { return null; }
        public Enumeration<Locale> getLocales() { return null; }
        public boolean isSecure() { return false; }
        public RequestDispatcher getRequestDispatcher(String path) { return null; }
        public String getRealPath(String path) { return null; }
        public int getRemotePort() { return 0; }
        public String getLocalName() { return null; }
        public String getLocalAddr() { return null; }
        public int getLocalPort() { return 0; }
        public ServletContext getServletContext() { return null; }
        public AsyncContext startAsync() { return null; }
        public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) { return null; }
        public boolean isAsyncStarted() { return false; }
        public boolean isAsyncSupported() { return false; }
        public AsyncContext getAsyncContext() { return null; }
        public DispatcherType getDispatcherType() { return null; }
        public String getAuthType() { return null; }
        public Cookie[] getCookies() { return new Cookie[0]; }
        public long getDateHeader(String name) { return 0; }
        public String getHeader(String name) { return null; }
        public Enumeration<String> getHeaders(String name) { return null; }
        public Enumeration<String> getHeaderNames() { return null; }
        public int getIntHeader(String name) { return 0; }
        public String getMethod() { return null; }
        public String getPathInfo() { return null; }
        public String getPathTranslated() { return null; }
        public String getContextPath() { return null; }
        public String getQueryString() { return null; }
        public String getRemoteUser() { return null; }
        public boolean isUserInRole(String role) { return false; }
        public java.security.Principal getUserPrincipal() { return null; }
        public String getRequestedSessionId() { return null; }
        public String getRequestURI() { return null; }
        public StringBuffer getRequestURL() { return null; }
        public String getServletPath() { return null; }
        public HttpSession getSession(boolean create) { return null; }
        public HttpSession getSession() { return null; }

        @Override
        public String changeSessionId() {
            return "";
        }

        public boolean isRequestedSessionIdValid() { return false; }
        public boolean isRequestedSessionIdFromCookie() { return false; }
        public boolean isRequestedSessionIdFromURL() { return false; }
        public boolean isRequestedSessionIdFromUrl() { return false; }
        public boolean authenticate(HttpServletResponse response) { return false; }
        public void login(String username, String password) {}
        public void logout() {}
        public Collection<Part> getParts() { return null; }
        public Part getPart(String name) { return null; }
        public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) { return null; }
    }

    private static class HttpServletResponseAdapter implements HttpServletResponse {
        public void addCookie(Cookie cookie) {}
        public boolean containsHeader(String name) { return false; }
        public String encodeURL(String url) { return null; }
        public String encodeRedirectURL(String url) { return null; }
        public String encodeUrl(String url) { return null; }
        public String encodeRedirectUrl(String url) { return null; }
        public void sendError(int sc, String msg) {}
        public void sendError(int sc) {}
        public void sendRedirect(String location) {}
        public void setDateHeader(String name, long date) {}
        public void addDateHeader(String name, long date) {}
        public void setHeader(String name, String value) {}
        public void addHeader(String name, String value) {}
        public void setIntHeader(String name, int value) {}
        public void addIntHeader(String name, int value) {}
        public void setStatus(int sc) {}
        public void setStatus(int sc, String sm) {}
        public int getStatus() { return 0; }
        public String getHeader(String name) { return null; }
        public Collection<String> getHeaders(String name) { return null; }
        public Collection<String> getHeaderNames() { return null; }
        public String getCharacterEncoding() { return null; }
        public String getContentType() { return null; }
        public ServletOutputStream getOutputStream() { return null; }
        public PrintWriter getWriter() { return new PrintWriter(System.out); }
        public void setCharacterEncoding(String charset) {}
        public void setContentLength(int len) {}
        public void setContentLengthLong(long len) {}
        public void setContentType(String type) {}
        public void setBufferSize(int size) {}
        public int getBufferSize() { return 0; }
        public void flushBuffer() {}
        public void resetBuffer() {}
        public boolean isCommitted() { return false; }
        public void reset() {}
        public void setLocale(Locale loc) {}
        public Locale getLocale() { return null; }
    }


    @Test
    public void testAddCustomerThroughControllerWithDB() throws Exception {
        String suffix = String.valueOf(System.currentTimeMillis() % 10000);
        String email = "test" + suffix + "@example.com";

        // set parameters
        request.setParameter("action", "add");
        request.setParameter("name", "TestCustomer" + suffix);
        request.setParameter("email", email);
        request.setParameter("address", "123 Street");
        request.setParameter("telephone", "0771234" + suffix);

        // call controller
        controller.doPost(request, response);

        // verify in DB
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM customers WHERE email=?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue("Customer should exist in DB", rs.next());
                assertEquals("Email should match", email, rs.getString("email"));
            }
        }

        // cleanup DB
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM customers WHERE email=?")) {
            ps.setString(1, email);
            ps.executeUpdate();
        }
    }
}
