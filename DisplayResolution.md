# Display resolution #

(also see [AspectRatio](AspectRatio.md))

<b>Resolution:</b> A display is composed of pixels, laid out in a grid. Many Android phones have a display with 800 pixels per row and 480 pixels per column. The resolution is then given as 800x480 pixels. When working with OpenGL you are not bound to that physical display resolution but can perform some tricks to work with any resolution you want.


Q: How can I set / change the resolution of my app e.g. with an orthographic camera?

A: If you are using desktop target, it's in the constructor:

```java

new !JoglApplication(new !MyLibgdxApplication(), "title", 480, 800, useGL20);```

Then in the OrthoGraphicCamera just use Gdx.graphics.getWidth() and Gdx.graphics.getHeight() for the width and height.

<b>Android target:</b><br />
Well, if you want to force 480 x 800 display on every device, im guessing that you don't want to worry about different resolutions and just stretch your 480 x 800 to fit every window? If that's the case, don't use Gdx.graphics.getWidth()/getHeight() and just use a hard coded 480 x 800.



Q: My app seems to be forced on 480x320 which is a standard resolution in Libgdx, why is that?

A: targetSdk needs to be set in AndroidManifest.xml, to highest number available (ATM it is 13).

```xml

<uses-sdk android:minSdkVersion="4" android:targetSdkVersion="13" />```

Without a targetSdk it will assume it's only tested on devices that have 480x320, so it runs in 'compatibility mode' on higher sdk levels:

http://developer.android.com/guide/topics/manifest/uses-sdk-element.html


# Credits #

Thanks for contributions:

  * chuckDiesel (forum)
  * user\_id (forum)
  * mzechner (forum)