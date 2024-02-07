package itemScraper;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Utils {

	public static WebDriver driver;
	public static WebDriverWait wait;

	public static void createInstance() {

		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	public void openTab() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.open('')");
	}

	public void iterate() {
		Set<String> id = driver.getWindowHandles();
		Iterator<String> itr = id.iterator();
		while (itr.hasNext()) {
			String window = itr.next();
			driver.switchTo().window(window);

		}
	}

}
