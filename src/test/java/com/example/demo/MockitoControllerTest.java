package com.example.demo;

import com.example.demo.h2.Staff;
import com.example.demo.h2.StaffController;
import com.example.demo.h2.StaffService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * you can now test controllers, services, or any other kind of object,
 * without having to run an integration test that boots up the Spring container.
 */
public class MockitoControllerTest {

    @InjectMocks
    private StaffController staffController;

    @Mock
    private StaffService staffService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testController() {
        Staff staff = new Staff();
        staff.setAge(10);
        staff.setId("34");
        staff.setName("dfd");
        when(staffService.getStaff("34")).thenReturn(staff);

        String response = staffController.getStaff("34");

        System.out.println(response);
        verify(staffService).getStaff("34");

        assertNotNull(response);
    }

}