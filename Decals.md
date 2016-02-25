# Using Decals #

## Introduction ##
Decals are bitmap sprites that exist in a 3D scene. They can be rotated and scaled in full 3D, and support z-ordering. But they render blazingly fast - almost as fast as simple 2D sprites it seems.
If you're using a 2D spritebatch you can basically replace it with a DecalBatch.

Decals have a front and a back side- they are rendered both sides unless you enable GL\_CULL\_FACE.

We created a demo that shows mixing 3d decals and 2d sprites in a simple scene:

note the walls at the back here are decals, and also the stars. 2d flat sprites rotated in the scene.
![http://wiki.libgdx-users.googlecode.com/git/images/shots/decals-demo.png](http://wiki.libgdx-users.googlecode.com/git/images/shots/decals-demo.png)

update: we added a feature to decals to always rotate them to the camera (a kind of inverse lookAt() function)
so this is good for having ultra-fast 2d graphics in a 3D space.
![http://www.bitz.pikkle.com/data/img/decals03.png](http://www.bitz.pikkle.com/data/img/decals03.png)

The basic lifecyle is:
```
// Load a Texture
Texture image = new Texture(Gdx.files.internal(imgPath));
// create a decal sprite
sprite = Decal.newDecal(w, h, new TextureRegion(image), true);

// create a DecalBatch to render them with just once at startup
decalBatch = new DecalBatch();
```

then later in your rendering loop
```

// ... add some more sprites to be rendered at same time
decalBatch.add(sprite);
// decalBatch.add(sprite2); //etc

// flush will render the sprites out and clear the openGL buffer
decalBatch.flush();
```

## demo ##
we prepared an example scene which uses Decals and DecalBatch. [You can get it git here](UsingGit.md) or just
[browse the source here](http://code.google.com/p/libgdx-users/source/browse/demos-3-tier-main/src/com/gdxuser/demos/DecalWall.java)

This demo integrates decals into a very simple 3D world scene, with a moving camera. There's also a 2D sprite in the scene for comparison.

update: we had some issues with layering and transparency, but all sorted out now in [teh codez!](GitSetup.md). Thanks to all on IRC!

We added a DecalSprite wrapper class which adds a feature to have a decal always face the user:
[DecalSprite.java](http://code.google.com/p/libgdx-users/source/browse/demos-3-tier-main/src/com/gdxuser/util/DecalSprite.java#42)


## More info ##
  * [Original contribution and Forum post by Vevusio](http://www.badlogicgames.com/forum/viewtopic.php?f=17&t=605)
  * See this [Blog Entry](http://www.badlogicgames.com/wordpress/?p=2100) for a basic intro.
  * [Decal Performance test from official trunk](http://libgdx.googlecode.com/svn/trunk/tests/gdx-tests/src/com/badlogic/gdx/tests/DecalTest.java)
  * [Decal Transformation test from official trunk](http://libgdx.googlecode.com/svn/trunk/tests/gdx-tests/src/com/badlogic/gdx/tests/TransformationTest.java)

  * see [this entry](http://code.google.com/p/libgdx/wiki/SpriteBatch) on using SpriteBatch for 2D sprites. Same idea..

## Javadocs ##
  * [Decal](http://libgdx.l33tlabs.org/docs/api/com/badlogic/gdx/graphics/g3d/decals/Decal.html)
  * [DecalBatch](http://libgdx.l33tlabs.org/docs/api/com/badlogic/gdx/graphics/g3d/decals/DecalBatch.html)

Decals were contributed to libGDX by Vevusio. Isn't open source great?