# Calorie Counter Android Application

**Team Members**: Aaron Huang, Andy Ho, Cameron King, ChanChan Mao, Mathew Kramsch 

#### Description
Calorie Diary is a mobile application that helps track your daily calorie consumption. It’s designed to help users consistently track their calorie intake through a fun, gamified interface. In addition, Calorie Diary offers a leaderboard social media functionality to celebrate your dietary achievements with other people.

#### Audience
This app targets the general demographic of people who are looking to maintain a healthier lifestyle. Since our application allows for users to set a goal of either gaining weight or losing weight, our target demographic includes both overweight and underweight users, as well as people who would just like to maintain a healthy diet and lifestyle.

#### Problem to Solve
This app aims to retain the motivation that people have towards living a healthy lifestyle. People typically get motivated to start something, but often struggle with sticking to their goals consistently and building long-term healthy habits. By turning something as mundane as eating healthy into a game, and building a community of individuals striving for their health goals through the social media aspect of our application, people will be incentivised to consistently achieve their health and fitness goals. Our application also aims to solve the difficulty and hassle that people trying to stick to strict diets face. By making the process of tracking daily caloric intake easier and more insightful, our application helps users make measurable progress towards their health goals.

#### Repository Structure
Java > edu.ucsb.cs.cs184.caloriecounter: Contains the kotlin code for the project 
- UI folder: Contains Controller classes and ViewModel classes for each of the fragments in the app. This follows the ViewModel Structure by performing model view controller separation and organization. 
	- dashboard : Streak History Calendar
	- home: Homepage/User Information Input 
	- leaderboard: Leaderboard/Social Media Aspect
	- logcalories: Calorie Tracking
- Data: Data abstraction 
	- StreakData: Class for updating streaks 
	- User: Data class for storing all of a user's data
	- UserStreak: Data class for linking user name and streak for the leaderboard. 
- AppRepository class: Acts as an intermediary between the ViewModels and the Firebase Realtime Database. 

Res: Contains xml code for the UI and stored values referenced throughout the project. 
- drawable: Contains all icons used in the project. 
- layout: Contains all xml for fragments and views. 
- menu: xml for bottom navigation bar
- navigation: xml for navigating between fragments. 

#### Implementation Details
We used kotlin and xml to develop this application, and utilized a Firebase remote database for our backend to store user data including calorie goals, streaks, and other details. We additionally used Firebases Google Authentication to allow users to login with their existing Google accounts to store and access their user data on multiple devices securely and with ease.

#### Links
[Product Manual](https://docs.google.com/document/d/12e9VtqA195yRl4WJHmGE9InMMcmSgRwnq0iMYBpRaMc/edit) \
[Project Documentation](https://docs.google.com/document/d/1C3jKW0eY5vk2FBU2KxfJt62THZBQ9Zhku9-dq8PkYh4/edit) \
[Project Board](https://github.com/cs184-project-group-05/calorie_counter/projects/2) \
[Figma Design](https://www.figma.com/file/lXWhOFhHbkRhhZmBhvAHbZ/MVP-Design?node-id=0%3A1) \
[Project Idea/Brainstorm](https://docs.google.com/document/d/16ygNktoWMI5ws5bw9VOr405scNEsafuZl3CZEMSH3sw/edit)

#### Emails
Aaron Huang: aaronhuang@ucsb.edu \
Andy Ho: andyho@ucsb.edu \
Cameron King: csking@ucsb.edu \
ChanChan Mao: chanchanmao@ucsb.edu \
Mathew Kramsch: mathewkramsch@ucsb.edu
