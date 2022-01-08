package itemScraper;

import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Utils {
	public static WebDriver driver;

	public static void createInstance() {
		System.setProperty("webdriver.gecko.driver", "D:\\programare\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
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
