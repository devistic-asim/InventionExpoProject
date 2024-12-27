# Invention Expo Project in JAVA

## Problem Statement
This assignment is a continuation of assignment # 1 (v1), where you have developed a basic Java program for an Invention Expo. Although the basic functionality remains the same (i.e., taking name and score as input from the user and calculating the winner), in this assignment, you are required to store/fetch data to/from a backend database (i.e., MS Access) and display it in a table using Java Swing-based GUI (i.e., Graphical User Interface) components.

## Requirements
- The program should display a GUI (i.e., Graphical User Interface) which should contain appropriate labels, text fields, buttons, and a table.
- To add a new inventor, the user will provide the name and score in corresponding text fields and click on the "Add Inventor" button to submit. If the input is valid (i.e., name and score are not empty, the score is a positive number between 1 and 100, and the same inventor does not exist in the database), the data should be added to the backend database. However, in case of invalid input, the user should be prompted accordingly.
- By clicking on the "Load Data" button, the program should fetch inventors' data in ascending order with respect to inventor name from the backend database and display it in a table.
- When clicking on the "Delete All" button, the program must remove all inventors' data from the table as well as from the database. However, the user should be prompted first before performing the delete process.
- The "View Winner" button should be responsible for fetching the top three inventors from the database based on the highest score and displaying them in the table.
- Further, the cross icon at the top right corner of the interface should terminate the program and show the developer information (i.e., Student ID and name) through a message dialog.

## Developer Information
- Student ID: [Your Student ID Here]