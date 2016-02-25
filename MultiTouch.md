# MultiTouch #

The number of pointers supported is device specific.<br />
Some phones only support two touch events at once.<br />
LibGDX will allow up to 20, but the underlying device may limit it further.

Also, there are known issues with HTC Desire not detecting 2 fingers which are crossing each other's axis, correctly.

You can check FEATURE\_TOUCHSCREEN\_MULTITOUCH\_DISTINCT and FEATURE\_TOUCHSCREEN\_MULTITOUCH\_JAZZHAND, but that will only tell you if the device supports 2+ or 5+ fingers - it doesn't give a specific number.<br />
It doesn't tell you anything about the underlying implementation of those pointers. Unfortunately, this is well beyond the control of libGDX.