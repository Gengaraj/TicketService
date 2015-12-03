

Infrastructure Prerequisites:
* Java 1.7 
* Maven 3.x.x
* Environment variables for Java and Maven should be set

Execution Procedure:
* Clone or download the project from Github and save it into your desired directory
* Navigate to the saved directory through your command prompt
* Run the command “mvn install”. This should install the project successfully and create a jar file under target directory in the same folder
* Navigate to the generated target directory and run the command “java –jar ticket-service-1.jar”.
* There will be prompt to key in request attributes and choose the functionality accordingly and press enter after each prompt.
* The program should execute and print the results in the next line.
 Assumptions & Call-outs 
1.	Data held in memory with no permanent persistence such as Database or flat files.
2.	Odd row seats are numbered from Left to Right. Even row seats are numbered from Right to Left in the Performance Avenue. So that bulk bookings will seated together.
3.	Customer will key-in valid values as suggested by the prompt while testing.
4.	Junit is added through Maven to automate the unit testing during the build. 
5.	PMD also added through maven build to check any code violations.

