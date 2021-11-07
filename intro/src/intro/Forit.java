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

public class Forit {

	public void foritResults(String cautare,JTable jtableG) throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "D:\\programare\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		String url = "https://www.forit.ro/";
		driver.get(url);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='cookies-consent']//button[@class='btn btn-primary']"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Cauta un produs']")));
		driver.findElement(By.xpath("//input[@placeholder='Cauta un produs']")).sendKeys(cautare);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='input-group']//button[@type='submit']"))).click();				
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()=' Cauta doar produsele in stoc']")));
		int elementePrimaPag = driver.findElements(By.xpath("//div[contains(text(),'lei')]")).size();
		if(elementePrimaPag > 0) {
			while(true) {
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(text(),'lei')]"))));
				int count = driver.findElements(By.xpath("//div[contains(text(),'lei')]")).size();
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//body[@class='touch-disabled']//form[@class='addtocart-modal']//h5[@class='name']/a"))));
				for (int j = 0; j < count; j++) {
					String nume = driver.findElements(By.xpath("//body[@class='touch-disabled']//form[@class='addtocart-modal']//h5[@class='name']/a")).get(j).getText().replaceAll(",", "");
					String pret = driver.findElements(By.xpath("//div[contains(text(),'lei')]")).get(j).getText().replaceAll(",", ".");
					String pretTaiat = pret.substring(0, pret.length() - 4);
					String getHref = driver.findElements(By.xpath("//div[@id='products-list']//form[@class='addtocart-modal']//h5[@class='name']/a")).get(j).getAttribute("href");
					DefaultTableModel model = (DefaultTableModel)jtableG.getModel();
					model.addRow(new Object[] {nume,pretTaiat,getHref});


				}				
				List<WebElement> paginaUrmatoare = driver.findElements(By.xpath("//ul[@class='pagination pagination-sm pull-right']/li[@class='pagination-next']"));
				if(paginaUrmatoare.size() != 0) {
					driver.findElement(By.xpath("//ul[@class='pagination pagination-sm pull-right']/li[@class='pagination-next']")).click();	
				}
				else {
					break;
				}	

			}
			driver.close();			
		}
		else {
			//JOptionPane.showMessageDialog(jtableG, "Failed to find any items", "Search Failed", JOptionPane.ERROR_MESSAGE);
			int input = JOptionPane.showOptionDialog(jtableG, "Failed to find any items", "Search Failed", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
			if(input == JOptionPane.OK_OPTION || input == JOptionPane.CANCEL_OPTION) {
				driver.close();
			}		
		}
		
		
	}
}
