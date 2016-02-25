# Introduction #

Many issues commonly come up as questions on the forum. This section will provide solutions to the most common problemsyou might come across.

# Problems #

## Wavefront (.obj) import fails on file exported from Blender ##

The ObjLoader may crash with the message "Unable to parse int..." in the log when importing a .obj file that came from Blender.  This is because some versions of Blender, when exporting .obj files, uses a carriage return character instead of a new line character. ObjLoader expects a new line character, so it cannot parse the file.

To fix this problem, append `.replace('\r', '\n')` to the String that you are loading with ObjLoader.

## Sprites in SpriteBatch are invisible ##

When drawing Sprites, you must make sure that you disable face culling to prevent backwards facing sprites from not getting drawn, like so:

`Gdx.gl.glDisable(GL10.GL_CULL_FACE);`

This is because your Sprites may all be facing away from the camera, depending on the triangle winding direction that is set.