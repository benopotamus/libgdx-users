# Launching libGDX from Android #

LibGDX "pages" can be launched in Android environment as views.
This could be either a part of the screen with a custom view or an application like look by using the default standalone (fullscreen) view.


## Launch as custom view ##

This way libGDX content can be embedded in an Android Layout.
How to do this is described in the official LibGDX Wiki (shows how [AdMob](adService.md) is integrated):

http://code.google.com/p/libgdx/wiki/AdMobInLibgdx




## Launch as "Application" (i.e. fullscreen view!) ##

This approach lets the libGDX content be started by Android UI elements but afterwards presented exclusively (even as real fullscreen without the status bar if you declare this in AndroidManifest.xml or use the appropriate setter in code).

See "Setting Up an Android Project":
http://code.google.com/p/libgdx/wiki/MyFirstTriangle

Basically it is this one line that starts any class that is implementing ApplicationListener:

```java
initialize(new !MyLibgdxApplication(), false);```


Also see section "Background" on how this works behind the scenes:

http://code.google.com/p/libgdx/wiki/AdMobInLibgdx