package com.loginpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.opentelemetry.exporter.logging.SystemOutLogExporter;

public class LoginTests {

	private WebDriver driver;

	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	private void setup(@Optional("chrome") String browser) {

// 		Create driver
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver = new ChromeDriver();
			break;

		case "firefox":
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
			driver = new FirefoxDriver();
			break;

		default:
			System.out.println("Do not khow how to start" + browser + ", starting chrome instaed");
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}

		// sleep for 3 second
		sleep(3000);

// 		Maximize browser windows 
		driver.manage().window().maximize();

	}

	@Test(priority = 1, groups = { "positiveTests", "smokeTests" })
	public void positiveLoginTest() {
		System.out.println("Starting loginTest");

//		open test page
		String url = "https://demo.motadataserviceops.com/login";
		driver.get(url);
		System.out.println("Page is open");

		// sleep for 2 second
		sleep(4000);

		// Click on technician portal link

		WebElement techlink = driver.findElement(By.xpath("//a[contains(text(),'Motadata Technician Portal')]"));
		techlink.click();
		sleep(2000);

//		enter username
		WebElement username = driver.findElement(By.xpath("//input[@id='username']"));
		username.sendKeys("nirav");

//		enter password
		WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
		password.sendKeys("admin@123");

//		click on login button
		WebElement loginbutton = driver.findElement(By.xpath("//button[@id='login-btn']"));
		loginbutton.click();
		sleep(5000);

//		verifications;

		// new url
		String expectetdUrl = "https://demo.motadataserviceops.com/dashboard/";
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectetdUrl, "Actual URL is not the same as expected");
		sleep(3000);

		// click on profile name to verify signout button is vissble
		WebElement profile = driver
				.findElement(By.xpath("//div[@id='user-dropdown']/a[@href='javascript:;']//img[@alt='Avatar']"));
		profile.click();
		sleep(2000);

		WebElement signoutbutton = driver.findElement(By.xpath("//button[@id='signout-btn']"));
		Assert.assertTrue(signoutbutton.isDisplayed(), "Signout button is not visible");
		sleep(3000);

		// After successful login Dashboard visible
		WebElement Dashboard = driver.findElement(By.xpath(
				"//body/div[1]/div[1]/section[1]/section[1]/section[1]/div[1]/div[1]/div[1]/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/h3[1]/div[1]/div[1]/div[1]/span[1]"));
		String expectedMessage = "Dashboard";
		String actualMessage = Dashboard.getText();
		Assert.assertEquals(actualMessage, expectedMessage, "Dashboard is not visible same as the expected ");

	}

	@Parameters({ "username", "password", "expectderrorMessage" })
	@Test(priority = 2, groups = { "negativeTests", "smokeTests" })
	public void negativeLoginTest(String username, String password, String expectderrorMessage) {
		System.out.println("Starting negativeLoginTest with" + username + " and " + password);

		/*
		 * // Create driver System.setProperty("webdriver.chrome.driver",
		 * "src/main/resources/chromedriver.exe"); WebDriver driver = new
		 * ChromeDriver();
		 * 
		 * // sleep for 3 second sleep(3000);
		 * 
		 * // Maximize browser windows driver.manage().window().maximize();
		 */

//		open test page
		String url = "https://demo.motadataserviceops.com/login";
		driver.get(url);
		System.out.println("Page is open");

		// sleep for 2 second
		sleep(4000);

		// Click on technician portal link

		WebElement techlink = driver.findElement(By.xpath("//a[contains(text(),'Motadata Technician Portal')]"));
		techlink.click();
		sleep(2000);

//		enter username
		WebElement usernameElement = driver.findElement(By.xpath("//input[@id='username']"));
		usernameElement.sendKeys(username);

//		enter password
		WebElement passwordElement = driver.findElement(By.xpath("//input[@id='password']"));
		passwordElement.sendKeys(password);

//		click on login button
		WebElement loginbutton = driver.findElement(By.xpath("//button[@id='login-btn']"));
		loginbutton.click();
		sleep(5000);

//		verifications;

		WebElement errormessage = driver
				.findElement(By.xpath("//div[contains(text(),'Invalid Credentials, Please try again.')]"));
		String actualErrorMessage = errormessage.getText();
		Assert.assertTrue(actualErrorMessage.contains(expectderrorMessage),
				"Actual error message does not contain expected. \nActual: " + actualErrorMessage + "\nExpected: "
						+ expectderrorMessage);

	}

	private void sleep(long m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@AfterMethod(alwaysRun = true)
	private void tearDown() {
		// Close browser
		driver.quit();
	}

}
