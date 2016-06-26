## SCU COEN 275 Final Project

COEN 275		Object-Oriented analysis, design and programming 	Spring 2016
											
Project (Groups of 1-3 people allowed. Please see the requirements for each team size)



1.0	Introduction
In TechTonic University, students and faculty are issued a CampusCard which provides access to the various facilities on campus, namely, the library, the commons, labs and so on. In addition, a card user may use the card to buy meals from the campus wide Campus Cafes. CampusCafes Inc maintains a number of restaurants/cafes and vending machines all over campus. A card user may use any of the cafes or vending machines to enter (and store) her/his choices, order food, know the calorie content and keep track of his/her expenses.
    CampusSmartCafe (CSC) is a computer-based application that is a simulation of how Campus Cafes work on TechTonic University campus. 
2.0	Functionality
The functionality of the application is described briefly below:
2.1	Display a Campus Map
Display a Campus map showing the location of the cafes, food stores and vending machines.

2.2	Select a cafe
A card user selects a café by pointing and clicking on its location on the map. The selection opens up a window with options to buy/order food, enter preferences and/or view expenses. A user may also select a café from a list of choices (of café names).

2.3	Enter preferences: A user can enter food preferences and/or available funds.
Food preferences
Food preferences can range from daily caloric requirements/restrictions or preference for specific needs such as low sodium, low sugar items etc.
A user can enter the daily calorie intake that should be monitored. The user can also add any special diet requirements (for example, low Sodium, low calorie, low cholesterol etc) that should be monitored. The user’s preferences are saved and used to monitor the restrictions as he/she buys food items.
Available Funds
A user can enter an amount for the available funds (for a month, for example). Every time she/he buys a food item, the purchase amount is subtracted from the available funds.
2.4	View Dietary Profile
A user can select to view his/her dietary profile. A dietary profile uses the food preferences (user preferences for number of calories per day, for example) entered earlier by the user. Whenever the user buys an item, the item’s calories are entered into the profile. The profile shows the user preference (for calories), calories consumed (up to the current date) and the no. of calories left. For example, a user may set a daily caloric requirement to be between 2000-2200 calories. Every time a user buys a food item through Smart Café machines, the item calories are subtracted from the daily requirement and the number of calories left will be displayed.
A graphical representation (using a bar graph, for example) of the calories consumed daily over a period of time (over a month for example) should be displayed. 

2.5	View Expense Profile
A user can select to view his/her expense profile. An expense profile uses an amount for funds (by month) entered earlier by the user. 
Whenever the user buys an item, the funds are adjusted accordingly. A view of the expenses shows the current date, funds amount set for the month ($800 for example), money spent thus far and a balance of funds available. 
A graphical representation (using a bar graph, for example) of the money spent daily over a period of time (over a month for example) should be displayed. 

2.6	 Buy Food from a vending machine
Using the available food choices displayed on the vending machine, the user selects the item, inserts the card (or enters the card number and password) and the item is dispensed; the user’s caloric and expense profile is updated.
2.7	 Order food from a cafe
Using the available cafes on campus, the user selects an item(s) from a specific cafe, inserts the card (or enters the card number and password) and a message is shown where and when the item would be ready for either pick-up or eat-in.  The user’s caloric and expense profile is updated.

2.8	A multi-user Campus Card (required of 3 person teams only)
      A multi-user campus card is issued to several members of a family working on campus. The cards will have the same card number, but each card with a unique 2 digit code. 

3.0 Your application
You are required to design and implement (in Java) an object model of a simulation of CampusSmartCafe (CSC). 
Using your application, it should be possible to demonstrate how the CSC can be configured with user preferences and used to buy food items on campus while monitoring the user’s dietary and expense profiles. 
4.0 Deliverables
4.1 The Analysis and Design document that is to be submitted for the project report should include the following (you will have specific dates for each of the deliverables below).
Use-Cases to illustrate the functionality of the system
CRC cards to show a list of the most important classes and a brief description of their responsibilities. 
Class Diagrams.
Interaction Diagrams for the Usage scenarios.
State Diagrams (if applicable)
The Logical partitioning of the system into Class Components (packages).
Include any assumptions that you make of the system’s functionality.

NOTE: UML notation is to be used. 

4.2 CampusSmartCafe (CSC) (a simulation) is to be implemented in Java, with a Graphic User Interface. A demo of the implementation to the class is required. Source code should be submitted.
Demo dates (10th week) will be posted separately.

5.0 Team Requirements
5.1 Requirements for 1-person teams
Sections 2.1 to 2.6 should be implemented with the following relaxations on the functionality specified.
2.1: Instead of displaying a campus map to allow the user to select a location (of a vending machine), you can display a number of text-based options in a list.
2.5: A bar graph 
5.2 Requirements for 2-person teams
Sections 2.1 to 2.7 should be implemented with the following additions.
2.4 and 2.5: Two different graphical representations (a bar graph and a pie chart, for example) of the expenses and the dietary profile should be provided (more details in class).

5.3 Requirements for 3-person teams
Sections 2.1 to 2.7 should be implemented with the following additions.
2.4 and 2.5: Two different graphical representations (a bar graph and a pie chart, for example) of the expenses and the dietary profile should be provided (more details in class).
2.7: When food is ordered from a vending machine, the locations of the vending machine and the café where the food is to be picked up should be clearly displayed on the campus map. 
2.8: Show a (possibly multi-threaded) implementation where a multi-user card is used from 2 different vending machines. The statistics for the expenses should reflect the fact that it is a multi-user card and show the breakdown of the usage for each card holder.

## How to run

Instructions to load DB data:
1. final_project_load jar file expects dbData folder in same folder from where this jar file is being run.
2. To load data, execute “java -jar final_project_load.jar”
3. This jar will create CampusSmartCafe database and four tables to run application.
4. For loading data and running application, these jar files expect mysql user: sqluser and password: sqluserpw

Instructions to run CampusSmartCafe application:
1. To start application run “java -jar final_project.jar”.
2. We have two user accounts to login. 
Username: manasa, password: pwd3
Username: dhara, password: pwd1
3. dbData folder in our submission contains seed data from June1, 2016 to June7, 2016.
