# Culling techniques #



Culling techniques generally aim at reducing the GPU load by not rendering objects or polygons that are not visible to the camera (out of view frustum or occluded). Such objects are not sent to OpenGL pipeline thus reducing GPU load.

TODO: Not 100% sure with this section... please correct me if I am unprecise or even wrong!!
Some implementations of OpenGL are not capable of determining which objects are visible an which are not. This has to be implemented and calculations are performed on CPU. OpenGL will draw what ever you pass it- thus potentially wasting GPU power on things that are not visible.


http://www.opengl.org/resources/faq/technical/clipping.htm


## Back face culling ##

Back sides of objects are not drawn as they are usually occluded.<br />
Back and front side are determined by winding of polygon definition:

counter-clockwise (CCW): front face<br />
clockwise: back face

```java

// Enable face culling- be careful with spriteBatch, might cull sprites as well!
gl.glEnable(GL10.GL_CULL_FACE);

// What faces to remove with the face culling.
gl.glCullFace(GL10.GL_BACK);```


## View frustum culling ##

LibGDX has some checks for object visibility - if they return false you don't need to render that object:

(see com.badlogic.gdx.math.Frustum)
```java

camera.frustum.pointInFrustum(Vector3 point):boolean
camera.frustum.sphereInFrustum(Vector3 center, float radius):boolean
camera.frustum.sphereInFrustumWithoutNearFar(Vector3 center, float radius):boolean```

Together with getBoundingBox(BoundingBox bbox) for each object you can implement a first simple approach for view frustum culling. Some more performance will provide advanced techniques mentioned in the following links.

<b>A very complete and detailed explanation of VFC:</b><br />
http://zach.in.tu-clausthal.de/teaching/cg_literatur/lighthouse3d_view_frustum_culling/index.html

"Efficient View Frustum Culling":<br />
http://www.cescg.org/CESCG-2002/DSykoraJJelinek/


TODO by some nice person ;-)


## Hierarchical view frustum culling ##

Objects have to be organized in a tree for this. Visibility testing is done in a recursive manner starting at root node. If a node is tested to be invisible it's whole sub-tree won't have to be tested.<br />
The tough part is creating the tree for a given scene... (also called a Bounding Volume Hierarchy, BVH)

### Octrees ###

Data structure for BVH.

TODO by some nice person ;-)


### BSP trees ###

Data structure for BVH.

TODO by some nice person ;-)


## Detail culling ##

Sorts out small objects.

TODO by some nice person ;-)


## Portal culling ##

For scenes within buildings.

TODO by some nice person ;-)


## PVS culling ##

TODO by some nice person ;-)


## Occlusion culling / hardware occlusion queues ##

Culling occluded objects within a scene. Needs front-to-back sorting of objects!

TODO by some nice person ;-)


## beam tree / wide rays ##

TODO by some nice person ;-)