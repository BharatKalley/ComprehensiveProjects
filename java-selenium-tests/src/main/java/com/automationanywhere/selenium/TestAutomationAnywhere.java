package com.automationanywhere.selenium;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import dev.failsafe.internal.util.Assert;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestAutomationAnywhere extends BaseTest {

    private static final Logger logger = LogManager.getLogger(TestAutomationAnywhere.class);

    public void verifyLogo() {
        driver.get("https://www.automationanywhere.com/");
        WebElement logo = driver.findElement(By.xpath("//img[@alt='Automation Anywhere']"));
        Assert.isTrue(logo.isDisplayed(), "Logo is not displayed");
    }

    public void verifyRequestDemo() {
        WebElement demoButton = driver.findElement(By.xpath("//*[@id='topNavbar']/div/div/div[1]/div[2]/a"));
        Assert.isTrue(demoButton.isDisplayed(), "Request demo button is not displayed");
        Assert.isTrue(demoButton.isEnabled(), "Request demo button is not clickable");
    }

    public void verifyMenuLinks() {
        driver.get("https://www.automationanywhere.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Dismiss cookie banner if present
        try {
            WebElement acceptCookies = wait.until(
                    ExpectedConditions
                            .elementToBeClickable(By.xpath("//button[contains(text(),'Accept All Cookies')]")));
            acceptCookies.click();
            logger.info("Cookie popup dismissed.");
        } catch (Exception e) {
            logger.warn("Cookie popup not shown or already dismissed.");
        }

        Map<String, String> menuItems = new LinkedHashMap<>();
        menuItems.put("Products", "//a[contains(normalize-space(),'Products')]");
        menuItems.put("Solutions", "//a[contains(normalize-space(),'Solutions')]");
        menuItems.put("Resources", "//a[contains(normalize-space(),'Resources')]");
        menuItems.put("Beyond RPA", "//a[contains(normalize-space(),'Beyond RPA')]");
        menuItems.put("Company", "//a[contains(normalize-space(),'Company')]");

        for (Map.Entry<String, String> entry : menuItems.entrySet()) {
            String name = entry.getKey();
            String xpath = entry.getValue();

            try {
                List<WebElement> elements = driver.findElements(By.xpath(xpath));
                if (elements.isEmpty()) {
                    logger.error("Menu link not found: {}", name);
                    continue;
                }

                WebElement menuLink = wait.until(ExpectedConditions.elementToBeClickable(elements.get(0)));
                logger.info("Clicking on menu item: {}", name);
                menuLink.click();

                // Wait until the URL updates
                wait.until(ExpectedConditions.urlContains(name.toLowerCase().replace(" ", "-")));
                logger.info("Navigated to URL: {}", driver.getCurrentUrl());

                // Navigate back and wait for homepage to reload
                driver.navigate().back();
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(normalize-space(),'Products')]")));

            } catch (Exception e) {
                logger.error("Error while handling menu item '{}': {}", name, e.getMessage());
            }
        }
    }

}