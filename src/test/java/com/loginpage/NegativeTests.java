package com.loginpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NegativeTests {

	@Parameters({ "username", "password", "expectdErrorMessage" })
	@Test(priority = 1, groups = { "negativeTests", "smokeTests" })
	public void negativeLoginTest(String username, String password, String expectdErrorMessage) {
		System.out.println("Starting negativeLoginTest with" + username + " and " + password);

// 		Create driver
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		// sleep for 3 second
		sleep(3000);

// 		Maximize browser windows 
		driver.manage().window().maximize();

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
		Assert.assertTrue(actualErrorMessage.contains(expectdErrorMessage),
				"Actual error message does not contain expected. \nActual: " + actualErrorMessage + "\nExpected: "
						+ expectdErrorMessage);

		// Close browser
		driver.quit();
	}

	/*
	 * @Test(priority = 2, groups = { "negativeTests"}) public void
	 * incorrectpasswordTest() {
	 * System.out.println("Starting incorrectpasswordTest");
	 * 
	 * // Create driver System.setProperty("webdriver.gecko.driver",
	 * "src/main/resources/geckodriver.exe"); WebDriver driver = new
	 * FirefoxDriver();
	 * 
	 * // sleep for 3 second sleep(3000);
	 * 
	 * // Maximize browser windows driver.manage().window().maximize();
	 * 
	 * // open test page String url = "https://demo.motadataserviceops.com/login";
	 * driver.get(url); System.out.println("Page is open");
	 * 
	 * // sleep for 2 second sleep(4000);
	 * 
	 * // Click on technician portal link
	 * 
	 * WebElement techlink = driver.findElement(By.
	 * xpath("//a[contains(text(),'Motadata Technician Portal')]"));
	 * techlink.click(); sleep(2000);
	 * 
	 * // enter username WebElement username =
	 * driver.findElement(By.xpath("//input[@id='username']"));
	 * username.sendKeys("nirav");
	 * 
	 * // enter password WebElement password =
	 * driver.findElement(By.xpath("//input[@id='password']"));
	 * password.sendKeys("admin@12345");
	 * 
	 * // click on login button WebElement loginbutton =
	 * driver.findElement(By.xpath("//button[@id='login-btn']"));
	 * loginbutton.click(); sleep(5000);
	 * 
	 * // verifications;
	 * 
	 * WebElement errormessage = driver .findElement(By.
	 * xpath("//div[contains(text(),'Invalid Credentials, Please try again.')]"));
	 * String expectdErrorMessage = "Invalid Credentials, Please try again."; String
	 * actualErrorMessage = errormessage.getText();
	 * Assert.assertTrue(actualErrorMessage.contains(expectdErrorMessage),
	 * "Actual error message does not contain expected. \nActual: " +
	 * actualErrorMessage + "\nExpected: " + expectdErrorMessage);
	 * 
	 * // Close browser driver.quit();
	 * 
	 * }
	 */

	private void sleep(long m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
