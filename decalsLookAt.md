# How to have decals always facing camera #

**Update!**

Mario added this new method to Decal class:

```java

/**
* Sets the rotation of the Decal to face the given point. Useful
* for billboarding.
* @param position
* @param up
*/
public void lookAt(Vector3 position, Vector3 up) {
dir.set(position).sub(this.position).nor();
setRotation(dir, Vector3.Y);
}```

http://code.google.com/p/libgdx/source/diff?spec=svn2551&r=2551&format=side&path=/trunk/gdx/src/com/badlogic/gdx/graphics/g3d/decals/Decal.java

See his notes on issue tracker:

http://code.google.com/p/libgdx/issues/detail?id=393


# how we did it before (deprecated) #
(thx to mcortez for some guidance on this on IRC)

```java

Vector3 viewingDirection = new Vector3(camera.position.cpy().sub(decal.getPosition()).nor());
decal.setRotation(viewingDirection, camera.up.cpy().nor());```


You could also have the decals facing any other point in 3D space this way.
Exchange

```java
camera.position```

with any Vector3 and provide any up-vector instead of

```java
camera.up.cpy().nor()```


Make sure updating your decals' orientation in render() method on camera movement.