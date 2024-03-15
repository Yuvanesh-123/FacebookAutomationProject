package facebook;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class loginPage {

	WebDriver driver;
	WebElement emailInput;
	WebElement passwordInput;
	WebElement eyeIconContainer;
	WebElement eyeIcon;

	@BeforeSuite
	public void setUP() {
		driver = Helper.getDriver();

	}

	@Test
	public void TC_001_LoginPageLoaded() {
		driver.get("https://www.facebook.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		String Page = driver.getTitle();
		boolean isLoginpage = Page.contains("log in or sign up");
		Assert.assertEquals(isLoginpage, true);
		String welcomeText = driver.findElement(By.xpath("//div[@class='_8esl']/h2")).getText();
		Assert.assertEquals(welcomeText, "Facebook helps you connect and share with the people in your life.");

	}

	@Test
	public void TC_002_EmailInputBoxIsEnable() {
		emailInput = driver.findElement(By.id("email"));
		boolean emailInputIsEnabled = emailInput.isEnabled();
		Assert.assertEquals(emailInputIsEnabled, true);

	}

	@Test
	public void TC_003_PlaceholderIsPresented() {
		String placeHolderText = emailInput.getAttribute("placeholder");
		boolean IsPlaceholderPresented = placeHolderText.contains("Email address or phone number");
		Assert.assertEquals(IsPlaceholderPresented, true);
	}

	@Test
	public void TC_004_EmailInputIsEmailEnterable() {
		emailInput.sendKeys("XYZ@gmail.com");
		String emailTextValue = emailInput.getAttribute("value");
		Assert.assertEquals(emailTextValue, "XYZ@gmail.com");

	}

	@Test
	public void TC_005_EmailInputIsPhoneNumberEnterable() {
		emailInput.clear();
		emailInput.sendKeys("9814567891");
		String phoneNumberValue = emailInput.getAttribute("value");
		Assert.assertEquals(phoneNumberValue, "9814567891");
	}

	@Test
	public void TC_006_PasswaordInputBoxIsEnable() {
		passwordInput = driver.findElement(By.id("pass"));
		boolean passwordInputIsEnabled = passwordInput.isEnabled();
		Assert.assertEquals(passwordInputIsEnabled, true);

	}

	@Test
	public void TC_007_PlaceholderIsPresentedInPassword() {
		String placeHolderTextPass = passwordInput.getAttribute("placeholder");
		boolean IsPlaceholderPresentedPass = placeHolderTextPass.contains("Password");
		Assert.assertEquals(IsPlaceholderPresentedPass, true);
	}

	@Test
	public void TC_008_PasswordInputIsEnterable() {
		passwordInput.sendKeys("paSSwoRd123");
		String passwordTextValue = passwordInput.getAttribute("value");
		Assert.assertEquals(passwordTextValue, "paSSwoRd123");

	}

	@Test
	public void TC_009_EyeIconIsDisplayed() {
		eyeIconContainer = driver.findElement(By.xpath("//div[@id='passContainer']/div"));
		boolean isHidden = eyeIconContainer.getAttribute("class").contains("hidden_elem");
		Assert.assertEquals(isHidden, false);
	}

	@Test
	public void TC_010_EyeIconIsNotDisplayed() throws InterruptedException {
		passwordInput.clear();
		passwordInput.sendKeys(Keys.BACK_SPACE);
		Thread.sleep(1000);
		boolean isnotHidden = eyeIconContainer.getAttribute("class").contains("hidden_elem");
		Assert.assertEquals(isnotHidden, true);
	}

	@Test
	public void TC_011_PasswordDisplayedByClickEyeIcon() {
		passwordInput.sendKeys("paSSwoRd123");
		 eyeIcon = driver.findElement(By.xpath("//div[@class='_9lsa']"));
		eyeIcon.click();
		boolean IsDisplayedAsText = passwordInput.getAttribute("type").contains("text");
		Assert.assertEquals(IsDisplayedAsText, true);

	}

	@Test
	public void TC_012_PasswordNotDisplayedByClickEyeIcon() {
		eyeIcon.click();
		boolean IsDisplayedAsPass = passwordInput.getAttribute("type").contains("password");
		Assert.assertEquals(IsDisplayedAsPass, true);

	}

	@Test
	public void TC_013_ForgetPasswordIsRedirectable() throws InterruptedException {
		driver.findElement(By.xpath("//div[@class='_6ltj']/a")).click();
		Thread.sleep(3000);
		String findText = driver.findElement(By.xpath("//h2[@class='uiHeaderTitle']")).getText();
		Assert.assertEquals(findText.contains("Find Your Account"), true);

	}

	@AfterSuite
	public void closure() {
//		driver.close();
	}

}