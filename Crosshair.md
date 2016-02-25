# Adding a crosshair #



Adding a crosshair (often to center of screen) is a common task in 1st person games.

There are multiple ways to archieve this:

  1. drawing an image of a crosshair (suboptimal)
  1. as mesh


== drawing an image of a crosshair

Such an image can be stretched if necessary.


### as Texture with Pixmap ###

<b>add class variables</b>
```java

Pixmap crosshair_Bitmap;
Texture crosshair_Texture;
SpriteBatch spriteBatch;```

<b>add to create() and to resume()</b>
```java

// Create an empty dynamic pixmap
crosshair_Bitmap = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
crosshair_Bitmap.setColor(0, 0, 0, 1);

// upper left corner
crosshair_Bitmap.drawLine(0, 0, 0, 10);
crosshair_Bitmap.drawLine(0, 0, 10, 0);
crosshair_Bitmap.drawLine(1, 1, 1, 10);
crosshair_Bitmap.drawLine(1, 1, 10, 1);

// upper right corner
crosshair_Bitmap.drawLine(63, 0, 63, 10);
crosshair_Bitmap.drawLine(63, 0, 53, 0);
crosshair_Bitmap.drawLine(62, 1, 62, 10);
crosshair_Bitmap.drawLine(62, 1, 53, 1);

// lower right corner
crosshair_Bitmap.drawLine(63, 63, 63, 53);
crosshair_Bitmap.drawLine(63, 63, 53, 63);
crosshair_Bitmap.drawLine(62, 62, 62, 53);
crosshair_Bitmap.drawLine(62, 62, 53, 62);

// lower left corner
crosshair_Bitmap.drawLine(0, 63, 0, 53);
crosshair_Bitmap.drawLine(0, 63, 10, 63);
crosshair_Bitmap.drawLine(1, 62, 1, 53);
crosshair_Bitmap.drawLine(1, 62, 10, 62);

// Create a texture to contain the pixmap
crosshair_Texture = new Texture(64, 64, Pixmap.Format.RGBA8888);
crosshair_Texture.setFilter(Texture.!TextureFilter.Linear, Texture.!TextureFilter.Linear);
crosshair_Texture.setWrap(Texture.!TextureWrap.!ClampToEdge, Texture.!TextureWrap.!ClampToEdge);

// Blit the composited overlay to a texture
crosshair_Texture.draw(crosshair_Bitmap, 0, 0);```

<b>add to render()</b>
```java

spriteBatch.begin();
spriteBatch.draw(crosshair_Texture, centerX-crosshair_Bitmap.getWidth()/2, centerY-crosshair_Bitmap.getHeight()/2,
crosshair_Bitmap.getWidth(), crosshair_Bitmap.getHeight(), 0,
0, crosshair_Bitmap.getWidth(), crosshair_Bitmap.getHeight(),
false, false);
spriteBatch.end();```


## as mesh ##

You can draw a crosshair in a 3D modeler (e.g. Blender) and load is just like any mesh (for details see [Importing models from Blender](ImportingModelsFromBlender.md)) by

<b>in create()</b><br />
```java

!ObjLoader loader = new !ObjLoader();
!StillModel crosshair = loader.loadObj(Gdx.files.internal("data/crosshair.obj"), true);
```

<b>in render()</b><br />
```java

crosshair.getSubMesh("crosshair").mesh.render(GL10.GL_TRIANGLES);
```


### Also, you can create a mesh with libGDX methods: ###

Create a Mesh using GL\_TRIANGLE\_FAN and specify vertices in a way so that they form a circle.

The Vector Pinball example in SVN shows you how to do that:<br />
http://code.google.com/p/libgdx/source/browse/#svn%2Ftrunk%2Fdemos%2Fvector-pinball

Cons: it's hard to predict how tesselated (== how many triangles) you need for a smooth circle.

<b>code snippet by Maranor, see</b><br />
http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1917#p10358


# Credits #

Thanks for contributions:

  * Maff (forum)
  * mzechner (forum)