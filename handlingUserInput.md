# Handling user input #

(also see [Password input text field](PasswordField.md))

There are several terms having to do with this in libGDX:

InputProcessor

InputAdapter

InputMultiplexer

You can set the handler-in-charge by
```java

Gdx.input.setInputProcessor(InputProcessor);```
Where InputProcessor might be one of the following

  * new InputMultiplexer(this)
  * new InputMultiplexer(this, stage)
  * new MyOwnFancyController(in) <- this could e.g. go to a new class implementing InputProcessor

You can get the "global"(???) InputProcessor by

```java

Gdx.input.getInputProcessor()```


Other InputProcessors can be added to a InputMultiplexer by

```java

inputMultiplexer.addProcessor(SomeClassName);```

## Using multiple input processors with wrapper and a test collection ##

**Example Setup:**

(This setup is taken from the demos-3-tier**packages,
see http://code.google.com/p/libgdx-users/source/browse/)**

  1. A wrapper as base class, e.g. "DemoWrapper".
  1. A collection class of apps or (in our case tests), e.g. "TestCollection".
  1. some tests each having individual input needs but all sharing the app's input method. E.g. "IsoCamTest".

The base wrapper has to implement ApplicationListener as well as InputProcessor. You can simply add multiple interfaces with colons:

```java
public class DemoWrapper implements ApplicationListener, InputProcessor {
// stuff
}```

The test collection has to implement InputProcessor.

A class member of type InputMultiplexer is added and for each test created there is an InputProcessor added in create() method:

```java
public class TestCollection extends DemoWrapper implements InputProcessor {
private InputMultiplexer inputMultiplexer = new InputMultiplexer(this);

// list of available tests in collection
private final DemoWrapper[] tests = {
new DecalWall(),
new PhysicsTest(),
new DecalTest(),
new IsoCamTest()};
// stuff
@Override
public void create() {

if (this.app == null) {
this.app = Gdx.app;
app.getInput().setCatchBackKey(true);
DemoWrapper test = tests[testIndex];
test.create();
inputMultiplexer.addProcessor(tests[testIndex]);
}

Gdx.input.setInputProcessor(inputMultiplexer);
}
// stuff
}```


Every single test of the collection has to implement InputProcessor as well!

A class member of type InputMultiplexer is added and the "global" InputProcessor is fetched in create() method.

After this, the test's own InputProcessor is added to the InputMultiplexer. In this special case of IsoCamTest there is a custom InputProcessor called "IsoCamController" which is implemented as an inner class extending InputAdapter:

```java
public class IsoCamTest extends DemoWrapper implements InputProcessor{
InputMultiplexer inputMultiplexer;

@Override
public void create () {
// stuff
inputMultiplexer = new InputMultiplexer(Gdx.input.getInputProcessor());
inputMultiplexer.addProcessor(new IsoCamController(cam));
Gdx.input.setInputProcessor(inputMultiplexer);
}

// stuff

// by extending InputAdapter you only have to implement
// those methods you are interested in and not all of them.
public class IsoCamController extends InputAdapter {
@Override
public boolean touchDragged (int x, int y, int pointer) {
// stuff
}
@Override
public boolean touchUp (int x, int y, int pointer) {
// stuff
}
}

}```