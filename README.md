Project Setup

Download or clone this project from the repository.
Import it into your IDE (e.g., IntelliJ IDEA) as a Maven project.
Wait until all Maven dependencies are downloaded automatically.
You can also trigger it manually by right-clicking on the project and selecting
“Reload Maven Project” or running:
mvn clean install
Once dependencies are resolved, the project is ready to run.

Test Execution and Reporting

Step 1. Run the Tests
To execute all tests, use the following command:
mvn clean test
This command will clean previous results and run all tests from scratch.

Step 2. Open the Test Report
After the tests finish, Maven Surefire will automatically generate an HTML report.
Navigate to:
target/surefire-reports
Locate the file:
index.html
Open it in your browser to view the test results — including passed and failed tests, execution time, and logs.
