# Interaction with objects in 3D #

## Selection ##

Selecting objects in a 3D scene is addressed by picking algorithms. The most common picking algorithms are [Color Picking](pickingColorPicking.md) and [Ray Casting](pickingRayCasting.md).

There is a PickingTest in libgdx tests package:

http://code.google.com/p/libgdx/source/browse/trunk/tests/gdx-tests/src/com/badlogic/gdx/tests/PickingTest.java


The basic task for picking (by ray casting) is testing if the pickRay intersects with e.g. boundingBoxes, planes or meshes. LibGDX Intersector class handles pretty much of it. It is still lacking some features, though.



### Getting vertices lists from Submeshes ###

```javascript
float[] verticesBody = new float[planeBody.mesh.getNumVertices()*6];
float[] verticesWings = new float[planeWings.mesh.getNumVertices()*6];
float[] verticesRudder = new float[planeRudder.mesh.getNumVertices()*6];```

### Organizing vertices lists in a list ###
```javascript
List<float[]> meshes = new !ArrayList<float[]>();
meshes.add(verticesBody);
meshes.add(verticesWings);
meshes.add(verticesRudder);```

### Getting the closest intersection point with Meshes ###

```java
	public boolean intersectRayMeshes(Ray ray, List<float[]> meshes,
Vector3 globalIntersection) {

// presets
boolean intersectionOccured = false;

// for all Meshes in List
for (float[] mesh : meshes) {
if (Intersector.intersectRayTriangles(ray, mesh, localIntersection)) {
intersectionOccured = true;
Log.out("Intersection Occured!");
// update globalIntersection only if
// it is closer to the screen as the
// intersection point we got earlier
// and there was an intersection yet at all
if (globalIntersection != null) {
Log.out("Local intersection occured!");
if (ray.origin.sub(localIntersection).len() < ray.origin
.sub(globalIntersection).len()) {
Log.out("updated global intersection");
globalIntersection.set(localIntersection);
}
} else {
Log.out("First time setting global intersection!");
globalIntersection.set(localIntersection);
}
}
}

if (intersectionOccured) {
return true;
} else {
return false;
}
}```

### Picking the closest Mesh ###

**This is WIP!**
```java

public boolean intersectRayMeshes(Ray ray, List<!StillSubMesh[]> meshes,
Vector3 intersection, String meshId) {
boolean intersectionOccured = false;
Vector3 localIntersection = new Vector3(0, 0, 0);
intersection = new Vector3(0, 0, 0);
meshId = null;

// for all Meshes in List
for (!StillSubMesh[] submesh : meshes) {
float[] meshVertices = new float[submesh.length*6];
if (Intersector.intersectRayTriangles(ray, submesh, localIntersection)) {
intersectionOccured = true;
if (ray.origin.tmp().sub(localIntersection).len() < ray.origin
.tmp().sub(intersection).len()) {
intersection = localIntersection;
meshId = submesh.name;
}
}
}

if (intersectionOccured) {
return true;
} else {
return false;
}
}
```

## Translation ##

## Rotation ##

## Scaling ##