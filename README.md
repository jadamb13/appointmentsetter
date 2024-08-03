# Internationalized Client and Appointment Management Suite
Multilingual and global appointment scheduling application with integrated customer management. Built with JavaFX, Scene Builder, and MySQL database with JDBC integration. 

### The application allows users to:
<ul>
    <li>Create, view, update, and delete international appointments between company contacts and clients.</li> 
    <li>Create, view, update, and delete customers and customer information.</li>
    <li>View reports including individual contact schedules and appointments by type, month, and day of the week.</li>
    <li>Track user login attempts by user id, time (timestamp), user's locale, and success or failure of the attempt.</li>
    <li>Persistently store data with MySQL database.</li>
    <li>The application currently supports English or French - determined by the user's system default locale.</li>
</ul>

### Application Development Environment
Application Version: 1.0

Date Created: April 30, 2023

IDE: IntelliJ IDEA 2021.1.3 (Community Edition)

JDK: Java SE Development Kit 17.0.1 (jdk-17.0.1_windows-x64_bin)

JavaFX: JavaFX SDK 17.0.6 (openjfx-17.0.6_windows-x64_bin-sdk)

MySQL Connector Driver: mysql-connector-java-8.0.25

### How to Run:

The language/locale of the application can be set manually if needed by uncommenting
"Locale.setDefault(new Locale("fr", "FR"));" in the main method, or it will be gathered automatically from
the system's default locale.

To run the program, launch the program from the main() method and enter credentials  
Username: test | Password: test  
(or)  
Username: admin | Password: admin  


Then, click "Login". From the main
application window, you are defaulted to the "Appointments" tab of the program where appointments can be
viewed, added, updated, or deleted. Appointments can also be viewed as "All Appointments", by week, or by month. 

To view, add, update, or delete Customers, click the "Customer" tab along the top of the application window.
Similarly, reports for the application can be viewed by clicking the "Reports" tab along the top of the
application window.

A login_activity.txt file is located at the root ("src") of the application file structure and Javadoc documentation files are 
located in the package: src/javadocs.


