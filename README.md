# FinancialNINJA---40270305---Karol_Pasierb
This application is inspired by great financial books, guides for personal finance. A lot of valuable knowledge could be obtained. With a lot of great inspirational quotes displayed on the phone, user will be constantly reminded to stay on track and don’t give up.  There could be a possibility of expanding this application into something bigger, like a platform for the books’ users, some sort of a tool supporting reading similar books and articles, etc. This project could be a great platform for likely minded people.

This application is inspired by the Polish book “Financial Ninja” by Michał Szafrański 1 and “Total Money Makeover” by Dave Ramsey 4. These books are guides for personal finance and a good description of the path everyone should follow. A lot of valuable knowledge could be gained from them: tips about how to run home budget, how to beat debts and to successfully avoid them, a lot of general tips about finances and how to plan future. This application could be nice addition to the books. This idea was abandoned as the new concept fits even better to the financial awareness growth. With a lot of great inspirational quotes displayed on the phone, user will be constantly reminded to stay on track and don’t give up.  There could be a possibility of expanding this application into something bigger, like a platform for the books’ users, some sort of a tool supporting reading similar books and articles, etc. This project could be a great platform for likely minded people.

From the main window user can create account, log in or read about the app. Read about page will show user the description of an app and then user can either go back or create new account. On creating account, user need to choose log in, give email address, create and confirm password. Account details will be stored in a database on a server. There is also an option to explore account creation with Gmail if that would be easy to do. Log in screen simply authorizes access to an app with the appropriate credentials.

Next, user is transferred to the welcome home screen that displays the reminder about the Ninja’s path and some motivational content. Then user opens main home page where quotes are being displayed and they change on the time rotation basis. This screen starts the constant notification that cannot be dismissed. Notification contains the quote and the time left to display it. If user quits the app, tap on the notification will resume the main home screen. The time can be set below when user chooses settings. As soon as the new time is set the notification restarts with the new time.

There is a button to add quotes and this can be done as many times as user wishes. Each successful submission shows the toast notification and erases the text boxes so the user can know the action was performed successfully. At the moment, all the quotes are sent to the database and stored there. However, all the quotes that application displays to the user are stored locally. This means that on the fresh installation of the app there will be no quotes to display. This is one of the current biggest disadvantages of the app. Local files are serialized and accessible only for this application. There is no other use for those files and they cannot be used anywhere else. Much better option to focus on for further updates would be to store all the data online and read it from there too.

The final available screen is the list of all existing quotes that user entered to this point. There are a lot of features that can be added to this page with future updates.

Final action user may take on the home screen is to log out. This will take the user back to the first screen of the app and will dismiss the notification. 

At this point of development there are few parts of the application that requires critical review and upgrade or exchange for better features. Such simple app should be available without the need of login and all of the data could still be stored on the server. Another problem to solve would be to allow user to stay logged in for as long as wished and log out only by choice or when another user wants to sign in. 

The display of motivational quotes could be dome much better way than simple notification. Perhaps an email sent on rotational basis or connection to another useful application’s API.

The display of existing quotes needs an overhaul as at this point it is not very good looking plain text list. This could be displayed in much nicer way. Apart from that it should allow editing and deleting existing quotes.

Another useful feature would be to skip current quote displayed and move to the next one. At this point all quotes are displayed in order. Good feature would be to allow them to be shuffled. This would give the user better way o look at them as it would vary.

Good feature that could be added to the program would be to export and import list of quotes with the settings. Either to share with friends, or as a replacement for online storage.

This was an exercise made for school purposes only. Therefore there is no intention of improving it. Now it serves as a portfolio.
