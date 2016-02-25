# Building games using tiled maps #
wip ...
```
    map = TiledLoader.createMap(Gdx.files.internal("data/tiledmap/desert_astar3.tmx"));
    atlas = new SimpleTileAtlas(map, Gdx.files.internal("data/tiledmap/"));
```

...TODO...


# Resources #

http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1682


<b>Some explanation on tile engines in general:</b>

http://en.wikipedia.org/wiki/Tile_engine


## Square tile maps ##

Theory: http://www.tonypa.pri.ee/tbw/tut02.html

Tutorial: [TileMap tutorial](Tiles.md)


## Isometric (paralax) tiled ##

Example: http://oos.moxiecode.com/jailbitch/flash5/isomapmaker.html

Theory: http://www.tonypa.pri.ee/tbw/tut16.html

<b>Isometric Tilemap Rendering with libgdx:</b>

  * http://www.badlogicgames.com/wordpress/?p=2032
  * libGDX IsoCamTest as such<br />http://www.google.com/codesearch#c5txscj0tnc/trunk/tests/gdx-tests/src/com/badlogic/gdx/tests/IsoCamTest.java
  * libGDX IsoCamTest in communityTestSuite demos-3-tier-`*`<br />http://code.google.com/p/libgdx-users/source/browse/


## Hextiles ##

Theory: http://www.tonypa.pri.ee/tbw/tut25.html


# TMX map format #

https://github.com/bjorn/tiled/wiki/TMX-Map-Format


## libgdx, box2d, tiled maps: full working example, part 1/3 ##

http://dpk.net/2011/05/01/libgdx-box2d-tiled-maps-full-working-example-part-1/