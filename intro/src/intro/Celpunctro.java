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

public class Celpunctro {
	
	public void celpunctroResults(String cautare,JTable jtableG) throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "D:\\programare\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		String url = "https://www.cel.ro/";
		driver.get(url);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='later']"))).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//input[@placeholder='Cauta un produs..']")));	
		driver.findElement(By.xpath("//input[@placeholder='Cauta un produs..']")).sendKeys(cautare, Keys.ENTER); 
		
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[text()='Sorteaza dupa:']")));
		int elementePrimaPag = driver.findElements(By.xpath("//div[@class='pret_n']")).size();
		if(elementePrimaPag > 0) {
			while(true) {
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='pret_n']"))));
				int count = driver.findElements(By.xpath("//div[@class='pret_n']")).size();
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//h2[@class='productTitle']/a"))));
				for (int j = 0; j < count; j++) {
					String nume = driver.findElements(By.xpath("//h2[@class='productTitle']/a")).get(j).getText().replaceAll(",", "");
					String getHref = driver.findElements(By.xpath("//h2[@class='productTitle']/a")).get(j).getAttribute("href");
					String pret = driver.findElements(By.xpath("//div[@class='pret_n']")).get(j).getText();
					String pretTaiat = pret.substring(0, pret.length() - 3);
					DefaultTableModel model = (DefaultTableModel)jtableG.getModel();
					model.addRow(new Object[] {nume,pretTaiat,getHref});

				}
				List<WebElement> paginaUrmatoare = driver.findElements(By.xpath("//div[@class='pageresults']//a[@class='last']"));
				if(paginaUrmatoare.size() != 0) {
					driver.findElement(By.xpath("//div[@class='pageresults']//a[@class='last']")).click();
					
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
