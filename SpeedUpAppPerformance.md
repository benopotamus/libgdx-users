# Speeding up app performance #

## Things to check ##

  * On some phones, images above 512x512 will slow down your app. By now using textures with a 1024x1024 size is well supported on most relevant devices.
  * Keep vertex / polygon count low.
  * Use materials / textures rather than geometry to render your models' details.
  * Use [Culling techniques](Culling.md).
  * Turn off [vsync](vsync.md) if not needed.
  * Use [Texture filters](TextureFilters.md): http://www.badlogicgames.com/wordpress/?p=1403

  * for SpriteBatch tuning see http://code.google.com/p/libgdx/wiki/SpriteBatch#Performance_tuning