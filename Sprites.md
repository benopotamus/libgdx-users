# Sprites #



Sprites are flat images that live in 2D space and always face the camera.

They are all rendered within a SpriteBatch.<br />
But remember: Just like any good Highlander- there can only be one SpriteBatch within render(). =)


# Minimal example #

You will at least need these items: Sprite, Texture and SpriteBatch.

<b>class variables:</b>

```java

Sprite sprite;
Texture texture;```


<b>in create()</b>

```java

// loading a texture from image file
buttonLeftTexture = new Texture(Gdx.files.internal("data/image.png"));

// setting a filter is optional, default = Nearest
texture.setFilter(Texture.!TextureFilter.Linear, Texture.!TextureFilter.Linear);

// binding texture to sprite and setting some attributes
sprite = new Sprite(texture);
sprite.setSize(256, 256);
sprite.setPosition(200, 200);

spriteBatch = new SpriteBatch();```

<b>in render()</b>

```java

// cleaning up GL state... (if you enabled it before!)
gl.glDisable(GL10.GL_CULL_FACE);

spriteBatch.begin();

// this is only one possible drawing out of many
sprite.draw(spriteBatch);

// this is another one
spriteBatch.draw(texture, xPos, yPos, 0, 0, texture.getWidth(), buttonLeftTexture.getHeight());

// and a third...
sprite.draw(texture, 100, 100);

spriteBatch.end();

// re-enable GL state... (if you need it)
gl.glEnable(GL10.GL_CULL_FACE);```



<b>in resize(...)</b>

```java

spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);```



# Enhanced example #

A SpriteBatch has it's own implicit 2D Orthographic Camera that can be interacted with just like any camera you set up on your own. It even co-exists with a 3D camera.

To use this camera you have to add a OrthographicCamera , set it's Projection matrix whenever you moved it in any way and finally update it.

<b>class variables:</b>

```java

private !OrthographicCamera oCam;```

<b>in create()</b>

```java

oCam = new !OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

// this is setting cam's projection matrix for mixing with 3D cam
spriteBatch.setProjectionMatrix(oCam.combined);

// this is setting cam's projection matrix the standard way
spriteBatch.getProjectionMatrix().setToOrtho2D(0f,0f,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

oCam.update();```

<b>in render()</b>

```java

// call this if you moved oCam in any way just before !SpriteBatch

spriteBatch.setProjectionMatrix(oCam.combined);
oCam.update();```


### Note on order of render, update and apply ###

<b>in create()</b>
After setting up any camera in create, call this for each camera:

```java

pam.update();
cam.apply(gl);```


<b>in render()</b>

Call this at the very end of render() for 3D (perspective) camera:

```java

pCam.update();
pCam.apply(gl);```

and additionally - if you moved your 2D (orthographic) camera, call this right before SpriteBatch:

```java

oCam.update();
oCam.apply(gl);```

## Pitfalls ##

A SpriteBatch has notions, e.g. it demands a somewhat "clean" OpenGL state: You need to disable some of your GL-settings right before a SpriteBatch. A common candidate that needs to be disabled is <b>GL_CULL_FACE</b> and some lighting settings.

Check if you set the ProjectionMatrix (see above!) for your SpriteBatch in resize(...)!<br />
Without this nothing from your SpriteBatch will show up on screen.



# Generating sprites by combination #

<b>Q:</b> I would like tho have the output of a SpiteBatch captured as a Texture and display this at the end of another sprite (or apply the texture to a rectangle. ...). The SpriteBatch might be sth. like the following:

```java

spriteBatch.begin();
backgroundSprite.draw(spriteBatch);
sprite.draw(spriteBatch);
spriteBatch.end```

### ...using [FrameBuffer](Framebuffer.md) capturing ###

<b>Eric:</b> There are several ways to do this with libgdx. You will find that none of the options are very fast on an Android device (assuming that's your target).<br />In either case shown above the Texture will be <b>unmanaged</b>, so it will be your responsibility to reload them after a context loss. Also, don't forget to keep track of the things you might need to dispose() of later.<br />
<b>These examples are untested and should only outline which way to go!</b>

  1. ) I wrote a ScreenUtils class that offers a few methods for you to capture the framebuffer contents (or a portion thereof). It's located in com.badlogic.gdx.utils. Based on what you described in your post, the code might look something like this:

```java

public Sprite generateSpriteFromBufferContents(int x, int y, int width, int height) {
// I assume you've already drawn what you want with !SpriteBatch at this point...

// grab the framebuffer contents from pixel location [x, y] to [x+width, y+height]:
!TextureRegion frameBufferRegion = !ScreenUtils.getFrameBufferTexture(x, y, width, height);
return new Sprite(frameBufferRegion);

// You can safely clear the framebuffer with glClear after this
// and draw anything else you want (including the Sprite you
// just created above)...
}```


### ...combining by Pixmap and sprite.draw(...) ###
> 2.) You could combine images at run time and then generate sprite's from that:

```java

public Texture generateDynamicTexture() {
Pixmap baseImage = new Pixmap("base_image.png");
Pixmap imgA = new Pixmap("some_background.png");
Pixmap imgB = new Pixmap("some_sprite.png");

Texture dynamicTexture = new Texture(baseImage); // Create a Texture from the baseImage
dynamicTexture.draw(imgA, 20, 20); // draw imgA at [20,20] on the Texture;
dynamicTexture.draw(imgB, 50, 50); // draw imgB at [50,50] on the Texture;

baseImage.dispose();
imgA.dispose();
imgB.dispose();

return dynamicTexture;
}```

http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1819

???  You could also add a Decal's TextureRegion by lately introduced method:

```java

decal.getTextureRegion();```

# Questions and Answers #

Q: When drawing with a SpriteBatch, what's the difference between supplying a scale factor <> 1 and supplying bigger/lesser width and height values (so the texture is just stretched to match them)?

A: The former will scale the sprite around its origin, the later will extend it to the right and upwards.


# Credits #

Thanks for your contributions:

  * mzechner (forum)