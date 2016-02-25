# Saving screenshots to disk for re-use #

This thread has a lot of information on this. Maybe someone can refine it to get it on this page...

http://www.badlogicgames.com/forum/viewtopic.php?t=1419


## Example code ##

https://github.com/gemserk/commons-gdx/blob/master/commons-gdx-core/src/main/java/com/gemserk/util/ScreenshotSaver.java

## Code ##

After having troubles passing the byte[.md](.md) array to a Pixmap and saving with PixmapIO, I mashed together various parts of methods in the ScreenUtils class and came up with a method that worked for me (hope it helps others). Uses the PNG class found in [this forum post](http://www.badlogicgames.com/forum/viewtopic.php?p=8358#p8358).

```
	public void saveScreenshot(FileHandle file) {
		Pixmap pixmap = getScreenshot(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		byte[] bytes;
		
		try {
			bytes = PNG.toPNG(pixmap, false);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		boolean append = false;
		file.writeBytes(bytes, append);
	}
	
	public Pixmap getScreenshot(int x, int y, int w, int h, boolean flipY) {
		Gdx.gl.glPixelStorei(GL10.GL_PACK_ALIGNMENT, 1);
		
		final Pixmap pixmap = new Pixmap(w, h, Format.RGBA8888);
		ByteBuffer pixels = pixmap.getPixels();
		Gdx.gl.glReadPixels(x, y, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, pixels);
		
		final int numBytes = w * h * 4;
		byte[] lines = new byte[numBytes];
		if (flipY) {
			final int numBytesPerLine = w * 4;
			for (int i = 0; i < h; i++) {
				pixels.position((h - i - 1) * numBytesPerLine);
				pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
			}
			pixels.clear();
			pixels.put(lines);
		} else {
			pixels.clear();
			pixels.get(lines);
		}
		
		return pixmap;
	}
```