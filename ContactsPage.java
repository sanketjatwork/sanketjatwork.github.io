package pomclass;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import baseclasses.Utility;

public class ContactsPage {

	
	@FindBy(xpath="(//button[@class='ui linkedin button'])[2]")private WebElement Export;
	@FindBy(xpath="(//button[@class='ui linkedin button'])[3]")private WebElement Create;
	@FindBy(xpath="//tbody//tr//td[2]//a")private List<WebElement> userNames;
	@FindBy(xpath="//button[text()='Show Filters']")private WebElement ShowFilters;
	@FindBy(xpath="//input[@aria-autocomplete='list']")private WebElement FilterType;
	@FindBy(xpath="//div[@name='name']//div[@class='divider text']")private WebElement selectedFilterType;
	@FindBy(xpath="//div[@class='field']//div[@name='subfield']")private WebElement subFieldFilter;
	@FindBy(xpath="//div[@name='operator']")private WebElement FilterOperator;
	@FindBy(xpath="//div[@class='field value']//div[@role='listbox']")private WebElement valueListbox;
	@FindBy(xpath="//div[@class='field value']//div//input[@name='value']")private WebElement valueTextBox;
	@FindBy(xpath="//button[@class='ui small icon button']//i[@class='search small icon']")private WebElement applyFilterButton;
	@FindBy(xpath="//tbody//tr")private List<WebElement> Filteredrows;
	@FindBy(xpath="//div[@name='action']")private WebElement actionDropdown;
	@FindBy(xpath="//div[@role='option']//span[contains(text(),'Delete')]")private WebElement DeleteContact;
	@FindBy(xpath="//div[@class='ui basic icon left attached button']")private WebElement actionConfirm;
	
	public ContactsPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	
	public void clickShowFilters() 
{
	ShowFilters.click();
}
	public void clickExport()
{
	Export.click();
}
	public void clickCreate() 
{
	Create.click();
}
	public void openContactDetail(WebDriver driver, String contactName) {
		
		Utility.Ewait(driver, driver.findElement(By.xpath("//table//tbody//tr//td[2]//a[text()='"+contactName+"']")));
		driver.findElement(By.xpath("//table//tbody//tr//td[2]//a[text()='"+contactName+"']")).click();
	}
	public void validateEntryUser(WebDriver driver, String expectedUsername) 
	{
		boolean usernamePresent = driver.findElement(By.xpath("//tbody//tr//td[2]//a[text()='"+expectedUsername+"']")).isDisplayed();
		 
		Assert.assertTrue(usernamePresent);
		 }
	public void clickFilterType()
	{
		FilterType.click();
	}
	public void selectFilterType(WebDriver driver, String FilterType, String subFieldInput)
	{
		
		 WebElement FilterTypeColumn = driver.findElement(By.xpath("//div[@role='combobox']//div//span[text()='"+FilterType+"']"));
		Utility.Ewait(driver, FilterTypeColumn);
		 FilterTypeColumn.click();
		 boolean subFieldboxStatus = false;
			/*try {
				subFieldboxStatus = subFieldFilter.isDisplayed();
			 } catch (NoSuchElementException e) {
			        System.out.println("subFieldbox is not present.");
			    }*/
			if (subFieldboxStatus == true)
		    {
				subFieldFilter.click();
				WebElement subfieldOption = driver.findElement(By.xpath("//div[@name='subfield']//span[text()='"+subFieldInput+"']"));
				subfieldOption.click();
		           }
		 
	}
	public void clickOperatorFilter()
	{
		FilterOperator.click();
	}
	public void selectFilterOperator(WebDriver driver, String Operator)
	{
		driver.findElement(By.xpath("//div[@name='operator']//div//span[text()='"+Operator+"']")).click();
	}
	public void clickValueFilterListBox()
	{
		 valueListbox.click();
	}
	public void clickValueFilterTextbox()
	{
		valueTextBox.click();
	}
	
	public void selectFilterValue(WebDriver driver, String value, String TextvalueInput) throws InterruptedException
	{
		boolean ListboxStatus = false;
		/*try {
		 ListboxStatus = valueListbox.isDisplayed();
		 } catch (NoSuchElementException e) {
		        System.out.println("Value List Box is not present.");
		    }*/

	    boolean valueTextBoxStatus = false; // Initialize the status
	    /* try {
	        valueTextBoxStatus = valueTextBox.isDisplayed(); // Try to check visibility
	    } catch (NoSuchElementException e) {
	        System.out.println("Value Text Box is not present.");
	    }*/

	    System.out.println(valueTextBoxStatus);
 
	    if (ListboxStatus == true)
	    {
	    	clickValueFilterListBox();
	        driver.findElement(By.xpath("//div[@name='value']//div//span[text()='"+value+"']")).click();
	        
	    }
	    if (valueTextBoxStatus == true) 
	    {
	    	clickValueFilterTextbox();
	    	valueTextBox.sendKeys(TextvalueInput);
	    } else
	    {
	        System.out.println("No element found");
	    }
	}
	public void applyFilter()
	{
		applyFilterButton.click();
	}
	public void validateFilters(WebDriver driver, String inputName, String inputValue) throws InterruptedException
	{
		Thread.sleep(2000);
		int rowsPresent = Filteredrows.size();
		Utility.Ewait(driver, selectedFilterType);
		String appliedFilter = selectedFilterType.getText();
		System.out.println(appliedFilter);
		if(appliedFilter.contains("Name"))
		{
			
		List<WebElement> Filteredrows = driver.findElements(By.xpath("//tbody//tr//td//a[contains(text(),'"+inputName+"')]"));
			
		int rowsWithFilteredName = Filteredrows.size();
		Assert.assertEquals(rowsWithFilteredName, rowsPresent,"verified successfully the Namefilter applied");
				}
		
		else {
		
			List<WebElement> Filteredrows = driver.findElements(By.xpath("//tbody//tr//td[contains(text(),'"+inputValue+"')]"));
			
			int rowsWithFilteredValue = Filteredrows.size();
			Assert.assertEquals(rowsWithFilteredValue, rowsPresent," verified successfully filter applied");
			System.out.println("helloNagpur");
		}
	}
	public void tickContactCheckbox(WebDriver driver, String contactName)
	{
		
		WebElement checkBoxContact = driver.findElement(By.xpath("//tr//td//a[contains(text(),'"+contactName+"')]//preceding::td[1]//div//input[@name='id']"));
		Utility.Ewait(driver, checkBoxContact);
		checkBoxContact.click();
		
	}
	public void deleteContact()
	{
		actionDropdown.click();
		DeleteContact.click();	
		actionConfirm.click();
	}
	
}
