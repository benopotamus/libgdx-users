# Handling different aspect rations #

(also see [DisplayResolution](DisplayResolution.md))


e.g. with a camera:

http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1940


Forum: Frustum v Display size<br />
http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=860&p=4965

## Mario's summary ##

  * Define your world system units (e.g. meters)
  * Define your objects within this system
  * Define your viewport in world system units, which tells you how much of your world is shown on screen. Think of it as a window into your world.
  * Now chose one of the two options for asset creation
    1. Define a target resolution (e.g. 320x480, or 480x800) and create all your graphical assets (sprites etc.) with the viewport to resolution mapping as defined above (e.g. 1x1 units = 32x32 pixels).
    1. Define which resolution classes you want to target (e.g. HVGA (320x480), WVGA (480x800) etc.) and create versions of your assets for each of these resolutions (e.g. 1x1 units = 32x32 pixels on HVGA, 1x1 units = 48x53 pixels on WVGA etc.). If you extend your viewport so that it has the same aspect ratio as the screen on WVGA in that example you can map 1x1 units to 48x48 pixels to make things square again.
  * Setup your OrthographicCamera with the appropriate viewport (constant for all screens -> stretching, extended to fit screen aspect ratio -> no stretch, but more/less of the world is visible) and draw all your objects in world system units!


# Credits #

Thanks for your contributions:

  * mzechner (forum)