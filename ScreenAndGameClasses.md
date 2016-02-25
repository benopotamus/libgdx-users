_import com.badlogic.gdx.Game;_

_import com.badlogic.gdx.Screen;_

# Using Screen and Game Classes #
There is some confusion about the Game and Screen classes.  Part of that may be because the Project Setup tutorial in the official libgdx wiki has you creating a Game class, which is also different than the com.badlogic.gdx.Game class.

## Details ##

When you start developing a libgdx app, you will set up your project to where the Android starter (AndroidApplication class) and/or Desktop starter (LwjglApplication or JoglApplication) projects initialize an ApplicationListener. The ApplicationListener's methods can be managed by using Game and Screen.

### com.badlogic.gdx.Game ###
We create a class that extends Game, which implements ApplicationListener.  It will be used as the "Main" libgdx class, the starting point basically, in the core libgdx project.  Your create() method is here, and once create() is done it moves on to rendering screens, (the current screen, that is).  To switch the current screen, all you need to do is call game.setScreen(screen);

### com.badlogic.gdx.Screen ###
We create our own classes that implement the Screen interface, and use them kind of like mini ApplicationListeners. They have render(), pause(), resume(), and resize(), all called when the ApplicationListener's respective methods would normally be called.  They also have other methods- hide() and show().

When setScreen(screen) is called, the current screen's hide() method is called, the new screen becomes the current screen, and its show() method is called.  Then the new screen's render() method is called each frame by Game.  Only one screen is rendered at a time.

_Note that Screen's dispose() method is never called automatically- when ApplicationListener's dispose() method is called, Game calls screen.hide() instead._

### Tips ###
I prefer to keep a single instance of each screen around for the life of the game, so that new Screen objects are not being created every screen change, to avoid garbage collection.

Each Screen should have a reference to your Game, if for no other reason than to switch screens from within a screen.

# Game code example #
```java

import com.badlogic.gdx.Game;

public class MyGame extends Game {

MainMenuScreen mainMenuScreen;
AnotherScreen anotherScreen;

@Override
public void create() {
mainMenuScreen = new MainMenuScreen(this);
anotherScreen = new AnotherScreen(this);
setScreen(mainMenuScreen);
}
}
```

# Screen code example #
```java

import com.badlogic.gdx.Screen;

public class MainMenuScreen implements Screen {

MyGame game; // Note it's "MyGame" not "Game"

// constructor to keep a reference to the main Game class
public MainMenuScreen(MyGame game){
this.game = game;
}

@Override
public void render(float delta) {
// update and draw stuff
if (Gdx.input.justTouched()) // use your own criterion here
game.setScreen(game.anotherScreen);
}

@Override
public void resize(int width, int height) {
}

@Override
public void show() {
// called when this screen is set as the screen with game.setScreen();
}

@Override
public void hide() {
// called when current screen changes from this to a different screen
}

@Override
public void pause() {
}

@Override
public void resume() {
}

@Override
public void dispose() {
// never called automatically
}
}```