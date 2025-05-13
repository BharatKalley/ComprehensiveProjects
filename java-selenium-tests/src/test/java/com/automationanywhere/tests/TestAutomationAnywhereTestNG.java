package com.automationanywhere.tests;

import com.automationanywhere.selenium.TestAutomationAnywhere;
import org.testng.annotations.Test;

public class TestAutomationAnywhereTestNG extends TestAutomationAnywhere {

    @Test(priority = 1, enabled = true, timeOut = 10000)
    public void testLogoPresence() {
        verifyLogo();
    }

    @Test(priority = 2, enabled = true, timeOut = 10000)
    public void testRequestDemoButton() {
        verifyRequestDemo();
    }

    @Test(priority = 3, enabled = true, timeOut = 40000)
    public void testHeaderLinks() {
        verifyMenuLinks();
    }
}