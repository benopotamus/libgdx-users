# Introduction #



This will walk developers through creating MD2 animated files and implementing MD2 files into their libgdx project.


# Create Your Model #

There are numerous ways to create your basic mesh model. Some applications to look at for this are:
  * Blender
  * Wings3D
  * Art of Illusion

It is probably best to export your model as a .obj file.

# Animation #

What you will need:
  * [Blender 2.5+](http://www.blender.org/download/get-blender/)

Install Blender and start a new project. Delete the default cube (right click and delete) and 'import' your .obj model through file -> import option.

One way to animate your mesh is to tie armatures (skeleton) to your mesh. To do this, follow these tutorial videos:

  * http://www.cgmasters.net/free-tutorials/blenders-armatured-a-crash-course/
  * http://vimeo.com/15679442]

# Export MD2 #

What you will need:
  * [Blender MD2 Export Script](http://groups.google.com/group/junaio-developer/browse_thread/thread/f4d51a48fe55dbcc?pli=1)

Please note, you will have to run the MD2 python export script manually to see the File -> Export -> Export MD2 option. This is detailed in the link above or on the [MD2 File Format Page](fileFormatMd2.md).

In order to export your animated model you need to combine all meshes into one object. Select all your meshes and hold down ctrl+j to join the meshes. This may mess with your animation. Check your animation and tweak the skeleton and skeletal movements.

Select the mesh only (not the armatures) and go to File -> Export -> Export MD2. Now you have an animated MD2 file!

# Load MD2 in Game #

What you will need:
  * An SVN client such as [TortoiseSVN](http://tortoisesvn.net/downloads.html) so you can pull down the libGDX source.
  * [LibGDX Extensions/model-loaders/model-loaders](http://code.google.com/p/libgdx/source/checkout) as a project in Eclipse

### Test the MD2 file in libGDX ###

You will want to test your MD2 file first. In your model-loaders project, go to the com.badlogic.gdx.graphics.g3d.test area and open up [KeyframeModelViewer](http://www.google.com/codesearch#c5txscj0tnc/trunk/extensions/model-loaders/model-loaders/src/com/badlogic/gdx/graphics/g3d/test/KeyframedModelViewer.java&q=md2%20package:http://libgdx%5C.googlecode%5C.com&type=cs).

Uncomment the line similar to this (currently line 190):
```java

new JoglApplication(new KeyframedModelViewer("data/knight.md2", "data/knight.jpg"), "KeframedModel Viewer", 800, 480, false);
```
Comment out all other lines in the "public static void main"

In the creat() method uncomment the lines:
```java

Material material = new Material("material", new TextureAttribute(texture, 0, "s_tex"));
model.setMaterial(material);
```
Run the application on your desktop to make sure the knight.md2 file animates correctly.

Now place your md2 file and texture in the model-loaders project folder "data" and build the project. Rename the knight.md2 file and texture file with your model and texture names. Run the application again to see your MD2 animated in libGDX!

### Implement MD2 in Game ###

You will want to export the model-loader source into a jar or reference the model-loader project in your game project.

Copy the source in the [KeyframeModelViewer](http://www.google.com/codesearch#c5txscj0tnc/trunk/extensions/model-loaders/model-loaders/src/com/badlogic/gdx/graphics/g3d/test/KeyframedModelViewer.java&q=md2%20package:http://libgdx%5C.googlecode%5C.com&type=cs).

Note: the MD2 exporter draws vertexes in clockwise order not GL's default counter clockwise. If your textures seem upside down you are probably hitting this as an issue. Before you render your MD2 file you need to implement the line:
```java

gl.glFrontFace(GL10.GL_CW);
```