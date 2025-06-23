📁 RealativePaths
A Java-based Spring Boot application that extracts and generates the ID, name, relative XPath, and relative CSS selector for all visible and hidden elements from a given website URL. The extracted data is then exported into an Excel file for further analysis or testing purposes.

🚀 Features
✅ Accepts a target website URL as input

✅ Crawls and scans all DOM elements on the page

✅ Extracts:

Element ID

Element name

Relative XPath

Relative CSS Selector

✅ Exports the results into a structured Excel (.xlsx) file

✅ Supports both visible and hidden elements

✅ Built with Spring Boot and Selenium WebDriver

🛠️ Technologies Used
Java 17

Spring Boot

Selenium WebDriver

Apache POI (for Excel generation)

Maven

📦 How to Run
1. Clone the Repository
bash
git clone https://github.com/Patil-Abhishek23/RealativePaths.git
cd RealativePaths

3. Build the Project

mvn clean install

5. Run the Application
bash

java -jar target/RealativePaths-0.0.1-SNAPSHOT.jar

7. Access the API
Send a POST request to:

bash
http://localhost:8080/api/generateSelectors

Sample Request Body:
json
{
  "url": "https://example.com"
}
The generated Excel file will be saved in the local file system under a predefined path like ./output/selectors.xlsx.

📄 Sample Output
ID	Name	XPath	CSS Selector
username	user	//*[@id="username"]	#username
password	pass	//*[@name="pass"]	input[name="pass"]

🙋‍♂️ Author

Abhishek Patil

https://github.com/Patil-Abhishek23

