# Texturing a mesh #


## with core libgdx ##

You can regular Textures and apply them to a mesh:

```java

Texture texture = new Texture(Gdx.files.internal("data/someTexture.png"));

// Loading Model: gdx core style
!ObjLoader model = new !ObjLoader();
Mesh mesh = !ObjLoader.loadObj(Gdx.files.internal("data/someMesh.obj").read(), true);

// Loading Model: gdx extensions model-loaders style
!ObjLoader model = new !ObjLoader();
!StillModel stillModel = model.loadObj(Gdx.files.internal("data/someMesh.obj"));
```

<b>in create()</b>
```java

gl.glEnable(GL10.GL_TEXTURE_2D);```


<b>in render()</b>
```java

gl.glPushMatrix();
texture.bind();
mesh.render();
gl.glPopMatrix();```

The mesh needs uv coordinates for texture - they are associated with the bind() call. It tells OpenGL "apply this texture to anything I render from this point forward, using it's uv coordinates"