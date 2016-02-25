# Applying materials to meshes #

OBJLoader ignores exported .mtl file ATM so you have to handle this your own:

```java

SubMesh subMesh;
Texture texture = new Texture(Gdx.files.internal("data/image.png"), true);
texture.setFilter(!TextureFilter.!MipMap, !TextureFilter.Linear);
Material material = new Material("Material1", new !TextureAttribute(texture, 0, "part"));
subMesh.material = material;```


# with model-loaders extension #

TextureAttribute is in the extensions\model-loaders branch (i.e. not part of gdx.jar!)