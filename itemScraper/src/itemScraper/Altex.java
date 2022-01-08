package itemScraper;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Altex extends Utils {

	public void altexResults(String cautare, JTable jtableG) {
		Utils.createInstance();
		driver.get("https://altex.ro/");
		WebDriverWait wait = new WebDriverWait(driver, 15);
		if (driver.findElements(By.xpath("//*[text()='Inapoi in site']")).size() != 0) {
			driver.findElement(By.xpath("//*[text()='Inapoi in site']")).click();
		}
		// accept cookie
		WebElement cookie = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='notice-cookie-block']//button")));
		cookie.click();
		// search for item
		WebElement Cauta = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Cauta produsul dorit']")));
		Cauta.sendKeys(cautare, Keys.ENTER);
		// check if there are items on the page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(text(),'Rezultate cautare')]")));
		int elementePrimaPag = driver.findElements(By.xpath("//ul//*[text()='lei']/parent::div")).size();
		//loop to get the elements
		if (elementePrimaPag > 0) {
			while (true) {
				
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[text()='lei']/parent::div/parent::div/parent::div/parent::a/h2"))));
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[text()='lei']/parent::div"))));
				List<WebElement> paginaUrmatoare = driver.findElements(By.xpath("//span//*[contains(text(),'Incarca inca')]")); 
				if (paginaUrmatoare.size() != 0) {
					driver.findElement(By.xpath("//span//*[contains(text(),'Incarca inca')]")).click();
				} else {
					int count = driver.findElements(By.xpath("//*[text()='lei']")).size();
					for (int j = 0; j < count; j++) {
						String nume = driver.findElements(By.xpath("//*[text()='lei']/parent::div/parent::div/parent::div/parent::a/h2")).get(j).getText().replaceAll(",", "");
						String getHref = driver.findElements(By.xpath("//*[text()='lei']/parent::div/parent::div/parent::div/parent::a")).get(j).getAttribute("href");
						String pret = driver.findElements(By.xpath("//*[text()='lei']/parent::div")).get(j).getText();
						String pretTaiat = pret.substring(0, pret.length() - 4).replaceAll(",", ".");
						DefaultTableModel model = (DefaultTableModel) jtableG.getModel();
						model.addRow(new Object[] { nume, pretTaiat, getHref });
						
						
					}
					break;
				}
			}
			openTab();
		} 
		else {
			int input = JOptionPane.showOptionDialog(jtableG, "Failed to find any items", "Search Failed", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
			if(input == JOptionPane.OK_OPTION || input == JOptionPane.CANCEL_OPTION) {
				openTab();
			}	
		}

	}

}
