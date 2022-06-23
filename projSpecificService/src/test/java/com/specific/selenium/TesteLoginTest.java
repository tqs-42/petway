package com.specific.selenium;

// Generated by Selenium IDE
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.extension.ExtendWith;
import io.github.bonigarcia.seljup.SeleniumJupiter;


import io.github.bonigarcia.wdm.WebDriverManager;

@ExtendWith(SeleniumJupiter.class)
public class TesteLoginTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;

    @BeforeAll
  static void setupClass() {
      WebDriverManager.firefoxdriver().setup();
  }

  @BeforeEach
  public void setUp() {
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @AfterEach
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void test1() {
    driver.get("http://localhost:4201/");
    driver.manage().window().setSize(new Dimension(1800, 1125));
    driver.findElement(By.cssSelector("span > .btn:nth-child(1)")).click();
    driver.findElement(By.id("login_email")).click();
    driver.findElement(By.id("login_email")).sendKeys("vera@ua.pt");
    driver.findElement(By.id("login_password")).sendKeys("qwertyui");
    driver.findElement(By.id("login_password")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector("#loginModal .btn-primary")).click();


    // driver.findElement(By.linkText("Products")).click();
    // driver.findElement(By.linkText("Categories")).click();
    // {
    //   WebElement element = driver.findElement(By.linkText("Categories"));
    //   Actions builder = new Actions(driver);
    //   builder.moveToElement(element).perform();
    // }
    // {
    //   WebElement element = driver.findElement(By.tagName("body"));
    //   Actions builder = new Actions(driver);
    //   builder.moveToElement(element, 0, 0).perform();
    // }
    // driver.findElement(By.cssSelector(".btn-outline-primary")).click();
    // driver.findElement(By.id("name")).click();
    // driver.findElement(By.id("name")).sendKeys("Comida");
    // driver.findElement(By.cssSelector(".btn-primary")).click();
    // {
    //   WebElement element = driver.findElement(By.cssSelector(".btn-primary"));
    //   Actions builder = new Actions(driver);
    //   builder.moveToElement(element).perform();
    // }
    // driver.findElement(By.linkText("Products")).click();
    // {
    //   WebElement element = driver.findElement(By.linkText("Products"));
    //   Actions builder = new Actions(driver);
    //   builder.moveToElement(element).perform();
    // }
    // {
    //   WebElement element = driver.findElement(By.tagName("body"));
    //   Actions builder = new Actions(driver);
    //   builder.moveToElement(element, 0, 0).perform();
    // }
    // driver.findElement(By.linkText("Categories")).click();
    // {
    //   WebElement element = driver.findElement(By.linkText("Categories"));
    //   Actions builder = new Actions(driver);
    //   builder.moveToElement(element).perform();
    // }
    // {
    //   WebElement element = driver.findElement(By.tagName("body"));
    //   Actions builder = new Actions(driver);
    //   builder.moveToElement(element, 0, 0).perform();
    // }
    // driver.findElement(By.linkText("Products")).click();
    // driver.findElement(By.cssSelector(".btn-outline-success")).click();
    // {
    //   WebElement element = driver.findElement(By.cssSelector(".btn-outline-success"));
    //   Actions builder = new Actions(driver);
    //   builder.moveToElement(element).perform();
    // }
    // driver.findElement(By.name("category")).click();
    // {
    //   WebElement dropdown = driver.findElement(By.name("category"));
    //   dropdown.findElement(By.xpath("//option[. = 'Comida']")).click();
    // }
    // driver.findElement(By.cssSelector("option:nth-child(2)")).click();
    // driver.findElement(By.id("name")).click();
    // driver.findElement(By.id("name")).sendKeys("Comida de cao");
    // driver.findElement(By.id("description")).click();
    // driver.findElement(By.id("description")).sendKeys("Comida humida de cao");
    // driver.findElement(By.id("price")).click();
    // driver.findElement(By.id("price")).sendKeys("7.66");
    // driver.findElement(By.id("stock")).click();
    // driver.findElement(By.id("stock")).sendKeys("10");
    // driver.findElement(By.cssSelector(".btn-primary")).click();
  }
}
