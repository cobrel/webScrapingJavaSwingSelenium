package itemScraper;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class Emag extends Utils {
	public void emagResults(String cautare, JTable jtableG) {
		iterate();
		driver.navigate().to("https://www.emag.ro/");

		// accept cookie
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement cookie = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Accept toate ']")));
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
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@data-zone='title']"))));
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[text()='Lei']/parent::p/parent::div//p[last()]"))));
				List<WebElement> names = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@data-zone='title']")));
				List<WebElement> prices = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[text()='Lei']/parent::p/parent::div//p[last()]")));
				int count = Math.min(prices.size(), names.size());
				for (int j = 0; j < count; j++) {							
					String nume = names.get(j).getText().replaceAll(",","");						
					String getHref = names.get(j).getAttribute("href");
					String pret = prices.get(j).getText();
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