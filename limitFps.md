# time-based movement / limit framerate (fps) #

Good advice right at start: You should never code anything dependent on the framerate! Instead it is a good idea to make it depend on the time e.g. one of these:

```java

System.currentTimeMillis();
System.nanoTime();
Gdx.graphics.getDeltaTime();
```

...or use Thread.sleep, to avoid killing your cpu.

http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1914

There is a Gdx.graphics.setVsync() method (generic = backend-independant), but it is not present in 0.9.1, only in the [Nightlies](Nightlies.md).


"Relying on vsync for fixed time steps is a REALLY bad idea. It will break on almost all hardware out there.

See LwjglApplicationConfiguration, there's a flag in there that let
s use toggle gpu/software vsynching. Play around with it." (Mario)

NOTE that none of these limit the framerate to a specific value... if you REALLY need to limit the framerate for some reason, you'll have to handle it yourself by returning from render calls if xxx ms haven't passed since the last render call.


## in LWJGL ##

Lwjgl backend has a sync method (not vsync) that will attempt to limit the frameRate to a specific value - but that's the only one.

If lwjgl is your target, there is a Display.sync(..) method.

On lwjgl backend, setVsync calls Display.setVSyncEnabled(true), which should also limit to monitor refresh rate.

## in JOGL ##

If jogl is your target, you will have to put the deltaTime into an accumulator variable and skip render calls until the accumulator has a certain value, i.e.:

```java

if (accum < 1 / 60.0f) return;
```

On jogl backend, setVsync calls GL.setSwapInterval(1), which should limit the frame rate to the monitor refresh rate.

## on Android ##

Some android devices cap fillrate at 60fps, some others at 80fps, some at 50fps, and finally some at 30fps.


# Credits #

Thanks for your contributions:

  * Eric (via IRC)
  * Obli (via IRC)
  * Shiu (via IRC)
  * mzechner (forum)