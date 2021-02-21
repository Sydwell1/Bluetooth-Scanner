### Challenge 3 

# Approach 
To solve this challenge i started by dividing the problem in to 4 sub-problems

	1. Problem understanding, planning and development environment setup
	
		The aim of this stage was to read up more on the problem and identify the requirements, success criteria and constraints associated with the challenge. After getting some clarity on the problem, i started setting up the development environment which includes:  
			* downloading and installing Android Studio. 
			* Choosing a development language and Minimum SDK.
 			* Setting up the Github CI test pipeline. 
			
	2. User stories and User interface design 
	
		The aim of this stage was to design a simple and intuitive User Interface for the application. It was designed to accommodate the following user stories:
			* The user must be able to switch on the Bluetooth using the application.
			* The user must be able to see available Bluetooth connections after. switching on the Bluetooth 
			* The user must be able to get the list of devices paired to their device 
			* The user must be able to switch off the Bluetooth using the application 
			
	3. 	User stories implementation 
		The aim of this stage was to follow a CI approach to implement the forementioned User stories, which includes:
			* Coding and committing each story 
			* Build and Test each feature both locally and on Github
		
	4. Functionality Testing 
		After each user story was completed, i used both the Android studio phone emulator and my physical phone to test if the application was functioning as stipulated from the story.
			
			
# Problems encountered 

	1. Problem: The first problem i came across a remote test failure (./gradlew: Permission denied).
		Solution: I did some reading on the type of error in Stack Overflow and went back to debug my code, and granted Grelew the permissions required by running the command chmod +x gradlew on my local machine

	2. Problem: After implementing the first user story i build and tested the application locally, everything was successful, however, when i was running the remote tests, the application failed to build
	   Solution: I ran the application on my phone to see if the error might be terminal, only to find out it was running smoothly. I then went back to read more on the problem and debug my code. I found out that the errors were coursed by some miss-translations in the strings.xml file. I fixed that and everything was running smoothly.    

# Application info
	The application runs on devices with the API 20 Android 4.4W (KitKat) operating system upwards, which accommodate approximately 98% of smartphones
	Java v-1.8 is the used development language 
	Gradlew  is used for the remote test and assembling suit of the application 
	

	Please see the git repository link to see the challenge solution overview https://github.com/Sydwell1/Bluetooth-Scanner.git
	The CI test suit file is located in .github/workflows/bluetooth-scanner.yml
	The main application file is located /app/src/main/java/com/bluetoothscanner/MainActivity.java
