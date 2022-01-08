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

public class Celpunctro extends Utils{
	public void celpunctroResults(String cautare, JTable jtableG) {
		iterate();
		driver.navigate().to("https://www.cel.ro/");
		WebDriverWait wait = new WebDriverWait(driver, 15);
		//accept cookie
		WebElement cookie = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='later']")));
		cookie.click();
		//searchBar
		WebElement Cautare = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Cauta un produs..']")));
		Cautare.sendKeys(cautare, Keys.ENTER);
		// check if there are items on the page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Sorteaza dupa:']")));
		int elementePrimaPag = driver.findElements(By.xpath("//div[@class='pret_n']")).size();
		//loop to get the elements
		if(elementePrimaPag > 0) {
			while(true) {
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='pret_n']"))));
				int count = driver.findElements(By.xpath("//div[@class='pret_n']")).size();
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//h2[@class='productTitle']/a"))));
				for (int j = 0; j < count; j++) {
					String nume = driver.findElements(By.xpath("//h2[@class='productTitle']/a")).get(j).getText().replaceAll(",", "");
					String getHref = driver.findElements(By.xpath("//h2[@class='productTitle']/a")).get(j).getAttribute("href");
					String pret = driver.findElements(By.xpath("//div[@class='pret_n']")).get(j).getText();
					if(pret.length() < 3) {
						DefaultTableModel model = (DefaultTableModel)jtableG.getModel();
						model.addRow(new Object[] {nume,pret,getHref});						
					}
					else {
						String pretTaiat = pret.substring(0, pret.length() - 3);
						DefaultTableModel model = (DefaultTableModel)jtableG.getModel();
						model.addRow(new Object[] {nume,pretTaiat,getHref});						
					}


				}
				List<WebElement> paginaUrmatoare = driver.findElements(By.xpath("//div[@class='pageresults']//a[@class='last']"));
				if(paginaUrmatoare.size() != 0) {
					driver.findElement(By.xpath("//div[@class='pageresults']//a[@class='last']")).click();
					
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
