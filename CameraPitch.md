# Camera pitch #

This snippet is for rotating a camera around it's local x-axis:


```java

// pitches camera (rotates camera around it's
// local x-axis) for viewing up and down
camdir = cam.direction.cpy();
if( (camdir.nor().y>-0.9) && (pitch<0) || (camdir.nor().y<-0.3) && (pitch>0) )
{
localCamAxisX = cam.direction.cpy();
localCamAxisX.crs(cam.up.tmp()).nor();
cam.rotate(speed*pitch, localCamAxisX.x, localCamAxisX.y, localCamAxisX.z);
cam.up.nor();
}```


Also see:

[Rotate (yaw) a camera around it's local y-axis](CameraYaw.md)

[Rotate (roll) a camera around it's local z-axis](CameraRoll.md)