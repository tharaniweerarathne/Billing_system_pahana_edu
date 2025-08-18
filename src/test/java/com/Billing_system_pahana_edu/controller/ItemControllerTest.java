package com.Billing_system_pahana_edu.controller;

import com.Billing_system_pahana_edu.model.Item;
import com.Billing_system_pahana_edu.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ItemControllerTest {

    private ItemController controller;
    private DummyRequest request;
    private DummyResponse response;
    private DummySession session;

    private final String TEST_ITEM_ID = "IT999";

    @Before
    public void setUp() throws Exception {
        controller = new ItemController();
        session = new DummySession();
        request = new DummyRequest(session);
        response = new DummyResponse();

        // Insert a test item for update/delete tests
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO items(itemId,itemName,category,price,unit) VALUES(?,?,?,?,?)")) {
            ps.setString(1, TEST_ITEM_ID);
            ps.setString(2, "TestItem");
            ps.setString(3, "TestCategory");
            ps.setInt(4, 100);
            ps.setInt(5, 10);
            ps.executeUpdate();
        }
    }

    @After
    public void tearDown() throws Exception {
        // Remove test items from DB
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM items WHERE itemId=? OR itemName LIKE 'NewTestItem%'")) {
            ps.setString(1, TEST_ITEM_ID);
            ps.executeUpdate();
        }
    }



    @Test
    public void testUpdateItem() throws Exception {
        request.setParameter("action", "update");
        request.setParameter("itemId", TEST_ITEM_ID);
        request.setParameter("itemName", "UpdatedItem");
        request.setParameter("category", "UpdatedCategory");
        request.setParameter("price", "999");
        request.setParameter("unit", "99");

        session.setAttribute("role", "Admin");
        controller.doPost(request, response);

        // Verify DB
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM items WHERE itemId=?")) {
            ps.setString(1, TEST_ITEM_ID);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next());
                assertEquals("UpdatedItem", rs.getString("itemName"));
                assertEquals("UpdatedCategory", rs.getString("category"));
                assertEquals(999, rs.getInt("price"));
                assertEquals(99, rs.getInt("unit"));
            }
        }
    }

    @Test
    public void testDeleteItem() throws Exception {
        request.setParameter("action", "delete");
        request.setParameter("itemId", TEST_ITEM_ID);

        session.setAttribute("role", "Admin");
        controller.doPost(request, response);

        // Verify deleted
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM items WHERE itemId=?")) {
            ps.setString(1, TEST_ITEM_ID);
            try (ResultSet rs = ps.executeQuery()) {
                assertFalse(rs.next());
            }
        }
    }

    // ======= Dummy classes =======

    private static class DummyRequest implements HttpServletRequest {
        private final Map<String, String> params = new HashMap<>();
        private final HttpSession session;

        public DummyRequest(HttpSession session) {
            this.session = session;
        }

        public void setParameter(String key, String value) {
            params.put(key, value);
        }

        @Override
        public String getParameter(String name) {
            return params.get(name);
        }

        @Override
        public HttpSession getSession() {
            return session;
        }

        @Override
        public String changeSessionId() {
            return "";
        }

        @Override
        public HttpSession getSession(boolean create) {
            return session;
        }

        @Override
        public RequestDispatcher getRequestDispatcher(String path) {
            return new RequestDispatcher() {
                public void forward(ServletRequest req, ServletResponse res) {}
                public void include(ServletRequest req, ServletResponse res) {}
            };
        }

        @Override
        public String getRealPath(String s) {
            return "";
        }

        // Required HttpServletRequest method implementations (minimal)
        @Override public String getAuthType() { return null; }
        @Override public Cookie[] getCookies() { return new Cookie[0]; }
        @Override public long getDateHeader(String name) { return -1; }
        @Override public String getHeader(String name) { return null; }
        @Override public Enumeration<String> getHeaders(String name) { return null; }
        @Override public Enumeration<String> getHeaderNames() { return null; }
        @Override public int getIntHeader(String name) { return -1; }
        @Override public String getMethod() { return "POST"; }
        @Override public String getPathInfo() { return null; }
        @Override public String getPathTranslated() { return null; }
        @Override public String getContextPath() { return ""; }
        @Override public String getQueryString() { return null; }
        @Override public String getRemoteUser() { return null; }
        @Override public boolean isUserInRole(String role) { return false; }
        @Override public java.security.Principal getUserPrincipal() { return null; }
        @Override public String getRequestedSessionId() { return null; }
        @Override public String getRequestURI() { return ""; }
        @Override public StringBuffer getRequestURL() { return new StringBuffer(); }
        @Override public String getServletPath() { return ""; }
        @Override public boolean isRequestedSessionIdValid() { return false; }
        @Override public boolean isRequestedSessionIdFromCookie() { return false; }
        @Override public boolean isRequestedSessionIdFromURL() { return false; }
        @Override public boolean isRequestedSessionIdFromUrl() { return false; }
        @Override public boolean authenticate(HttpServletResponse response) { return false; }
        @Override public void login(String username, String password) {}
        @Override public void logout() {}
        @Override public java.util.Collection<Part> getParts() { return null; }
        @Override public Part getPart(String name) { return null; }
        @Override public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) { return null; }
        @Override public Object getAttribute(String name) { return null; }
        @Override public Enumeration<String> getAttributeNames() { return null; }
        @Override public String getCharacterEncoding() { return null; }
        @Override public void setCharacterEncoding(String env) {}
        @Override public int getContentLength() { return -1; }
        @Override public long getContentLengthLong() { return -1; }
        @Override public String getContentType() { return null; }
        @Override public ServletInputStream getInputStream() { return null; }
        @Override public String[] getParameterValues(String name) { return new String[]{getParameter(name)}; }
        @Override public Enumeration<String> getParameterNames() { return java.util.Collections.enumeration(params.keySet()); }
        @Override public Map<String, String[]> getParameterMap() {
            Map<String, String[]> map = new HashMap<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                map.put(entry.getKey(), new String[]{entry.getValue()});
            }
            return map;
        }
        @Override public String getProtocol() { return "HTTP/1.1"; }
        @Override public String getScheme() { return "http"; }
        @Override public String getServerName() { return "localhost"; }
        @Override public int getServerPort() { return 8080; }
        @Override public java.io.BufferedReader getReader() { return null; }
        @Override public String getRemoteAddr() { return "127.0.0.1"; }
        @Override public String getRemoteHost() { return "localhost"; }
        @Override public void setAttribute(String name, Object o) {}
        @Override public void removeAttribute(String name) {}
        @Override public java.util.Locale getLocale() { return java.util.Locale.getDefault(); }
        @Override public Enumeration<java.util.Locale> getLocales() { return null; }
        @Override public boolean isSecure() { return false; }
        @Override public int getRemotePort() { return 0; }
        @Override public String getLocalName() { return "localhost"; }
        @Override public String getLocalAddr() { return "127.0.0.1"; }
        @Override public int getLocalPort() { return 8080; }
        @Override public ServletContext getServletContext() { return null; }
        @Override public AsyncContext startAsync() { return null; }
        @Override public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) { return null; }
        @Override public boolean isAsyncStarted() { return false; }
        @Override public boolean isAsyncSupported() { return false; }
        @Override public AsyncContext getAsyncContext() { return null; }
        @Override public DispatcherType getDispatcherType() { return DispatcherType.REQUEST; }
    }

    private static class DummyResponse implements HttpServletResponse {
        private String redirectedUrl;

        @Override
        public void sendRedirect(String location) {
            this.redirectedUrl = location;
        }

        public String getRedirectedUrl() {
            return redirectedUrl;
        }

        @Override
        public PrintWriter getWriter() {
            return new PrintWriter(System.out);
        }

        // Required HttpServletResponse method implementations (minimal)
        @Override public void addCookie(Cookie cookie) {}
        @Override public boolean containsHeader(String name) { return false; }
        @Override public String encodeURL(String url) { return url; }
        @Override public String encodeRedirectURL(String url) { return url; }
        @Override public String encodeUrl(String url) { return url; }
        @Override public String encodeRedirectUrl(String url) { return url; }
        @Override public void sendError(int sc, String msg) {}
        @Override public void sendError(int sc) {}
        @Override public void setDateHeader(String name, long date) {}
        @Override public void addDateHeader(String name, long date) {}
        @Override public void setHeader(String name, String value) {}
        @Override public void addHeader(String name, String value) {}
        @Override public void setIntHeader(String name, int value) {}
        @Override public void addIntHeader(String name, int value) {}
        @Override public void setStatus(int sc) {}
        @Override public void setStatus(int sc, String sm) {}
        @Override public int getStatus() { return 200; }
        @Override public String getHeader(String name) { return null; }
        @Override public java.util.Collection<String> getHeaders(String name) { return null; }
        @Override public java.util.Collection<String> getHeaderNames() { return null; }
        @Override public String getCharacterEncoding() { return "UTF-8"; }
        @Override public String getContentType() { return null; }
        @Override public ServletOutputStream getOutputStream() { return null; }
        @Override public void setCharacterEncoding(String charset) {}
        @Override public void setContentLength(int len) {}
        @Override public void setContentLengthLong(long len) {}
        @Override public void setContentType(String type) {}
        @Override public void setBufferSize(int size) {}
        @Override public int getBufferSize() { return 0; }
        @Override public void flushBuffer() {}
        @Override public void resetBuffer() {}
        @Override public boolean isCommitted() { return false; }
        @Override public void reset() {}
        @Override public void setLocale(java.util.Locale loc) {}
        @Override public java.util.Locale getLocale() { return java.util.Locale.getDefault(); }
    }

    private static class DummySession implements HttpSession {
        private final Map<String, Object> attributes = new HashMap<>();

        @Override
        public Object getAttribute(String name) {
            return attributes.get(name);
        }

        @Override
        public void setAttribute(String name, Object value) {
            attributes.put(name, value);
        }

        @Override
        public void removeAttribute(String name) {
            attributes.remove(name);
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return java.util.Collections.enumeration(attributes.keySet());
        }

        @Override public long getCreationTime() { return 0; }
        @Override public String getId() { return "dummy"; }
        @Override public long getLastAccessedTime() { return 0; }
        @Override public ServletContext getServletContext() { return null; }
        @Override public void setMaxInactiveInterval(int interval) {}
        @Override public int getMaxInactiveInterval() { return 0; }
        @Override public HttpSessionContext getSessionContext() { return null; }
        @Override public Object getValue(String name) { return getAttribute(name); }
        @Override public String[] getValueNames() { return attributes.keySet().toArray(new String[0]); }
        @Override public void putValue(String name, Object value) { setAttribute(name, value); }
        @Override public void removeValue(String name) { removeAttribute(name); }
        @Override public void invalidate() { attributes.clear(); }
        @Override public boolean isNew() { return false; }
    }
}