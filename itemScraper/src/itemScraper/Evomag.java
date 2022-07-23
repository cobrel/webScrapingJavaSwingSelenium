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

public class Evomag extends Utils{
	public void evomagResults(String cautare, JTable jtableG) {
		iterate();
		driver.navigate().to("https://www.evomag.ro/");
		WebDriverWait wait = new WebDriverWait(driver, 15);
		// accept cookie
		WebElement cookie = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='pushinstruments_popup']//a[@class='pushinstruments_button_deny']")));
		cookie.click();
		//search
		WebElement Cautare = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='ce cauti astazi?']")));
		Cautare.sendKeys(cautare, Keys.ENTER);
		//check if there are items on the page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sn-slider']")));
		int elementePrimaPag = driver.findElements(By.xpath("//span[@class='real_price']")).size();
		//loop to get the elements
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
