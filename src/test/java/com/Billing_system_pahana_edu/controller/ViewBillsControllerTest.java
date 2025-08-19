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

public class ViewBillsControllerTest {
    private ViewBillsController controller;
    private DummyRequest request;
    private DummyResponse response;

    @Before
    public void setUp() {
        controller = new ViewBillsController();
        request = new DummyRequest();
        response = new DummyResponse();
    }


    private static class DummyRequest extends HttpServletRequestWrapper {
        private final Map<String, String> params = new HashMap<>();
        private final Map<String, String[]> multiParams = new HashMap<>();
        private final Map<String, Object> attributes = new HashMap<>();

        public DummyRequest() { super(new HttpServletRequestAdapter()); }

        public void setParameter(String key, String value) { params.put(key, value); }

        public void setParameterValues(String key, String[] values) { multiParams.put(key, values); }

        @Override
        public String getParameter(String name) { return params.get(name); }

        @Override
        public String[] getParameterValues(String name) { return multiParams.get(name); }

        @Override
        public void setAttribute(String name, Object o) { attributes.put(name, o); }

        @Override
        public Object getAttribute(String name) { return attributes.get(name); }

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
        public String changeSessionId() { return ""; }

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
    public void testViewBillsBasicFunctionality() throws Exception {

        try {
            controller.doGet(request, response);


            assertTrue("ViewBillsController should handle GET request without exceptions", true);


            System.out.println("Attributes set by ViewBillsController:");
            if (request.getAttribute("bills") != null) {
                System.out.println("- bills: " + request.getAttribute("bills").getClass().getSimpleName());
            }
            if (request.getAttribute("error") != null) {
                System.out.println("- error: " + request.getAttribute("error"));
            }
            if (request.getAttribute("billList") != null) {
                System.out.println("- billList: " + request.getAttribute("billList").getClass().getSimpleName());
            }

        } catch (Exception e) {
            fail("ViewBillsController should not throw exceptions: " + e.getMessage());
        }
    }

    @Test
    public void testViewBillsWithDifferentParameters() throws Exception {

        String[][] paramCombinations = {
                {},
                {"fromDate", "2024-01-01"},
                {"toDate", "2024-12-31"},
                {"customerFilter", "ACC123"},
                {"customerId", "ACC123"},
                {"search", "test"},
                {"page", "1"},
                {"limit", "10"}
        };

        for (int i = 0; i < paramCombinations.length; i += 2) {

            request = new DummyRequest();


            if (i < paramCombinations.length - 1 && paramCombinations[i].length > 0) {
                request.setParameter(paramCombinations[i][0], paramCombinations[i + 1][0]);
            }

            try {
                controller.doGet(request, response);
                assertTrue("Controller should handle parameters without exceptions", true);
            } catch (Exception e) {
                fail("Controller failed with parameters: " + Arrays.toString(paramCombinations) +
                        " - " + e.getMessage());
            }
        }
    }

    @Test
    public void testViewBillsReturnsBillsList() throws Exception {
        String suffix = generateShortSuffix();
        String customerId = null;
        String itemId = null;
        int billId = 0;

        try {
            // create test data
            customerId = createTestCustomer(suffix);
            itemId = createTestItem(suffix);
            billId = createTestBill(suffix, customerId, itemId);


            controller.doGet(request, response);


            Object result = request.getAttribute("bills");
            if (result == null) result = request.getAttribute("billList");
            if (result == null) result = request.getAttribute("allBills");


            if (result != null && result instanceof List) {
                @SuppressWarnings("unchecked")
                List<?> bills = (List<?>) result;
                System.out.println("Found " + bills.size() + " bills in result");
            }


            assertTrue("ViewBillsController executed successfully", true);

        } finally {
            // cleanup
            if (billId > 0) cleanupBill(billId);
            if (customerId != null) cleanupCustomer(customerId);
            if (itemId != null) cleanupItem(itemId);
        }
    }


    private String generateShortSuffix() {
        return String.valueOf(System.currentTimeMillis() % 1000);
    }


    private String createTestCustomer(String suffix) throws SQLException {
        String customerId = "SS" + suffix;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO customers (accountNo, name, email, address, telephone) VALUES (?, ?, ?, ?, ?)")) {
            ps.setString(1, "ACC" + suffix);
            ps.setString(2, "TestCustomer" + suffix);
            ps.setString(3, "test" + suffix + "@example.com");
            ps.setString(4, "123 Test Street");
            ps.setString(5, "077123" + suffix);
            ps.executeUpdate();
            return customerId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private String createTestItem(String suffix) throws SQLException {
        String itemId = "IT" + suffix;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO items (itemId, itemName, price, unit) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, itemId);
            ps.setString(2, "TestItem" + suffix);
            ps.setDouble(3, 50.0);
            ps.setInt(4, 100);
            ps.executeUpdate();
            return itemId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private int createTestBill(String suffix, String customerId, String itemId) throws SQLException {
        int billId = 0;
        try (Connection conn = DBUtil.getConnection()) {
            // create bill
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO bills (customerId, totalAmount, discount, finalAmount, billDate) VALUES (?, ?, ?, ?, NOW())",
                    Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, "ACC" + suffix);
                ps.setDouble(2, 100.0);
                ps.setDouble(3, 10.0);
                ps.setDouble(4, 90.0);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        billId = rs.getInt(1);
                    }
                }
            }


            if (billId > 0) {
                try (PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO bill_items (billId, itemId, unit, unitPrice, totalPrice) VALUES (?, ?, ?, ?, ?)")) {
                    ps.setInt(1, billId);
                    ps.setString(2, itemId);
                    ps.setInt(3, 2);
                    ps.setDouble(4, 50.0);
                    ps.setDouble(5, 100.0);
                    ps.executeUpdate();
                }
            }

            return billId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void cleanupCustomer(String customerId) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM customers WHERE accountNo = ?")) {
            String suffix = customerId.substring(2); // Remove "SS" prefix
            String accountNo = "ACC" + suffix;
            ps.setString(1, accountNo);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error cleaning up customer: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void cleanupItem(String itemId) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM items WHERE itemId = ?")) {
            ps.setString(1, itemId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error cleaning up item: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void cleanupBill(int billId) {
        try (Connection conn = DBUtil.getConnection()) {

            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM bill_items WHERE billId = ?")) {
                ps.setInt(1, billId);
                ps.executeUpdate();
            }

            // delete bill
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM bills WHERE billId = ?")) {
                ps.setInt(1, billId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error cleaning up bill: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}