# Mixing Android interface with libGDX 2D / 3D i.e. organizing a user interface #

This is a recurring question on the forum.


## FAQ ##

**People asked either these questions ...**

  * Is libgdx designed to be rendered in full windowed mode only?

  * Is there a way to create a libgdx compatible surface fitting in a JPanel / View ?


**... or they used these topics ...**

  * using libgdx in a JPanel / a View

  * mixing 2D + 3D

  * mixing a libgdx window into a normal Intent based app

  * Using the 2D UI

  * how to draw Button class in libgdx


**.. or they face these tasks ....**

  * I need to position other GUI elements on the window, and to be able to layout the OpenGL Scene like any other GUI element in the window at the same time.


## Solutions ##

### TableLayout ###

http://code.google.com/p/table-layout/


### mixing 2D + 3D ###

http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1615&p=9122&hilit=Camera#p9122


### TWL ###

TWL needs orographic camera setup to work correctly. Example code to render TWL:<br />
```java
batch.getProjectionMatrix().setToOrtho(0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 1);
twl.render();```

Setting the projection matrix to a camera just before rendering the world:<br />
```java
camera.update();
camera.apply(Gdx.graphics.getGL10());
batch.getProjectionMatrix().set(camera.combined);```

see: http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1870

<b>Tutorial on how to use TWL with libGDX:</b><br />
http://dpk.net/2011/03/10/libgdx-and-twl/

<b>Source code for TWL in libGDX:</b><br />
http://code.google.com/p/libgdx/source/browse/trunk/extensions/twl/gdx-twl/src/com/badlogic/gdx/twl/TWL.java


### libGDX UITest ###

http://code.google.com/p/libgdx/source/browse/trunk/tests/gdx-tests/src/com/badlogic/gdx/tests/UITest.java


### mixing a libgdx window into a normal Intent based app ###

http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1610


### Using the 2D UI ###

http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1672


### how to draw Button class in libgdx ###

http://stackoverflow.com/questions/6217537/how-to-draw-button-class-in-libgdx


Q: "Is libgdx designed to be rendered in full windowed mode only, or is there a way to create a libgdx compatible surface fitting in a JPanel/View ?
I'm asking this because I'm considering using this library, which looks good, but I need to position other GUI elements on the window, and to be able to layout the OpenGl Scene like any other GUI element in the window."

Mario: "On Android you can use AndroidApplication.initializeForView(). On the desktop you can integrate libgdx as a widget via LwjglCanvas or LwjglFrame. See projects in the extensions folder on how to do this, e.g. box2d editor or hiero or the particle editor."


... to be continued



# Credits #

Thanks for your contributions:

  * MattWay