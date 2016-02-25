# Preventing dimming and sleeping #

Whether you're designing an application or a game for Android you will often need to the screen to remain in an "on" state so as not to interrupt the experience you are providing. While many applications/games won't need this feature due to specific user interactions keeping the screen on, there are certain interactions that do no affect the screen's "on" state, or even the interaction is so limited that enough time can pass and cause the screen to dim.

There are two general ways you can instruct the screen to stay on and that is through using WAKE-LOCKS (requires manifest permission) and also through a special flag that the Android SDK provides.

# Flag Method #
I will discuss the flag first as it fits the bill for most apps/games.
What this "flag" will do is keep the screen in it's fully lit state (not sure if auto brightness or user-defined brightness affects this) which is active when your app/game is displayed to the user, is temporarily disabled when the app/game is paused (home button pressed or screen is locked) and is released completely when the app/game is exited(often via "back" button).

To utilise this method all you need is this code snippet:

`getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);`

This piece of code needs to be placed in the "onCreate" method in your default activity.
Here it is in one of my applications:

`@Override`
> `public void onCreate (Bundle savedInstanceState) {`
> > `super.onCreate(savedInstanceState);`
> > `getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);`

Naturally there is a lot more to my activity code there, but this should give you an idea of where you need it if you are new to libGDX or Android etc.

# WAKE LOCK method #
So why would you need another method to keep the screen on you might ask?
Well there are certain circumstances where you may wish to keep the screen on, but control it's brightness directly (lower brightness = less battery consumption). The previous method might not work in some cases for whatever reason too.
This method uses what is called a "WAKE-LOCK" which requires a permission in the manifest file:

`<uses-permission android:name="android.permission.WAKE_LOCK"></uses-permission>`

You will need to put your code in the default activity once again, but there is a bit more for this method.

Firstly import these:
`import android.os.PowerManager;`
`import android.content.Context;`

Then place this variable before the "onCreate" method:
`private PowerManager.WakeLock wl;`

Then in your "onCreate" method place this:
`PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);`

> `wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");`

Finally you need the "onPause" and "onResume" to deal with the release and acquiring of the wake lock so that the screen behaves normally outside of your application:

`@Override`
`protected void onPause()`
`{`
> `wl.release();`

> `super.onPause();`
`}`

`@Override`
`protected void onResume()`
`{`
> `wl.acquire();`

> `super.onResume();`
`}`

Finally here is an example using all of the WAKE LOCK code above:

`import android.content.Context;`
`import android.os.Bundle;`
`import android.os.PowerManager;`

`public class AmbientSoundsAndroid extends AndroidApplication{`
> `private PowerManager.WakeLock wl;`
> > `@Override`
> > > `public void onCreate(Bundle savedInstanceState) {`
> > > > `super.onCreate(savedInstanceState);`



> `initialize(new AmbientSounds(this), false);`

> `PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);`
> `wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");`
> `}`

`@Override`
`protected void onPause()`
`{`
> `wl.release();`

> `super.onPause();`
`}`

`@Override`
`protected void onResume()`
`{`
> `wl.acquire();`

> `super.onResume();
`}`

You may notice that I have requested "SCREEN\_DIM\_WAKE\_LOCK" here. If you need the screen to remain bright, simply replace it with "SCREEN\_BRIGHT\_WAKE\_LOCK".

Forum topic on this:<br />
http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=174

Summarised by **Alkaline Labs**


# Credits #
AlkalineLabs,
wizlon,
mlipman,
BurningHand