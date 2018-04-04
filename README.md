# vocabulary-test

A simple test to measure English vocabulary size written in Groovy

ENVIRONMENT
-----------
- jdk 1.7
- grails 2.4.4
- groovy 2.4.5


RUN THE TEST
------------

To run the test, clone and open the project on Eclipse with Groovy and Grails plugin.

Go to the file WordTest.groovy under src/groovy/com/wordcraft/test, right-click and Run As Groovy Script.
In the console, you will be asked with a series of question whether you know a certain word. Just answer y for yes and n for no.

After the test, you will be given your estimated vocabulary size.

_Note: the word list currently has only 5000 entries, so this test is meant for beginners at the moment._


RUN THE API APP
---------------

The app is configured to run on HSQLDB on DEV and TEST environment and run MySQL on PROD environment. 

- Download and set up MySQL in your environment and create database **wordcraft** for the app. 
- Update Config.groovy under production environment the MySQL username and password that you set up.
- From the project folder, run **.\grailsw run-app** to start the app on DEV environment or **.\grailsw prod run-app** to start in PROD enviroment. 


