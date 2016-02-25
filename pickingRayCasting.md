# Ray Casting #

Ray Casting is an algorithm for determining which object on screen has just been interacted with (e.g. touched or clicked). This is essential for the selection task.

LibGDX implements this picking algorithm.

See Camera class for details:

http://code.google.com/p/libgdx/source/browse/trunk/gdx/src/com/badlogic/gdx/graphics/Camera.java

See PickingTest on usage:

http://code.google.com/p/libgdx/source/browse/trunk/tests/gdx-tests/src/com/badlogic/gdx/tests/PickingTest.java

## How it works ##

A ray is sent perpendicular to the touched / clicked point into the displayed scene.
Intersection of this ray with objects in the scene is tested to find the selected object.