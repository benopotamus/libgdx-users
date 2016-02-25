# Asset manager #

The libGDX AssetManager takes care of loading all kind of assets (e.g. load textures, bitmap fonts, texture atlases, ...). The special thing about this is, that it's done asynchronously. This means that an asset's GPU (images, ...) and other data (the Pixmap of a Texture, the glyph metrics of a BitmapFont, ...) can be loaded in separate threads (rendering thread vs. main thread) resulting in better GPU performance(???).

As an extra benefit, this can be used to implement animated loading screens.


## Resources ##

AssetManager test

http://code.google.com/p/libgdx/source/browse/trunk/tests/gdx-tests/src/com/badlogic/gdx/tests/AssetManagerTest.java