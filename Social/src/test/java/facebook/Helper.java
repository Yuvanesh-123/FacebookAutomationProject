package facebook;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bouncycastle.oer.Switch;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Helper {
	static WebDriver driver;
	static String currentTimeStamp;
	static ExtentReports report;

	/*
	 * This method initialize the WebDriver
	 */
	public static void initiateWebdrivers() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

	}

	/*
	 * @param Not required
	 * 
	 * @return This method provide the driver Instance
	 */

	public static WebDriver getDriver() {
		if (driver == null) {
			initiateWebdrivers();
		}
		return driver;
	}

	/**
	 * Initialize the ExtentReport and attaching SparkReporter in "D" drive path
	 * with TimeStamp.
	 */
	public static void initiateExtentReport() {
		Date date = new Date();
		SimpleDateFormat formattedTime = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
		currentTimeStamp = formattedTime.format(date);
		report = new ExtentReports();
		ExtentSparkReporter allCasesRepot = new ExtentSparkReporter(
				"D:\\Eclipse\\DemoProjectScripts\\Reports\\" + currentTimeStamp + "\\AllCasesReport.html");
		ExtentSparkReporter passCasesRepot = new ExtentSparkReporter(
				"D:\\Eclipse\\DemoProjectScripts\\Reports\\" + currentTimeStamp + "\\PassCasesReport.html").filter()
				.statusFilter().as(new Status[] { Status.PASS }).apply();
		ExtentSparkReporter failCasesRepot = new ExtentSparkReporter(
				"D:\\Eclipse\\DemoProjectScripts\\Reports\\" + currentTimeStamp + "\\FailCasesReport.html").filter()
				.statusFilter().as(new Status[] { Status.FAIL }).apply();
		ExtentSparkReporter skipCasesRepot = new ExtentSparkReporter(
				"D:\\Eclipse\\DemoProjectScripts\\Reports\\" + currentTimeStamp + "\\SkipCasesReport.html").filter()
				.statusFilter().as(new Status[] { Status.SKIP }).apply();
		report.attachReporter(allCasesRepot, passCasesRepot, failCasesRepot, skipCasesRepot);

	}

	/*
	 * @param Not required
	 * 
	 * @return This method provide the ExtentReports
	 */

	public static ExtentReports getReport() {
		if (report == null) {
			initiateExtentReport();

		}
		return report;
	}

	/*
	 * This method will have the enum values for different pages
	 */
	public enum Pages {
		Login, Dashboard
	}

	/*
	 * @param Pages(enum)
	 * 
	 * @param TestCase name String This method will create a ExtentTest for each
	 * Enum Pages.
	 * 
	 * @return ExtentTest
	 */
	public static ExtentTest createTest(Pages pages, String testName) {
		ExtentReports report = getReport();
		ExtentTest test;

		switch (pages) {
		case Login:
			test = report.createTest(String.format("Login Page - %s", testName));
			test.assignCategory("Login Page");
			return test;

		case Dashboard:
			test = report.createTest(String.format("Dashboard - %s", testName));
			test.assignCategory("Dashboard");
			return test;

		}
		return null;

	}

	/*
	 * This method helps to take a screenshot of failure testCases to attach in the
	 * report for future debugging
	 */
	public static String captureScreenshot() {
		driver = getDriver();
		String base64codeShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		return base64codeShot;
	}

	/**
	 * 
	 * @param ExtentTest, Assertion Error 
	 * This method will add the failure
	 *                    TestCasesin the ExtentReport of both All_test_cases and
	 *                    Fail__test_cases with ScreenShot and Error
	 * 
	 * @throws Assertion error in cosole.
	 * 
	 * 
	 */
	public static void triggerAssertionError(ExtentTest test, AssertionError error) {
		String assertionScreenshot = captureScreenshot();
		String errorMessage = error.getMessage();
		test.fail(error).addScreenCaptureFromBase64String(assertionScreenshot, errorMessage);
		throw new AssertionError();
	}
	
	
	/**
	 * 
	 * @param ExtentTest, Expection Error 
	 * This method will add the failure
	 *                    TestCasesin the ExtentReport of both All_test_cases and
	 *                    Fail__test_cases with ScreenShot and Error
	 * 
	 * @throws Expection error in cosole.
	 * 
	 * 
	 */
	public static void triggerExpectionError(ExtentTest test, Exception error) {
		String expectionScreenshot = captureScreenshot();
		String exErrorMessage = error.getMessage();
		test.fail(error).addScreenCaptureFromBase64String(expectionScreenshot, exErrorMessage);
		throw new AssertionError();
	}
	

}
