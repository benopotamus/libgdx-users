# Integrating native Android UI elements #

(also see: [Android native ui elements with 3 tier project setup](IntegratingAndroidNativeUiElements3TierProjectSetup.md))



Calling Android native UI elements or raising Actions/Intents from libGDX context needs a special setup.
The Android native (UI) elements live within the UI thread which runs in parallel to libGDX's GL thread. These two threads do not know each other, yet.

You have to apply a handler to each of the threads to be able to pass it to the other thread as reference of context.

<b>This was described in zeke's and mzechner's thread in the forum:</b><br />
http://www.badlogicgames.com/forum/viewtopic.php?f=21&t=1298
(also see http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1357)

Unfortunately this was not clear enough for me, so I tried to describe it here after fiddling it out...

For easier access and better understanding I am repeating the info from forum with annotations in the following.

<b>This is what you would call an Android Toast message in a normal Activity:</b>

```java
public class !MyActivity extends Activity {
// ...
Toast.makeText(this, "This is a Toast message.", Toast.LENGTH_SHORT).show();
}```

<b>And this is what raises an Intent in a normal Activity:</b>

```java
Uri uri = Uri.parse("http://www.google.com");
Intent intent = new Intent(Intent.ACTION_VIEW, uri);
startActivity(intent);```


Unfortunately this is not possible from your libGDX app as it does not know about the static Android context Toast will need instead of passing "this". BUT you can post the Toast message from your libGDX GL/Game thread to the UI thread as a "Runnable"!

First, you have to create a handle object within the Android UI thread (= the Android Activity).

You can then use the handle.postRunnable() method to post a Runnable to the UI thread like this

```java
game.handler.post(new Runnable() {
public void run() {
Toast.makeText(game, "This is a Toast message.", Toast.LENGTH_SHORT).show();
}
});```




<b>Android developers page on Handler class:</b><br />
http://developer.android.com/reference/android/os/Handler.html


## sending message UI --> render thread ##

Application.postRunnable() will will post a Runnable on the rendering thread.

```java

private Handler handler;
private Runnable countDown = new Runnable() {

@Override
public void run() {
// do something
handler.postDelayed(...);
// do something
}
};```



# I want toast! #

If it is only toast you are looking for, maybe Syl has developed the right tool for you. He wrote about it some time ago in the forum. He simulates Android native toast message with libGDX calls only:

http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=484&p=3094&hilit=toast#p3094

The <b>native</b> solution asks for the technique mentioned in the first section or (with 3-tier project setup as described in [Android native ui elements with 3 tier project setup](IntegratingAndroidNativeUiElements3TierProjectSetup.md))