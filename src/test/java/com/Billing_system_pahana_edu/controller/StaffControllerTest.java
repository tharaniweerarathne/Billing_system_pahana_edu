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

public class StaffControllerTest {
    private StaffController controller;
    private DummyRequest request;
    private DummyResponse response;

    @Before
    public void setUp() {
        controller = new StaffController();
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
    public void testUpdateStaffThroughControllerWithDB() throws Exception {
        String suffix = String.valueOf(System.currentTimeMillis() % 10000);
        String username = "updatestaff" + suffix;
        String email = "updatestaff" + suffix + "@example.com";
        String id = "S" + suffix;


        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO users (id, name, email, username, password, role) VALUES (?, ?, ?, ?, ?, 'Staff')")) {
            ps.setString(1, id);
            ps.setString(2, "OriginalName");
            ps.setString(3, email);
            ps.setString(4, username);
            ps.setString(5, "oldpassword");
            ps.executeUpdate();
        }


        request.setParameter("action", "update");
        request.setParameter("id", id);
        request.setParameter("name", "UpdatedName" + suffix);
        request.setParameter("email", email);
        request.setParameter("username", username);
        request.setParameter("password", "newpassword123");

        controller.doPost(request, response);


        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM users WHERE username=? AND role='Staff'")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue("Staff should exist in DB", rs.next());
                assertEquals("Name should be updated", "UpdatedName" + suffix, rs.getString("name"));
                assertEquals("Password should be updated", "newpassword123", rs.getString("password"));
            }
        }


        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM users WHERE username=? AND role='Staff'")) {
            ps.setString(1, username);
            ps.executeUpdate();
        }
    }

    @Test
    public void testDeleteStaffThroughControllerWithDB() throws Exception {
        String suffix = String.valueOf(System.currentTimeMillis() % 10000);
        String username = "deletestaff" + suffix;
        String email = "deletestaff" + suffix + "@example.com";
        String id = "S" + suffix;


        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO users (id, name, email, username, password, role) VALUES (?, ?, ?, ?, ?, 'Staff')")) {
            ps.setString(1, id);
            ps.setString(2, "ToDeleteStaff");
            ps.setString(3, email);
            ps.setString(4, username);
            ps.setString(5, "password123");
            ps.executeUpdate();
        }


        request.setParameter("action", "delete");
        request.setParameter("id", id);

        controller.doPost(request, response);


        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM users WHERE id=? AND role='Staff'")) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                assertFalse("Staff should not exist in DB after deletion", rs.next());
            }
        }
    }

    @Test
    public void testAddStaffWithInvalidEmail() throws Exception {
        String suffix = String.valueOf(System.currentTimeMillis() % 10000);
        String username = "invalidemailstaff" + suffix;
        String invalidEmail = "invalid-email@invalid.net";
        String id = "S" + suffix;


        request.setParameter("action", "add");
        request.setParameter("id", id);
        request.setParameter("name", "TestStaff" + suffix);
        request.setParameter("email", invalidEmail);
        request.setParameter("username", username);
        request.setParameter("password", "password123");


        controller.doPost(request, response);


        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM users WHERE username=? AND role='Staff'")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                assertFalse("Staff with invalid email should not be added to DB", rs.next());
            }
        }
    }

    @Test
    public void testAddStaffWithDuplicateUsername() throws Exception {
        String suffix = String.valueOf(System.currentTimeMillis() % 10000);
        String username = "duplicatestaff" + suffix;
        String email1 = "staff1" + suffix + "@example.com";
        String email2 = "staff2" + suffix + "@example.com";
        String id1 = "S1" + suffix;
        String id2 = "S2" + suffix;

        // First add a staff member
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO users (id, name, email, username, password, role) VALUES (?, ?, ?, ?, ?, 'Staff')")) {
            ps.setString(1, id1);
            ps.setString(2, "FirstStaff");
            ps.setString(3, email1);
            ps.setString(4, username);
            ps.setString(5, "password123");
            ps.executeUpdate();
        }


        request.setParameter("action", "add");
        request.setParameter("id", id2);
        request.setParameter("name", "SecondStaff");
        request.setParameter("email", email2);
        request.setParameter("username", username);
        request.setParameter("password", "password456");

        controller.doPost(request, response);

        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT COUNT(*) FROM users WHERE username=? AND role='Staff'")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue("Should have result", rs.next());
                assertEquals("Should have only one staff with that username", 1, rs.getInt(1));
            }
        }


        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM users WHERE username=? AND role='Staff'")) {
            ps.setString(1, username);
            ps.executeUpdate();
        }
    }
}