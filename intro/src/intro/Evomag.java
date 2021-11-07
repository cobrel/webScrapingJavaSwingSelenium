package intro;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Evomag {

	public void evomagResults(String cautare,JTable jtableG) throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "D:\\programare\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		String url = "https://www.evomag.ro/";
		driver.get(url);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='pushinstruments_popup']//a[@class='pushinstruments_button_deny']"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='ce cauti astazi?']")));
		driver.findElement(By.xpath("//input[@placeholder='ce cauti astazi?']")).sendKeys(cautare, Keys.ENTER);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sn-slider']")));
		int elementePrimaPag = driver.findElements(By.xpath("//span[@class='real_price']")).size();
		if(elementePrimaPag > 0) {
			while (true) {
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[@class='real_price']"))));
				int count = driver.findElements(By.xpath("//span[@class='real_price']")).size();
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='npi_name']//a"))));
				for (int j = 0; j < count; j++) {			
					String nume = driver.findElements(By.xpath("//div[@class='npi_name']//a")).get(j).getText().replaceAll(",", "");
					String getHref = driver.findElements(By.xpath("//div[@class='npi_name']//a")).get(j).getAttribute("href");
					String pret = driver.findElements(By.xpath("//span[@class='real_price']")).get(j).getText();
					String pretTaiat = pret.substring(0, pret.length() - 4).replaceAll(",", ".");
					DefaultTableModel model = (DefaultTableModel)jtableG.getModel();
					model.addRow(new Object[] {nume,pretTaiat,getHref});

				}
				List<WebElement> paginaUrmatoare = driver.findElements(By.xpath("(//div[@class='pagination'])[1]//li[@class='next']"));
				if (paginaUrmatoare.size() != 0) {
					driver.findElement(By.xpath("(//div[@class='pagination'])[1]//li[@class='next']")).click();
				} else {
					break;
				}
			}
			driver.close();		
		}
		else {
			int input = JOptionPane.showOptionDialog(jtableG, "Failed to find any items", "Search Failed", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
			if(input == JOptionPane.OK_OPTION || input == JOptionPane.CANCEL_OPTION) {
				driver.close();
			}
		}		
	}
}
