libGDX has APIs for reading files. since most apps work on both the PC and android, there are a couple of hoops to jump through

[full example code](http://libgdx.googlecode.com/svn/trunk/tests/gdx-tests/src/com/badlogic/gdx/tests/FilesTest.java)

## PC vs android ##
```
if (Gdx.app.getType() == ApplicationType.Android)
```

## reading files ##

## reading directories ##

Q: Is it possible to get a list of files inside the assets folder of an apk while running on Android?

A: Yes, the File library has a method that reads files- if your pointer happens to be a dir you get a directory listing instead.

Asset folders can be read. The Android ressource folder res/ is protected and can't be accessed this way.

How about something like Gdx.files.internal("/").list(); ?


## reference ##