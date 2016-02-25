# Dealing with Garbage Collection #
(with contributions from Obli by IRC and forum)

## What is the Garbage Collection ##

The Garbage Collection (GC) is the process of deleting unused objects.
Since there is no command to free or delete an object in Java, the only way to delete objects is to simply "forget" them.

## Why avoid Garbage Collection ##

The Garbage Collection itself is a good guy, with bad attitudes :P
Running the Garbage Collection is slow, and can stop your whole application for several milliseconds! (~ >50ms)
These small "hiccups" can greatly reduce the responsiveness of the controls, and thus make game feel laggy.

## Finding out when the Garbage Collection is run ##

Note: This only works on a phone or emulator, not on the PC!

Android is kind enough to print some text inside the LogCat everytime the Garbage Collection is run.
The actual text varies from Android Version to Android Version, but if you perform a search for 'GC' or 'gc', you should find out how it looks on your phone.

## Avoiding Garbage Collection ##

There are many different ways to implement a Garbage Collection, and each VM (e.g. JavaVM,DalvikVM) can have its own way of dealing with it. So finding out what triggered the GC is not always easy.

**But** there is a rule of thumb that can always be applied:
Don't create objects during performance critical sections!

So, you should try to allocate everything you need before the user can play. So, every time the level changes, allocate everything you'll need and don't allocate anything else when its the users turn to play.

## Finding out when objects are created ##

Sometimes finding out when objects are created is hard.

Example:
Whats wrong with the following code?

```java
for(Entity current : entityList)
current.logic();```
(It is using the enchanted loop because the android documentation is telling us to: [Android Developer Docs](http://developer.android.com/guide/practices/design/performance.html#foreach))

On the first look, everything is okay, but there is actually an memory allocation right in front of your nose (if using the Java List implementation)

Everytime this loop is entered, an ListIterator gets created.
Assuming this code is run in every frame, and our game runs at 60fps, the game would allocate 60 short-lived objects in every second! (And this is bad!)

But have fun finding this out when you have no clue about the actual List implementation used.

Luckily there is a tool inside the Android-SDK (Android only, again) that saves us from lots of headaches called "Allocation Tracker"

Describing how its used would go beyond the scope of this article, so here are links:

A little bit older but still valid: [Track memory allocations](http://android-developers.blogspot.com/2009/02/track-memory-allocations.html)

And a more in detail look: [Memory Analysis for Android Applications](http://android-developers.blogspot.com/2011/03/memory-analysis-for-android.html)

## How to trigger the Garbage Collection manually ##

Sometimes you want to trigger the Garbage Collection by yourself,
for whatever reason there is.

**But** you can't force the GC to run!
Luckily, you can give a "hint" to the runtime engine that now might be a good situation to run the GC.

Use this code to do so:
```java
System.gc();```
(Note: also works on the desktop!)