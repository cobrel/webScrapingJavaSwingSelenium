package intro;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Emag {

	public void emagResults(String cautare, JTable jtableG) throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "D:\\programare\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		String url = "https://www.emag.ro/";
		driver.get(url);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='gdpr-cookie-banner js-gdpr-cookie-banner pad-sep-xs pad-hrz-none show']//button[1]"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='searchboxTrigger']"))).click();
		driver.findElement(By.xpath("//*[@id='searchboxTrigger']")).sendKeys(cautare);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='input-group-btn']//button[@class='btn btn-default searchbox-submit-button']"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//i[@class='em em-close'])[2]"))).click(); 
		
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='clearfix pad-btm-md']")));
		int elementePrimaPag = driver.findElements(By.xpath("//i[@class='em em-fav em-fav-bold']")).size();
		if(elementePrimaPag > 0) {
			while (true) {
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//i[@class='em em-fav em-fav-bold']"))));
				int count = driver.findElements(By.xpath("//i[@class='em em-fav em-fav-bold']")).size();
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@data-zone='title']"))));
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[text()='Lei']/parent::p/parent::div//p[last()]"))));
				for (int j = 0; j < count; j++) {							
					String nume = driver.findElements(By.xpath("//a[@data-zone='title']")).get(j).getText().replaceAll(",","");						
					String getHref = driver.findElements(By.xpath("//a[@data-zone='title']")).get(j).getAttribute("href");
					String pret = driver.findElements(By.xpath("//*[text()='Lei']/parent::p/parent::div//p[last()]")).get(j).getText();
					String pretTaiat = pret.substring(0, pret.length() - 6).replaceAll("de la", "");
					DefaultTableModel model = (DefaultTableModel) jtableG.getModel();
					model.addRow(new Object[] { nume, pretTaiat, getHref });
				}
				
				List<WebElement> paginaUrmatoare = driver.findElements(By.xpath("//*[text()='Pagina urmatoare']"));
				int ultimaPag = driver.findElements(By.xpath("//*[text()='Pagina urmatoare']/parent::li")).size();
				if (paginaUrmatoare.size() != 0 && ultimaPag == 0) {
					driver.findElement(By.xpath("//*[text()='Pagina urmatoare']")).click();
				} 
				else {
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
