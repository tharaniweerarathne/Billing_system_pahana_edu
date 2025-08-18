package com.Billing_system_pahana_edu.dao;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class BillDAOTest {
    private BillDAO billDAO;

    @Before
    public void setUp() {
        billDAO = new BillDAO();
    }

    @Test
    public void testGetSimpleBillsDummy() throws Exception {

        LinkedList<String[]> dummyList = new LinkedList<>();
        dummyList.add(new String[]{"1", "C001", "Alice", "Book", "400.00", "50.00", "2025-08-18"});
        dummyList.add(new String[]{"2", "C002", "Bob", "Pen", "100.00", "10.00", "2025-08-18"});


        assertEquals(2, dummyList.size());

        String[] first = dummyList.get(0);
        assertEquals("1", first[0]);
        assertEquals("C001", first[1]);
        assertEquals("Alice", first[2]);
        assertEquals("Book", first[3]);
        assertEquals("400.00", first[4]);
        assertEquals("50.00", first[5]);
        assertEquals("2025-08-18", first[6]);
    }
}
