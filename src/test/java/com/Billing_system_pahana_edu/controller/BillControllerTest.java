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

public class BillControllerTest {
    private BillController controller;
    private DummyRequest request;
    private DummyResponse response;

    @Before
    public void setUp() {
        controller = new BillController();
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
    public void testBillControllerGetRequest() throws Exception {

        request.setParameter("showCustomers", "true");
        request.setParameter("showItems", "true");

        controller.doGet(request, response);


        assertNotNull("Request should complete without error", request.getAttribute("customers"));
        assertNotNull("Request should complete without error", request.getAttribute("items"));
    }

    @Test
    public void testBillControllerWithCustomerSearch() throws Exception {
        String suffix = generateShortSuffix();
        String customerId = null;

        try {

            customerId = createTestCustomer(suffix);

            // Set search parameters
            request.setParameter("customerSearch", "TestCustomer" + suffix);

            // execute GET request
            controller.doGet(request, response);

            // verify customer search results
            @SuppressWarnings("unchecked")
            List<?> customers = (List<?>) request.getAttribute("customers");
            assertNotNull("Customers should not be null", customers);

        } finally {
            // cleanup
            if (customerId != null) cleanupCustomer(customerId);
        }
    }

    @Test
    public void testBillControllerWithItemSearch() throws Exception {
        String suffix = generateShortSuffix();
        String itemId = null;

        try {
            // setup test item
            itemId = createTestItem(suffix);

            // set search parameters
            request.setParameter("itemSearch", "TestItem" + suffix);

            // execute GET request
            controller.doGet(request, response);

            // verify item search results
            @SuppressWarnings("unchecked")
            List<?> items = (List<?>) request.getAttribute("items");
            assertNotNull("Items should not be null", items);

        } finally {
            // cleanup
            if (itemId != null) cleanupItem(itemId);
        }
    }

    @Test
    public void testConfirmBillWithValidData() throws Exception {
        String suffix = generateShortSuffix();
        String customerId = null;
        String itemId = null;
        int billId = 0;

        try {
            // setup test data
            customerId = createTestCustomer(suffix);
            itemId = createTestItem(suffix);

            // set confirm bill parameters
            request.setParameter("action", "confirmBill");
            // FIXED: Use accountNo instead of customerId for selectedCustomer
            request.setParameter("selectedCustomer", "ACC" + suffix); // This is the accountNo we created
            request.setParameter("discount", "10.0");

            // set bill items
            request.setParameterValues("billItemId", new String[]{itemId});
            request.setParameterValues("billItemName", new String[]{"TestItem" + suffix});
            request.setParameterValues("billUnitPrice", new String[]{"50.0"});
            request.setParameterValues("billUnit", new String[]{"2"});

            // execute POST request
            controller.doPost(request, response);


            String error = (String) request.getAttribute("error");
            if (error != null) {
                System.err.println("Controller returned error: " + error);
            }

            // verify bill was processed (check if bill was created in DB)
            billId = getLastCreatedBillId();
            assertTrue("Bill should be created", billId > 0);


            assertNotNull("Customer should be set", request.getAttribute("customer"));
            assertNotNull("Items should be set", request.getAttribute("items"));
            assertNotNull("Total amount should be set", request.getAttribute("totalAmount"));
            assertNotNull("Final amount should be set", request.getAttribute("finalAmount"));

        } finally {
            // cleanup
            if (billId > 0) cleanupBill(billId);
            if (customerId != null) cleanupCustomer(customerId);
            if (itemId != null) cleanupItem(itemId);
        }
    }

    @Test
    public void testConfirmBillWithMissingCustomer() throws Exception {
        // set confirm bill parameters without customer
        request.setParameter("action", "confirmBill");
        request.setParameterValues("billItemId", new String[]{"ITEM001"});
        request.setParameterValues("billItemName", new String[]{"Test Item"});
        request.setParameterValues("billUnitPrice", new String[]{"50.0"});
        request.setParameterValues("billUnit", new String[]{"2"});


        controller.doPost(request, response);


        assertNotNull("Error should be set", request.getAttribute("error"));
        String error = (String) request.getAttribute("error");
        assertTrue("Error should mention missing customer", error.contains("Customer ID is missing"));
    }

    @Test
    public void testConfirmBillWithNoItems() throws Exception {
        String suffix = generateShortSuffix();
        String customerId = null;

        try {
            // setup test customer
            customerId = createTestCustomer(suffix);

            // set confirm bill parameters without items
            request.setParameter("action", "confirmBill");
            // FIXED: Use accountNo instead of customerId
            request.setParameter("selectedCustomer", "ACC" + suffix);


            controller.doPost(request, response);

            // verify error is set
            assertNotNull("Error should be set", request.getAttribute("error"));
            String error = (String) request.getAttribute("error");
            assertTrue("Error should mention no items", error.contains("No items found in bill"));

        } finally {
            // cleanup
            if (customerId != null) cleanupCustomer(customerId);
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


    private int getLastCreatedBillId() throws SQLException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT billId FROM bills ORDER BY billId DESC LIMIT 1");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("billId");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


    private void cleanupCustomer(String customerId) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM customers WHERE accountNo = ?")) {

            String suffix = customerId.substring(2);
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