# Orbiting camera #

There are at least two ways to get your camera rotate around a point in 3D while looking at it all the time - I tend to call this "orbiting camera".


## Method (1) ##

  1. calculate the distance between camera and world origin (= orbit radius)
  1. move the camera to world origin withoud changing up-vector or viewing direction
  1. rotate the camera around the global y-axis in the desired direction
  1. move the camera backwards along it's direction (viewing) vector for distance of "orbit radius"

Note that the "camera orbit point" is the projection from the unprojected center of the screen onto the xz-plane.

```java

/**
* Rotates the camera around a point in 3D by 1 degree each call.
* @author radioking from Badlogic forum (libGDX)
* @param origin The vector to rotate.
* @param clockwise defines rotation direction
*/
public void orbitPoint(Vector3 origin, boolean clockwise)
{
// (1) get intersection point for
//     camera viewing direction and xz-plane
cam.lookAt(origin.x, origin.y, origin.z);
cam.update();
cam.apply(gl);
camViewRay.set(cam.position, cam.direction);
Intersector.intersectRayPlane(camViewRay, xzPlane, lookAtPoint);

// (2) calculate radius between
//     camera position projected on xz-plane
//     and the intersection point from (1)
orbitRadius = lookAtPoint.dst(cameraPosition.set(cam.position));

// (3) move camera to intersection point from (1)
cam.position.set(lookAtPoint);

// (4) rotate camera by 1° around y-axis
//     according to winding clockwise/counter-clockwise
if(clockwise){
cam.rotate(-1, 0, 1, 0);
}
else
{
cam.rotate(1, 0, 1, 0);
}

// (5) move camera back by radius
orbitReturnVector.set(cam.direction.tmp().mul(-orbitRadius));
cam.translate(orbitReturnVector.x, orbitReturnVector.y, orbitReturnVector.z);
}```

```java

/**
* Rotates the camera around the current center of screen projected on xz-plane
* @author radioking from Badlogic forum (libGDX)
* @param angle rotation angle in degrees.
*/
public void orbitLookAt(float angle)
{
// (1) get intersection point for
//     camera viewing direction and xz-plane
camViewRay.set(cam.position, cam.direction);
Intersector.intersectRayPlane(camViewRay, xzPlane, lookAtPoint);

// (2) calculate radius between
//     camera position projected on xz-plane
//     and the intersection point from (1)
orbitRadius = lookAtPoint.dst(cameraPosition.set(cam.position));

// (3) move camera to intersection point from (1)
cam.position.set(lookAtPoint);

// (4) rotate camera by 1° around y-axis
//     according to winding clockwise/counter-clockwise
cam.rotate(angle, 0, 1, 0);

// (5) move camera back by radius
orbitReturnVector.set(cam.direction.tmp().mul(-orbitRadius));
cam.translate(orbitReturnVector.x, orbitReturnVector.y, orbitReturnVector.z);
}```


## Method (2) ##

This method involves a little more maths as you have to calculate the camera's waypoints with the help of polar coordinates / trigonometry.

```java

/**
* Rotates a vector around an origin.
* @author Obli from Badlogic forum (libGDX)
* @param v The vector to rotate.
* @param o The origin.
* @param angleDeg The angle, in degrees.
*/
public static void rotate(Vector2 v, Vector2 o, float angleDeg) {
float cos = !MathUtils.cosDeg(angleDeg);
float sin = !MathUtils.sinDeg(angleDeg);
float x = v.x;
float y = v.y;
v.x = cos*(x-o.x) - sin*(y-o.y) + o.x;
v.y = sin*(x-o.x) + cos*(y-o.y) + o.y;
}```


# Simple Rotations #

[Rotate (pitch) a camera around it's local x-axis](CameraPitch.md)

[Rotate (yaw) a camera around it's local y-axis](CameraYaw.md)

[Rotate (roll) a camera around it's local z-axis](CameraRoll.md)