**WARNING**

Technique described below works by looping **Texture** from edge to edge.
It will draw all there is from one side to the other depending on scroll direction. Red square represents sprite, rest is texture we are using:

![http://i.imgur.com/OMWyN.gif](http://i.imgur.com/OMWyN.gif)

So if you have some other stuff in your texture place them so they wont interfere with scrolling part. That would be above or under in previous example.

**CREATING SPRITE**

Creating scrolling sprite is quite simple. We start with creating new Texture:

```java

Texture spriteTexture = new Texture(Gdx.files.internal("data/spriteTexture.png"));
```

We set wrapping of texture in both directions:
```java

spriteTexture.setWrap(!TextureWrap.Repeat, !TextureWrap.Repeat);
```



Then we create Sprite from this texture:
```java

sprite = new Sprite(spriteTexture, 0, 0, 64, 64);
```
**SCROLLING SPRITE**

Now we are ready to animate it.

_Code below needs to be called every time game is rendered._

We need to know how much we need to move texture region of the sprite.
For this we will use simple timer.
```java

float scrollTimer+=Gdx.graphics.getDeltaTime();
if(scrollTimer>1.0f)
scrollTimer = 0.0f;

```

Texture coordinates go from 0 to 1, if you want to show only part of the texture at given frame you need to scale timer value proprietorially.
This code will scroll the texture from right to left, horizontally.
```java

sprite.setU(scrollTimer);
sprite.setU2(scrollTimer+1);
```

To scroll bottom to top, vertically, use this:

```java

sprite.setV(scrollTimer);
sprite.setV2(scrollTimer+1);
```

You can use negative values to reverse the directions.

Finally we draw our sprite within a SpriteBatch:
```java

sprite.draw(spriteBatch);
```

Full code below.
This requires working bare bones project, paste this in class that implements ApplicationListener.

```java

SpriteBatch spriteBatch;
Texture spriteTexture;
Sprite sprite;
float scrollTimer = 0.0f;

@Override
public void create() {
spriteBatch = new SpriteBatch();
spriteTexture = new Texture(Gdx.files.internal("data/spriteTexture.png"));

spriteTexture.setWrap(!TextureWrap.Repeat,!TextureWrap.Repeat);
sprite = new Sprite(spriteTexture, 0, 0, 64, 64);
sprite.setSize(256, 256);
}

@Override
public void render() {
scrollTimer+=Gdx.graphics.getDeltaTime();
if(scrollTimer>1.0f)
scrollTimer = 0.0f;

sprite.setU(scrollTimer);
sprite.setU2(scrollTimer+1);

spriteBatch.begin();
sprite.draw(spriteBatch);
spriteBatch.end();
}
```