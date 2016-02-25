# Introduction #

Using tiled maps in libgdx is made easier with the use of a number of classes that do most of the heavy lifting for you.

These classes are included in [com.badlogic.gdx.graphics.g2d.tiled](http://libgdx.l33tlabs.org/docs/api/com/badlogic/gdx/graphics/g2d/tiled/package-frame.html).

There is also some detail around this at [TiledMaps wiki](http://code.google.com/p/libgdx/wiki/TiledMaps).



# Preparation #

## Tiled Maps ##

Tiled maps consist of a couple of things :
  * a map in .TMX format
  * a tileset texture
  * a pack file explaining the layout of the tiles in the texture


## Tilesets ##

### Creating ###

First thing you need to do, is create some graphics that you will use to draw the map with.

First, decide how big you want the tiles. Perhaps 32x32 pixels is a good size.

Then, in your favourite graphics tool, create an image, and start drawing your tiles. Each tile should be 32x32 pixels.

**Note:** The size of your image must be a multiple of the size of the tiles you are using, otherwise you will have problems with packing the texture later. eg. for 5 32x32 tiles, make it 160x32 and place the tiles horizontally with no spaces.

_example image here_

Create a new empty directory (you'll see why later) and save this image as a PNG inside the directory.

### Optimising ###

Depending on how you are going to use the tileset, there may be some tweaks you can use to make things look better or remove artifacts during resizing/filtering.

For example, one common method is to place a 1-pixel border around each of the tiles, that repeats the same pixels as the original edge.

_example image here_

If you are using filtering for rendering your tiles, this will stop the filter from mixing colors from other tiles into the one you are rendering.

**Note:** Using filtering on tiles will definitely include a performance hit, and the tiled classes render using the NEAREST filter by default.


## Map files ##

Now, you can actually create a map using those pretty tiles you've drawn. Open up the [Tiled](http://put/link/here) editor.

Create a new map that has the dimensions you want. For example, you might want it to be 100x100, which when combined with your 32x32 pixel tiles will result in a 3200x3200 pixel map area.

Next we need to load the tileset, so click blah and blah.

_open image example_

Here, choose the PNG file you saved before, enter 32 for the width and height and put 0 for the margin and border as we have created the tiles with no borders (If you've fiddled with the textures or have it in a different format, like with the 1px borders or spaces between, set these values as appropriate).

Now you can use Tiled to create your map, and save it as a TMX file in the same location as your PNG.

## Packing ##

So, now we need to do pack the map and tileset. This optimises the TMX file as well as the texture file for use with the libgdx classes.

Read this [TiledMaps](http://code.google.com/p/libgdx/wiki/TiledMaps).

Some things to look out for :
  * The texture size must be a multiple of the tile size
  * Check inside the TMX file you saved (it's just a text file) for the path of the image. This is relative and needs to point to your actual PNG tileset in your newly created dir.
  * Do not have an underscore character (_) in any map name or tile set name. This will cause issues with the TexturePacker extension that is used when packing the maps with TiledMapPacker.
  * All maps in the input directory for the TiledMapPacker should be able to be opened in Tiled without having to move them. This is a good way to test that your directory structure is valid._

Run the TexturePacker with your input directory being the one you've created that contains your TMX and PNG file (and nothing else!).

Set the output directory to somewhere that exists and is empty.

The results are an updated TMX and PNG file, along with a pack file. You can now move these to somewhere under your assets directory for use in the game.

Just remember to check the paths in the TMX and pack file are correct as they are relative to the location of the TMX file.

The TexturePacker automatically makes the output PNG file have dimensions that are powers-of-2, so it can be loaded directly into libGDX and OpenGL.


## Implementation ##

### Loading ###

When loading a tiled map you do the below somewhere in your init code ( create() or show() ).

```
        TileMapRenderer tileMapRenderer;
        TiledMap map;
        TileAtlas atlas;

        // Load the tmx file into map
        map = loader.createMap(Gdx.files.internal("data/testmap.tmx"));

        // Load the tiles into atlas
        atlas = new TileAtlas(map, Gdx.files.internal("data/"));

        // Create the renderer
        tileMapRenderer = new TileMapRenderer(map, atlas, 32, 32, 5, 5);



```

So first we are getting an internal filehandle to the testmap.tmx file in the data directory (which means it's actually assets/data on android). Adjust the path and filename as necessary.

It then passes it to TiledLoader which actually loads it into the map object.

Next we are loading the tileset, but notice we are not passing a filehandle or path to the actual PNG file. TileAtlas actually takes the map object and a base directory filehandle.

Remember, inside the TMX file is the relative path to the PNG tileset it was created with. So TileAtlas is using that to figure out where and what the PNG file is.

This is where the packfile comes in though, and things are not quite obvious. If you look at the [source code](http://code.google.com/p/libgdx/source/browse/trunk/gdx/src/com/badlogic/gdx/graphics/g2d/tiled/TileAtlas.java) for TileAtlas, you'll see the very first thing it does is not to look for the PNG file referenced in the TMX map, but to look for a packfile.

So, if your PNG was called tileset.png, the code removes the extension and appends " packfile" to the filename, and then tries to open that. So  you end up with this :
```
  "testmap.tmx"
  "tileset.png"
  "tileset packfile"
```

The packfile actually contains the relative path to the PNG file which is loaded later. Simple right??

So by this point, you have a map and a tileset in memory which is basically everything you need to render the basic map.

So the last step we do is setup the TileMapRenderer object. We pass in the map object (which contains all the layers and tile ids), the atlas (which contains the graphics we need to draw it), the size (x and y in pixels) and the number of tiles per block (see the next section on what this means).

### about the `TileMapRenderer` ###

The TileMapRenderer does some fancy stuff to optimise the performance. In theory you could just access the 2-dimensional array of tile id's in the map object and call batch.draw() for each of them and get the same result.

The TileMapRenderer, however, uses a spritecache to speed things up. What this means is, it clumps together the tiles into blocks. Each block for us in this example will be 5 tiles by 5 tiles. This group of 5x5 tiles will always be drawn as one.

The spritebatch pre-calculates a lot of the stuff about where to draw, and the blocks mean we are doing less individial draw() calls.

**Note:** The most important bit to note here is that because we are using a SpriteCache, the maps drawn with TiledMapRenderer are completely static. Even if you change the tiles array in the map object, the rendered map will still be the same as when you initially loaded it.

If you want non-static maps, you'll have to extend or re-implement this class and get rid of the SpriteCache. I changed the render method to just call SpriteBatch.draw() for each tile in the loop, and it allows me to change the tiles at will. The performance hit of this, though, I'm not completely sure of. With SpriteCache it was running at 30fps solid on my X10 (30fps limit), without spritecache it seems to run around 28-30 so you'll have to really think if you need dynamic tiles or not.

### Render ###

```
public void render () {
                Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

                if (automove) {
                        updateCameraPosition();
                }

                cam.zoom = 0.9f;
                cam.update();
                
                tileMapRenderer.render(cam);

                spriteBatch.begin();
                font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
                font.draw(spriteBatch, "InitialCol, LastCol: " + tileMapRenderer.getInitialCol() + "," + tileMapRenderer.getLastCol(), 20,
                        40);
                font.draw(spriteBatch, "InitialRow, LastRow: " + tileMapRenderer.getInitialRow() + "," + tileMapRenderer.getLastRow(), 20,
                        60);

                tmp.set(0, 0, 0);
                cam.unproject(tmp);
                font.draw(spriteBatch, "Location: " + tmp.x + "," + tmp.y, 20, 80);
                spriteBatch.end();
        }
```

The above is lifted from the TiledMapTest.java test code.

The main bit you're interested in is this bit :

```
tileMapRenderer.render(cam);
```

This will render your tilemap, using the currently set (Orthographic in this case) camera.
It automatically culls stuff outside of the screen (within the constraints of the blocks).

You can also see some code about getting the first/last row and column drawn.

## Figuring out what tile is at a location ##

You may need to know what tile is at a certain location, for example if the user touches it, or if you have actors with pathfinding and you need to know which tiles are traversible or not.

You can do this by accessing the appropriate tiles array for the layer you are interested in :

```
   int tileid = map.layers.get(0).tiles[y][x];
```

**Note:** here the y and x are swapped around. Also, the x and y is the coordinate of the tile in the map, not in the world or screen so you'll have to do some calculations.

**Note 2:** Also, just to confuse things more, the map is stored in a y-down coordinate system, whereas your screen and world coordinates are y-up. That's why in the example we subtract the y from the map height.

For example, to find the tile at a certain screen location (for a touch for example) you would :

```
  example here
```

## Example Source Code ##

http://code.google.com/p/libgdx/source/browse/trunk/tests/gdx-tests/src/com/badlogic/gdx/tests/TiledMapTest.java

Add your content here.  Format your content with:
  * Text in **bold** or _italic_
  * Headings, paragraphs, and lists
  * Automatic links to other wiki pages