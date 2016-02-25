# How to get transparencies right #
(with laymans explanations from "Seargent" EricS ;-))

This page is about how to deal with transparencies.

If you have graphic glitches it might well be that you haven't obeyed one or more of the following rules (**in this exact order!**):

  1. render opaque objects first (this includes things like render(gl, GL10.GL\_LINE\_STRIP); commands!)
  1. enable blending
  1. render transparent objects from furthest to nearest (= from back to front)


**Why should I do this?**

The depth buffer will ensure your transparent objects are obstructed by the solid ones where appropriate - but if you want to see things "through" the transparent objects, those things have to be drawn into the frame buffer already


## Other things to check ##
  * **Use the right GroupStrategy:** There are 3 GroupStrategies available: "SimpleOrthoGroupStrategy", "CameraGroupStrategy", and "DefaultGroupStrategy". Camera sorts the objects by distance from the camera whereas DefaultGroupStrategy only sorts them in order of opaque then transparent, without any regard to their distance from the camera.
**So you will most likely to have the CameraGroupStrategy set up!**

```java

decalBatch.setGroupStrategy(new !CameraGroupStrategy(yourCamera));```


## Notes ##

With completely opaque objects, there is no issue with depth buffer enabled. But with transparent objects, you must render them in "back-to-front" order, after all opaque objects have been rendered.

Blending only combines the alpha of the current object with what's already in the framebuffer.

Decal batch will handle the order within itself if you create a Decal with: ```java

sprite = Decal.newDecal(new !TextureRegion("egg.png"), true);```

and use the GROUP\_BLEND strategy. But that alone doesn't help with objects of other type in your scene.