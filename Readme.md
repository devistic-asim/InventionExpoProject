# Invention Expo Project in JAVA

## Overview
The Invention Expo Project is a Java program designed to identify the top inventor in an Invention Expo Competition using object-oriented principles. The program utilizes `JOptionPane` for user input and output, and implements an inner class to manage inventor details.

### Problem Statement
You are required to develop a Java program to identify the top inventor in an Invention Expo Competition. The program will prompt the user to enter the number of inventors participating in the competition. It will then allow the user to input the details of each inventor, including their name and the score of their invention. After all participant data is entered, the program will determine the top inventor based on the highest invention score. The program should be user-friendly and based on graphical interfaces.

### Requirements
- The program should prompt the user to enter the number of inventors participating in the Invention Expo competition (any valid number greater than zero).
- For each inventor, the program should prompt the user to input the following information:
  - Name of the inventor (string)
  - Score of the invention created by the inventor (number between 1 and 100)
- Both the name and score should not be empty, and the score must be a positive number between 1 and 100.
- Store the information of each inventor in an `ArrayList` of type `Inventor`.
- Implement a method named `findTopInventor()` that takes an `ArrayList` of `Inventor` objects as input and returns the inventor with the highest invention score.
- The program should determine and display the name and invention score of the top inventor, i.e., the inventor with the highest invention score (closer to 100).
- Use `JOptionPane` for both input and output.
- Ensure that exceptions, especially `NumberFormatException`, are managed properly throughout the program when taking input from the user.
- At the end, show the developer info (i.e., student ID) as well.

### Developer Information
- Student ID: [Your Student ID Here]

## How to Implement and Compile in Java IDE

### Step 1: Copy the Code
Copy the provided Java code for the Invention Expo project.

### Step 2: Create a New Java File
1. Open your preferred Java IDE (e.g., Notepad, NetBeans, IntelliJ IDEA).
2. Create a new file named `InventionExpo.java`.
3. Paste the copied code into this file.

### Step 3: Compile the Code
If you are using Command Prompt:
1. Navigate to the folder where your `InventionExpo.java` file is located.
2. Run the following command:
   ```bash
   javac InventionExpo.java
If there are no errors, an InventionExpo.class file will be created.

### 4: Run the Program
In the Command Prompt, run the program using:
java InventionExpo

