# Generic texture binding #



This page describes a generic approach for applying textures to (3D) meshes.


## Why? ##
The current OBJ-Loader from the extension "model-loaders" does not import textures. This has to be taken care of manually.

However, hard-coded loading and binding textures does not fit dynamic mesh loading. Therefore a generic approach has to be implemented.

## How? ##

The textures are loaded from texture file(s) and bound to meshes dynamically in render() method. This implementation tries to find texture files in the "texture" folder that have a basename matching one or more of the object's name(s) inside the model file.
As a fallback it tries to find texture files in the "texture" folder that have a basename matching the object file's basename.
In both cases a list of common texture filetypes (.png, .jpg, .gif, .tga or .bmp) is used to find the correct filename extension, see helper method at the end of this page.

<b>Examples:</b>

Model file: airplane.obj with 2 objects named "left\_wing" and "right\_wing"<br />
Found texture files: left\_wing.bmp, right\_wing.jpg

Model file: airplane.obj<br />
Found texture file: airplane.png


# Preliminaries #

  1. The OBJ file has to contain the UV-mapping (= the "vt" lines) for the objects that should appear textured on screen. UV-Mapping can be added in a 3D modeler like [Blender](Blender.md).
  1. A texture file (= an image of type .png, .jpg, .gif, .tga or .bmp) is needed. Either as one big file containing all textures or multiple files each containing a different object's texture. <b>Using a single file is recommended!</b> The base-filename (that is the name without the extension) of the texture file(s) must match the OBJ-filename or a mesh object's name.

# Code #

<b>in class:</b><br />

```java

// counter for texturing
int listPosition;

List<Texture> modelTextures;```


<b>in create():</b><br />

```java

// testing for available textures
String texturePath;
!FileHandle texturePathToLoad;
boolean engageTextureLookupStrategy2 = false;

// strategy #1: texture files share
//              the object names as basename
for(!SubMesh subMesh : subMeshes)
{
// looking for texture file if sub mesh
// has uv coordinates attached, only!
if(stillModel.getSubMesh(subMesh.name).mesh.getVertexAttribute(Usage.!TextureCoordinates)!=null)
{
texturePath = "data/textures/" + subMesh.name;
texturePathToLoad = guessTextureFile(texturePath);
if(texturePathToLoad!=null)
{
Log.out("... found Texture! Adding " + texturePathToLoad.path() + " to list of model textures.");
Texture temp_texture = new Texture(texturePathToLoad);
modelTextures.add(temp_texture);
}
else
{
Log.out("... not found. Object " + subMesh.name + " will not be textured.");
engageTextureLookupStrategy2 = true;
}
}
}


// testing for available textures
// strategy #2: texture shares the model file's base name
if(engageTextureLookupStrategy2)
{
String folderDelimiter = "/";
String[] pathParts = new String[10];
pathParts = filepaths[modelNr].split(folderDelimiter);
String modelFileName = pathParts[pathParts.length-1];

texturePath = null;
if(modelFileName.endsWith(".g3d")
|| modelFileName.endsWith(".obj"))
{
String extensionDelimiter = "\\.";
String[] modelFileNameParts = new String[2];
modelFileNameParts = modelFileName.split(extensionDelimiter);
String basename = modelFileNameParts[modelFileNameParts.length-2];
texturePath = "data/textures/" + basename;
}
else
{
texturePath = "data/textures/" + modelFileName;
}

texturePathToLoad = guessTextureFile(texturePath);
if(texturePathToLoad!=null)
{
Log.out("... found Texture! Adding " + texturePathToLoad.path() + " to list of model textures.");
Texture temp_texture = new Texture(texturePathToLoad);
modelTextures.add(temp_texture);
}
}```


<b>in render():</b><br />

```java

// counter for texturing
listPosition = 0;

// automatic texture binding...
if( (modelTextures!=null)
&&  (modelTextures.size()>0)
// binding texture if sub mesh
// has uv coordinates attached, only!
&& (stillModel.getSubMesh(subMesh.name).mesh.getVertexAttribute(Usage.!TextureCoordinates)!=null)
)
{
if(listPosition<modelTextures.size())
{
// we don't want the texture to be tinted,
// thus setting white color.
gl.glColor4f(1,1,1,1);

Log.out("binding texture #" + listPosition + " to texture unit #" + listPosition + " for submesh " + subMesh.name);
gl.glActiveTexture(listPosition);
// glClientActiveTexture not needed?!
//		gl.glClientActiveTexture(GL11.GL_TEXTURE0 + listPosition);
gl.glEnable(GL11.GL_TEXTURE_2D);
modelTextures.get(listPosition).bind();
Log.out("!TextureObjectHandle: " + modelTextures.get(listPosition).getTextureObjectHandle());
}
else
{
gl.glActiveTexture(listPosition);
gl.glDisable(GL11.GL_TEXTURE_2D);
}
listPosition++;
}

// render your mesh here!
```

## texture file extension guessing ##

This is a helper method to "guess" the correct file extension by brute-force-testing well-known extensions. If there is no suitable texture file found, it returns "null".

```java

public !FileHandle guessTextureFile(String texturePath)
{
boolean textureFileFound = false;
Log.out("Looking for " + texturePath + ".* ...");
if(Gdx.files.internal(texturePath + ".png").exists())
{
texturePath += ".png";
textureFileFound = true;
}
else if(Gdx.files.internal(texturePath + ".jpg").exists())
{
texturePath += ".jpg";
textureFileFound = true;
}
else if(Gdx.files.internal(texturePath + ".gif").exists())
{
texturePath += ".gif";
textureFileFound = true;
}
else if(Gdx.files.internal(texturePath + ".tga").exists())
{
texturePath += ".tga";
textureFileFound = true;
}
else if(Gdx.files.internal(texturePath + ".bmp").exists())
{
texturePath += ".bmp";
textureFileFound = true;
}

if(textureFileFound)
{
return Gdx.files.internal(texturePath);
}
return null;
}```


# Credits #

Thanks for your contributions:

  * radioking (initial version)