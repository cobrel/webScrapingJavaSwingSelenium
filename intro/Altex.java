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

public class Altex {

	public void altexResults(String cautare, JTable jtableG) throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "D:\\programare\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		String url = "https://altex.ro/";
		driver.get(url);
		if(driver.findElements(By.xpath("//*[text()='Inapoi in site']")).size() != 0) {
			driver.findElement(By.xpath("//*[text()='Inapoi in site']")).click();
		}
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//input[@placeholder='Cauta produsul dorit']/parent::div")));
		driver.findElement(By.xpath("//input[@placeholder='Cauta produsul dorit']")).sendKeys(cautare, Keys.ENTER);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='notice-cookie-block']//button"))).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[text()='lei']/parent::div")));
		
		int elementePrimaPag = driver.findElements(By.xpath("//*[text()='lei']/parent::div")).size();
		if (elementePrimaPag > 0) {
			while (true) {
				
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
		} else {
			JOptionPane.showMessageDialog(jtableG, "Failed to find any items", "Search Failed",JOptionPane.ERROR_MESSAGE);
		}
		driver.close();
	}
}
