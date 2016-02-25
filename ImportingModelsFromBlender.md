# Importing Models, e.g. from Blender #



## Export ##
There is a [Blender](Blender.md) export script for both still and keyframed (animated) models.
It exports to libgdx's own text-based [g3dt](fileFormatG3dt.md) format.

Installation guide for the export script:

The python script for exporting a static mesh from [Blender](Blender.md) is living in this SVN folder:

> trunc/extensions/model-loaders/doc/blender/io\_scene\_g3dt/

This folder

> io\_scene\_g3dt/

has to be copied into [Blender](Blender.md)'s addons folder (first folders may vary, depending on your Blender version)

> ''blender-2.58a-linux-glibc27-x86\_64''/2.58/'''scripts/addons/'''


Install the exporter by selecting "export\_still.py" in [Blender](Blender.md)'s addons-install mask ("File" -> "User Preferences..." -> Tab "Add-Ons").


At first the addon is greyed out and thus not active.
You can enable it by checking the box to the right.

There is an issue with imports being moved in [Blender](Blender.md) 2.58a+, see issue tracker http://code.google.com/p/libgdx/issues/detail?id=382 for fix.<br />
Update: the issue has been fixed, the exporter works fine for Blender 2.58a+, just leaving the section as reference...


In general, text handling (String manipulations, XML parsing, ...) is slow on Android.
Loading binary files is said to be 10 times faster than loading text files on Android. (In desktop environment this difference is about factor 3-4 only.)
Therefore it is a good idea to convert your [g3dt](fileFormatG3dt.md) to [g3d](fileFormatG3d.md) which is libgdx's binary format).
Once the model is in memory there is no difference between using text and binary formats, though.

## Import and Conversion ##

You can convert any supported model format to any other supported model format on the fly by using this code snippet:

example code for [OBJ](fileFormatObj.md) to [G3D](fileFormatG3d.md) conversion (though this will work with any supported format):

```java

!ObjLoader loader = new !ObjLoader();
!StillModel model = loader.loadObj(Gdx.files.internal("data/model.obj"), true);
!G3dExporter.export(models, Gdx.files.absolute("data/model.g3d"));
```

Libgdx will turn any non-animated model into a StillModel object.
You tell libgdx if the model is static or animated by using G3DLoader.loadStillmodel(...) or G3DLoader.loadKeyframedModel(...);
But in the case of other formats, like OBJ - it assumes StillModel from the get go. For MD2 it assumes KeyFramesModel.
Providing the wrong type will result in an unforgiving Exception ;-).

Future development will leave you only setting the type of model (Still or Keyframed) at load time and libgdx guessing the file format thereafter.


Rumors say that there is a G3D Exporter for Blender as well - buried somewhere in SVN and only supporting still models at the moment.



## Supported 3D model formats ##

  * [Wavefront OBJ](fileFormatObj.md) (.obj), text, still models only, .mtl for materials/textures is not yet supported but will be in future! You have to [assign textures by hand](applyingTextures.md).
  * [G3DT](fileFormatG3dt.md) (.g3dt), text, still and keyframed models
  * [G3D](fileFormatG3d.md) (.g3d), binary, still and keyframed models
  * [MD2](fileFormatMd2.md) (.md2)
  * [MD5](fileFormatMd5.md) (.md5)

### Note on Meshes (for Wavefront OBJ) ###

<b>Tri angulate or not tri angulate</b> (all hail W.Shakespeare)<br />
With the current OBJ loader residing in com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader in the core package, meshes must still be triangulated.

While it is good practice, you do not need to triangulate your OBJ models prior to export if you use the new version of the OBJ loader residing in com.badlogic.gdx.graphics.g3d.loaders.wavefront.ObjLoader which is kept in the extensions\model-loaders branch.
This one will triangulate the mesh for you which will take some additional load time.

From a coding perspective quads add one extra loop to the normal face definition section of the code. Normally, the loader sees three verts definining a face and it just puts them into the mesh definition. When it sees four, it loops through those four verts to determine how to make two triangles from them.

<b>Indices list</b><br />
The method ```java
mesh.getIndices(meshIndices);``` from package com.badlogic.gdx.graphics.Mesh <b>WAS</b> not returning any indices at all. This was due to optimization on OBJ loading with the <b>new</b> model loader. Leaving out indices parsing is significantly faster. However- you might need the indices information for special purposes as intersection tests (e.g. on (Sub)Meshes, see [Interaction with objects in 3D](InteractionWithObjectsIn3D.md)).<br />
This is why indices were re-inroduced in SVN [r2608](https://code.google.com/p/libgdx-users/source/detail?r=2608) and SVN [r2609](https://code.google.com/p/libgdx-users/source/detail?r=2609):

http://code.google.com/p/libgdx/source/diff?path=/trunk/extensions/model-loaders/model-loaders/src/com/badlogic/gdx/graphics/g3d/loaders/wavefront/ObjLoader.java&format=side&r=2608

http://code.google.com/p/libgdx/source/diff?path=/trunk/extensions/model-loaders/model-loaders/src/com/badlogic/gdx/graphics/g3d/loaders/wavefront/ObjLoader.java&format=side&r=2609


<b>roadmap</b><br />
Once the 3d package is finished, all the code from extensions\model-loaders will be migrated into the core gdx package. That should happen in tandem with the libgdx v1.0 release.


### What about n-gons? ###

LibGDX does not support n-gons since they are technically not allowed by the OBJ specification. Though there may be some 3D modelling apps that will put n-gon definitions into the OBJ file, [Blender](Blender.md) is one of them. Be sure that you have triangulated your mesh!

### Triangulating a mesh ###

You can archieve this in Blender 2.5+ by selecting all faces and then

In Edit Mode:
> Mesh -> Faces -> Quads to Tris

or simply using shortcut

> Ctrl + T


# Credits #

Thanks for your contributions:

  * Eric Spitz (via IRC)