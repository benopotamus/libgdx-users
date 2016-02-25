# Framebuffer #

Eric S. wrote a ScreenUtils class that offers a few methods for you to capture the framebuffer contents (or a portion thereof). It's located in com.badlogic.gdx.utils.

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

http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1819


## FrameBuffer class ##
Or you can use the FrameBuffer class (which is wrapper around OpenGL ES framebuffer objects), for ease of use, flexibility and most of all speed. You need to use OpenGL ES 2.0 for that.

```java
fbo.begin();

// draw your stuff
fbo.end();

fbo.getColorTexture();```


# Credits #

Thanks for your contributions:

  * Eric Spitz
  * Mario Zechner