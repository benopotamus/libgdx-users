# Getting world coordinates out of screen coordinates #


```java

// x and y are your screen coordinates
// (e.g. where a click/touch happened)
Vector3 worldCoordinates = new Vector3(x, y, 0);
camera.unproject(worldCoordinates);```

<b>As reference for Camera classes:</b><br />
http://www.badlogicgames.com/wordpress/?p=1550


If you like to determine what object(s) you hit in the 3D-scene you might find solutions on page [Interaction with objects in 3D](InteractionWithObjectsIn3D.md).