package itemScraper;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Emag extends Utils {
	public void emagResults(String cautare, JTable jtableG) {
		iterate();
		driver.navigate().to("https://www.emag.ro/");
		WebDriverWait wait = new WebDriverWait(driver, 15);
		// accept cookie
		WebElement cookie = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='gdpr-cookie-banner js-gdpr-cookie-banner pad-sep-xs pad-hrz-none show']//button[1]")));
		cookie.click();
		//searchBar
		WebElement Cautare = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='searchboxTrigger']")));
		Cautare.click();
		Cautare.sendKeys(cautare);
		WebElement lupa = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='input-group-btn']//button[@class='btn btn-default searchbox-submit-button']")));
		lupa.click();
		//check if there are items on the page
		WebElement ad = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//i[@class='em em-close'])[2]")));
		ad.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='clearfix pad-btm-md']")));
		int elementePrimaPag = driver.findElements(By.xpath("//i[@class='em em-fav em-fav-bold']")).size();
		//loop to get the elements
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
				
				int paginaUrmatoare = driver.findElements(By.xpath("//*[text()='Pagina urmatoare']")).size();
				int ultimaPag = driver.findElements(By.xpath("//*[text()='Pagina urmatoare']/parent::li")).size();
				WebElement paginaUrmatoareDisplayed = driver.findElement(By.xpath("//*[text()='Pagina urmatoare']"));
				//WebElement ultimaPagDisplayed = driver.findElement(By.xpath("//*[text()='Pagina urmatoare']/parent::li"));
				
				if (paginaUrmatoare != 0 && ultimaPag == 0 && paginaUrmatoareDisplayed.isDisplayed()) {
					driver.findElement(By.xpath("//*[text()='Pagina urmatoare']")).click();
				} 
				else {
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
