# Minimap #

A mini-map is a miniature map, often placed in a corner of the screen in computer games and video games to aid in reorientation. Mini-maps usually display traversable terrain, allies, enemies, and important locations or items.

![http://upload.wikimedia.org/wikipedia/commons/b/b3/Freeciv-2.1.0-beta3-sdl_slack11.0.png](http://upload.wikimedia.org/wikipedia/commons/b/b3/Freeciv-2.1.0-beta3-sdl_slack11.0.png)

http://img.over-blog.com/599x337/0/01/53/72/jeux-PS3/gta-4-plan.jpg&w=599&h=337&ei=lkYTT-qDPaOuiQfsqclD&zoom=1&chk=sbg&iact=hc&vpx=1340&vpy=187&dur=1513&hovh=168&hovw=299&tx=164&ty=73&sig=102546414162505449410&page=9&tbnh=141&tbnw=230&ndsp=40&ved=1t:429,r:15,s:319

http://en.wikipedia.org/wiki/Mini-map

In practice, a minimap is usually a stylised, zoomed out perspective of the play area.

## Why would I need a minimap? ##

If the player needs to see the entire play area and where they are within the entire play area, then a minimap can show the player that information easily. [Real Time Strategy](http://en.wikipedia.org/wiki/Real-time_strategy) games usually have some form of minimap. A minimap also provides a mechanism to move the players view to a specific area quickly without having to scroll around.

Scrolling within a mobile game can be less of an issue because of the players ability to swipe across the screen to rapidly move around.

However mobile games also often have small viewports and so important items outside the viewport would be invisible. A minimap is a way of showing them those important items.

## How to create a minimap for an Orthographic Camera ##

Information on the [OrthographicCamera](Camera.md).

Information on SpriteBatch.

Firstly, we need to think of a camera as a way of displaying graphics to the player. A game can have multiple cameras each overlapping and showing the player different graphics through different perspectives and zooms.

To display a minimap we need to use a second zoomed out camera.

```
public static final int WIDTH = 800;
public static final int HEIGHT = 480;
public static final int SCALE = 4;

private OrthographicCamera camera;
private OrthographicCamera cameraMiniMap;

private Texture playerTexture;
private Sprite player;

public void create () {
    // create a player 
    playerTexture = new Texture(Gdx.files.internal("assets/chicken.png"));
    player = new Sprite(playerTexture);

    // setup our standard camera
    camera = new OrthographicCamera(WIDTH,HEIGHT);
    batch = new SpriteBatch();

    // setup our mini map camera
    cameraMiniMap = new OrthographicCamera(WIDTH,HEIGHT);
    cameraMiniMap.zoom = SCALE;
    batchMiniMap = new SpriteBatch();
}
```

The first camera is our standard camera, used to draw graphics to the screen.
The second camera is for our minimap. It is identical besides setting a zoom level on it.

We can then use our minimap camera to draw our graphics at a zoomed level.

```
public void render () {
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.

    // update our camera
    cameraMiniMap.update();
    cameraMiniMap.apply(Gdx.gl10);

    // set the projection matrix for our batch so that it draws
    // with the zoomed out perspective of the minimap camera
    batchMiniMap.setProjectionMatrix(cameraMiniMap.combined);
    
    // draw the player
    batchMiniMap.begin();
    batchMiniMap.draw(playerTexture, player.getX(), player.getY());
    batchMiniMap.end();
}
```

More information on [batch.setProjectionMatrix()](http://libgdx.l33tlabs.org/docs/api/com/badlogic/gdx/graphics/g2d/SpriteBatch.html#setProjectionMatrix(com.badlogic.gdx.math.Matrix4))

What we have now is a zoomed out version of our playarea. There are several things you would probably want to do at this point:

### Display minimap in a corner ###

Instead of drawing our minimap markers in the same position as the non-zoomed out relatives, we can display them in a corner. We can do this by simply adding pixels to the position of the marker until it sits where we want it.

In this example I have moved the minimap to the top left hand corner of the screen.

```

public static final int MARKER_SIZE = 20;
public static final int MINIMAP_LEFT = 0;
public static final int MINIMAP_RIGHT = 200;
public static final int MINIMAP_TOP = 480;
public static final int MINIMAP_BOTTOM = 280;

public void render () {
    batchMiniMap.begin();
    batchMiniMap.draw(playerTexture, player.getX() - (WIDTH/2)*SCALE + ((MINIMAP_RIGHT-MINIMAP_LEFT)/2)*SCALE, player.getY() + (HEIGHT/2)*SCALE - ((MINIMAP_TOP-MINIMAP_BOTTOM)/2)*SCALE, MARKER_SIZE, MARKER_SIZE);
    batchMiniMap.end();
}
```

### Using different graphics ###

As most games don't scale down very well, you can use markers to represent the items in the game. So instead of rendering the texture of the item in the game, simply use an equivalent marker texture.

```
private Texture markerPlayer;

public void create () {
    markerPlayer = new Texture(Gdx.files.internal("assets/markerPlayer.png"));
}
public void render () {
    batchMiniMap.begin();
    batchMiniMap.draw(markerPlayer, player.getX(), player.getY());
    batchMiniMap.end();
}
```

### Only showing markers inside our minimap ###

Our minimap camera fills the screen, which is great if you want to have a full map view. However if you choose to have a small minimap in the corner of your game, this won't work.

All we need to do is check the markers position before we decide to render it. If it is within the bounds we want, then draw it, otherwise, ignore it.