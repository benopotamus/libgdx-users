# Color Picking #

Color picking is an algorithm for determining which object on screen has just been interacted with (e.g. touched or clicked). This is essential for the selection task.

## How it works ##

Every object in the scene is given a unique and solid color. This is rendered in background without being displayed on screen.

If an interaction (onTouch / onClick) was performed the color of the affected pixel is read out (on Android by getPixelColor()).
From this color the object can be identified.

LibGDX implements a [ray casting picking algorithm](pickingRayCasting.md).