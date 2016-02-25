# PinchToZoom #

The GestureDetector class has been designed to capture and handle gestures such as fling, pan, zoom, pinch, tap and long press. [GestureDetectorTest.java](http://code.google.com/p/libgdx/source/browse/trunk/tests/gdx-tests/src/com/badlogic/gdx/tests/GestureDetectorTest.java) provides an excellent example of how to implement this.

See also the documentation for [GestureDetector](http://libgdx.l33tlabs.org/docs/api/com/badlogic/gdx/input/GestureDetector.html) and [GestureDetectorListener](http://libgdx.l33tlabs.org/docs/api/com/badlogic/gdx/input/GestureDetector.GestureListener.html)

If you would to implement it manually, the following information is relevant.



This gesture is quite common to zoom in / out:
Two fingers are moved away from each other to zoom in or moved closer together to zoom out.

The zoom action taken on this gesture could be scaling your object (sprite, decal, model, scene, ...) or modifying the camera.


## detecting the gesture ##

(thx to bach (irc) for contributing his basic idea detection code, modified it a little)

<b>in class</b>
```java

// for pinch-to-zoom
int numberOfFingers = 0;
int fingerOnePointer;
int fingerTwoPointer;
float lastDistance = 0;
Vector3 fingerOne = new Vector3();
Vector3 fingerTwo = new Vector3();
```

<b>in touchDown()</b>
```java

// for pinch-to-zoom
numberOfFingers++;
if(numberOfFingers == 1)
{
fingerOnePointer = pointer;
fingerOne.set(x, y, 0);
}
else if(numberOfFingers == 2)
{
fingerTwoPointer = pointer;
fingerTwo.set(x, y, 0);

float distance = fingerOne.dst(fingerTwo);
lastDistance = distance;
}```

<b>in pause()</b>
```java

// some error prevention...
numberOfFingers = 0;```

<b>in touchDragged()</b>
```java

// for pinch-to-zoom
if (pointer == fingerOnePointer) {
fingerOne.set(x, y, 0);
}
if (pointer == fingerTwoPointer) {
fingerTwo.set(x, y, 0);
}

float distance = fingerOne.dst(fingerTwo);
float factor = distance / lastDistance;

if (lastDistance > distance) {
cam.fieldOfView += factor;
} else if (lastDistance < distance) {
cam.fieldOfView -= factor;
}

// clamps field of view to common angles...
if (cam.fieldOfView < 10f) cam.fieldOfView = 10f;
if (cam.fieldOfView > 120f) cam.fieldOfView = 120f;

lastDistance = distance;

cam.update();
cam.apply(gl);```

<b>in touchUp()</b>
```java

// for pinch-to-zoom
if(numberOfFingers == 1)
{
Vector3 touchPoint = new Vector3(x, y, 0);
cam.unproject(touchPoint);
}
numberOfFingers--;

// just some error prevention... clamping number of fingers (ouch! :-)
if(numberOfFingers<0){
numberOfFingers = 0;
}

lastDistance = 0;```




## Example: modifying the model ##

```java

gl.glScalef(x, y, z);
someMesh.render;```



## Example: modifying the camera ##

With an orthographic camera you can use it's zoom attribute:

```java

oCam.zoom = -3f;
oCam.zoom = 4f;```

With a perspective camera you can modify the FOV attribute...


```java

// field of view in degrees
cam.fieldOfView = 30;```


...or you stay with the FOV and move the camera along it's viewing axis instead:

```java

deltaTime = Gdx.graphics.getDeltaTime();
speed = 40*deltaTime;

// moves camera along it's viewing direction
// to simulate zooming
Vector3 camZoom = new Vector3();
camZoom.set(cam.direction.cpy());
camZoom.nor().mul(speed*zoom);

// don't zoom too close or too far away
if( ((cam.position.y>3f)&&(zoom>0)) ||
((cam.position.y<100f)&&(zoom<0))
)
{
cam.translate(camZoom.x, camZoom.y, camZoom.z);
}```