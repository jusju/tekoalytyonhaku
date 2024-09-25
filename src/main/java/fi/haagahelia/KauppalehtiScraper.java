package fi.haagahelia;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver; // Changed to FirefoxDriver
import java.util.ArrayList;
import java.util.List;

public class KauppalehtiScraper {

    // Custom class to hold company name and hyperlink information
    static class Company {
        String name;
        String url;

        Company(String name, String url) {
            this.name = name;
            this.url = url;
        }

        @Override
        public String toString() {
            return "Company Name: " + name + ", URL: " + url;
        }
    }

    public static void main(String[] args) {
        // Set path to your GeckoDriver
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\jusju\\git\\tekoalytyonhaku\\geckodriver.exe"); // Use geckodriver instead of chromedriver

        // Instantiate WebDriver for Firefox
        WebDriver driver = new FirefoxDriver(); // Changed to FirefoxDriver

        // List to store company names and URLs
        List<Company> companies = new ArrayList<>();

        try {
            // Loop through all 835 pages
            for (int page = 1; page <= 835; page++) {
                // Build the URL for each page
                String url = "https://www.kauppalehti.fi/yritykset/toimialat/ohjelmistojen-suunnittelu-ja-valmistus/62010?page=" + page;
                driver.get(url);

                // Wait for the page to load fully (you can add explicit waits here if needed)
                Thread.sleep(3000); // Simple sleep for demonstration, use WebDriverWait for better control
                driver.findElement(By.cssSelector("#almacmp-modalConfirmBtn")).click();
 

                
                // Find all company name elements and their corresponding links
                List<WebElement> companyElements = driver.findElements(By.cssSelector("a.in-block.company-list-link")); 

                // Extract the company names and URLs
                for (WebElement companyElement : companyElements) {
                    String companyName = companyElement.getText(); // Get company name
                    String companyUrl = companyElement.getAttribute("href"); // Get hyperlink (URL)

                    // Add company details to the list
                    companies.add(new Company(companyName, companyUrl));
                }

                System.out.println("Page " + page + " scraped successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser once the scraping is done
            driver.quit();
        }

        // Print out the scraped data (you can save it to a file or database)
        for (Company company : companies) {
            System.out.println(company);
        }
    }
}