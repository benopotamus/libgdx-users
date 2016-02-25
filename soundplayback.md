# soundeffects #

Every good game needs soundeffects! I'm going to show you how to load them an play them back.


## loading soundfiles ##

To load a soundfile you need a variable of the type "Sound".
For example:
```java

public static Sound scream;
```

Then you can use it to load the soundfile from your filesystem.
```java

scream = Gdx.audio.newSound(Gdx.files.internal("sounds/scream.ogg"));
```
In this case, the file "scream.ogg" must be placed in an "sounds" folder inside your assets folder.

## playback ##

Now, to playback the sound you just need to call:
```java

scream.play();
```

### volume ###
If you want to set volume you can call it like this:
```java

scream.play(0.5f);
```
The value in the () must be a float between 0 and 1. (0 = silence, 1 = very loud)

## dispose ##
You should dispose every sound after it's been played back:
```java

scream.dispose();
```