# Scene2d #
(also see [Scene2d UI](Scene2dUI.md))

"official wiki article": http://code.google.com/p/libgdx/wiki/scene2d

A 2D-scenegraph implementation for quick creation of 2D scenes.

<b>Update:</b> scene2d was refactored lately. See the changes on Mario's blog:<br />
http://www.badlogicgames.com/wordpress/?p=2139

and lately

http://www.badlogicgames.com/wordpress/?p=2181<br />
http://www.badlogicgames.com/wordpress/?p=2195





# Example usage #

http://code.google.com/p/libgdx/source/browse/trunk/tests/gdx-tests/src/com/badlogic/gdx/tests/StageTest.java


# Elements #
(some snippets taken from libGDX javadocs)


## Stage ##

A Stage is a container for <b>Actors</b> and handles distributing touch events, animating Actors and asking them to render themselves. A Stage is basically a 2D scenegraph with hierarchies of <b>Actors</b>.

http://code.google.com/p/libgdx/source/browse/trunk/gdx/src/com/badlogic/gdx/scenes/scene2d/Stage.java

Stage has a Camera. This can be used for e.g. zooming and moving the viewport over your scene (also in combination with [TweenEngine](Tweening.md).<br />
http://www.badlogicgames.com/wordpress/?p=1797


## Actor ##

<b>Actors</b> can be put on <b>stage</b> (I like this metaphora).

An <b>Actor</b> is part of a <b>Stage</b> or a <b>Group</b> within a Stage. It has a position, a rectangular size given as width and height, a rotation angle, a scale in x and y and an origin relative to the position which is used for rotation and scaling.

Actors have a "visible" flag that toggles an actor's visibility.

All actor's input methods are public.

<b>Some of Actor's methods:</b>

```java

public abstract Actor hit (float x, float y);
public abstract boolean touchDown (float x, float y, int pointer);
public abstract void touchDragged (float x, float y, int pointer);
public abstract void touchUp (float x, float y, int pointer);```


http://code.google.com/p/libgdx/source/browse/trunk/gdx/src/com/badlogic/gdx/scenes/scene2d/Actor.java


<b>See the list of available Actors:</b><br />
http://code.google.com/p/libgdx/source/browse/trunk/gdx/src/com/badlogic/gdx/scenes/scene2d/#scene2d%2Factors

## Group ##

A group is an Actor that contains other Actors (also other Groups which are Actors).

http://code.google.com/p/libgdx/source/browse/trunk/gdx/src/com/badlogic/gdx/scenes/scene2d/Group.java


## Action ##

An Action is used with an <b>Actor</b> and modifes the <b>Actor</b>'s attributes over time.

http://code.google.com/p/libgdx/source/browse/trunk/gdx/src/com/badlogic/gdx/scenes/scene2d/Action.java

<b>See the list of available Actions:</b><br />
http://code.google.com/p/libgdx/source/browse/trunk/gdx/src/com/badlogic/gdx/scenes/scene2d/#scene2d%2Factions


## AnimationAction ##

An <b>AnimationAction</b> performs a transformation on its target <b>Actor</b>. These transformations physically change the <b>Actor</b> itself.

http://code.google.com/p/libgdx/source/browse/trunk/gdx/src/com/badlogic/gdx/scenes/scene2d/AnimationAction.java


## CompositeAction ##

A base class for composite actions which deals with multiple child <b>Action</b>.

http://code.google.com/p/libgdx/source/browse/trunk/gdx/src/com/badlogic/gdx/scenes/scene2d/CompositeAction.java


## TemporalAction ##

A <b>TemporalAction</b> controls an <b>Action</b> by repeating, delaying etc. the effect of an <b>Action</b>.

http://code.google.com/p/libgdx/source/browse/trunk/gdx/src/com/badlogic/gdx/scenes/scene2d/TemporalAction.java


## Interpolator ##

An interpolator defines the rate of change of an animation. This allows the basic animation effects (alpha, scale, translate, rotate) to be accelerated, decelerated etc.

http://code.google.com/p/libgdx/source/browse/trunk/gdx/src/com/badlogic/gdx/scenes/scene2d/Interpolator.java

<b>See the list of available Interpolators:</b><br />
http://code.google.com/p/libgdx/source/browse/trunk/gdx/src/com/badlogic/gdx/scenes/scene2d/#scene2d%2Finterpolators


# Movement #

Q: How can I move Actors on stage in scene2d?

A1: In your ApplicationListener call ...

```java
stage.act(Gdx.graphics.getDeltaTime());
stage.draw();```

stage.act will call all the Actor.act() method in all actors in the stage, which in turn will make sure all Actions are executed.

A2: You could refer to ActionTest in gdx-test project. That's the best practise of Action associated with Actor.

http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1856


# Is anybody listening? Time for some action! #

Interaction with Stage elements is possible by registering your Stage as the current InputProcessor. LibGDX framework will then call the element's touch handlers.

You can register a stage as your current input processor like this:
```java
Gdx.input.setInputProcessor(stage);```

Q: How do the actor´s touch events get called? They have touchable parameter and they implement touchable functions but nothing calls them?!

A: LibGDX framework's native code listens for actual events, that code then calls the input processor that's currently registered (in this case your Stage)
the Stage calls it on it's groups, they call it on their actors.

touchDown, for example, gets passed to the root group, which then goes through its children like this:

```java
if (child.hit(point.x, point.y) == null) continue;
if (child.touchDown(point.x, point.y, pointer)) { ... }```

touchDown() will be called on all objects until one of them returns true.


All you need to do is to create a Stage instance and register it with any of these:
```java
Gdx.input.setInputProcessor(yourStageInstance);
Gdx.input.setInputProcessor(this);```

Example code for all input events passed directly to the Stage (and nowhere else):
```java
ui = new Stage(800, 480, false);
Gdx.input.setInputProcessor(ui);```

Then it will pass touch events to it's children (the Groups/Actors).
Touch events are passed to the Stage by Gdx.input which interfaces with the OS, which is why you have to register the stage as an InputProcessor.


# Credits #

Thanks for your contributions:

  * Mario Zechner
  * Wesker
  * Eric S.
  * mcortez