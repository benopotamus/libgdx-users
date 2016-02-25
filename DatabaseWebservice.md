# Connecting to database (via webservice) #

Use case: Publishing Highscores


For security(?) reasons you shouldn't ever connect to the database directly through the device. Set up a web service with an API for fetching and updating user high scores instead!

something in the flavor of:<br />
```

GET mywebsite.com/androidgame/highscores/
POST mywebsite.com/androidgame/highscores/?user=<androidUserId>&hash=<someVerificationHash>&score=<score>```


# Resources #

http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1567

PHP, JSON, MySQL: http://www.helloandroid.com/tutorials/connecting-mysql-database

Java: http://vaadin.com/home



# Security issues #

...