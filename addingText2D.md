# How to add text to 2D graphics #

The Font class is no longer in libGDX and BitmapFont class is now in force.

**Sample code from SVN:**
```java

!TextureAtlas textureAtlas = new !TextureAtlas("data/main");
font = new !BitmapFont(Gdx.files.internal("data/calibri.fnt"),
textureAtlas.findRegion("calibri"), false);```


Q: How can i write some text? Do I need font.png and font.fnt?

A: There is an embedded font "arial-15", so you can directly write using a BitmapFont. For custom text instantiate a BitmapFont, and use

```java

font.draw(spritebatch, "my-string", x, y);
```

A: you need a .png file containing every symbol you need your font to contain (e.g. alphanumeric characters). The .fnt file houses the locations of every character in the .png file.

One of the simplest ways to procure these files is through a piece of software called <a href='http://slick.cokeandcode.com/demos/hiero.jnlp'>Hiero</a>, which generates bitmap fonts of multiple sizes and styles based on your computer's TTF fonts.

A nice tutorial for the user-interface can be found <a href='http://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=4&ved=0CEMQFjAD&url=http%3A%2F%2Fwww.learn-cocos2d.com%2Fwordpress%2Fwp-content%2Fuploads%2FTutorial-Bitmap-Fonts-and-Hiero.pdf&ei=SN8-T9ueM6LUiAK1wJDTAQ&usg=AFQjCNG0d94brdFj4uXcTj5RDJkyQ_UYcQ&sig2=ZuL__ll5hs_Hox9gnVA8yg'>here</a>

## Adding fonts to a project's directory ##

Once you have made all the fonts you need, you need to add them to your project's /data folder.

# Adding fonts to a project #

## Creating your objects ##
At minimum, you need to declare two objects: a SpriteBatch to extract the characters from the .png file and your BitmapFont object.

```java
SpriteBatch spriteBatch;
BitmapFont font;
CharSequence str = "Hello World!"```

BitmapFonts can only draw text using CharSequence objects, so I'm providing one in the example.

## Using the font files in your code ##

It's best practice to use `Gdx.files.internal(Str relativePath)` to retrieve your .png and .fnt files.

```java
spriteBatch = new SpriteBatch();
font = new BitmapFont(Gdx.files.internal("data/nameOfFont.fnt"),
Gdx.files.internal("data/nameOfFont.png"), false);```

The first line in the example simply instantiates a new `SpriteBatch`, which you can learn about elsewhere in this wiki (TODO: link to SpriteBatch tutorial and/or API doc).

The second line instantiates our font.
<ul>
<li>The first argument passed is a function call to <code>Gdx.files.internal()</code> which returns a <code>FileHandle</code> object containing the .fnt file you added to your project.</li>
<li>The second argument passed is another call to <code>Gdx.files.internal()</code> which returns yet another <code>FileHandle</code> object, this time containing the .png file storing all your font characters.</li>
<li>The final argument is a boolean which indicates whether characters the font object draws will be flipped on the y-axis, appearing upside down. I'm setting this to <code>false</code> for the time being, but I'm sure there are instances where someone would want to set this boolean variable to <code>true</code> as well.</li>
</ul>

[TODO: provide examples for each constructor method in the API docs]

## Writing text on the screen ##

Text is written on-screen in the class's `draw` method, but the SpriteBatch object must be called first.

```
spriteBatch.begin();
font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
font.draw(spriteBatch, str, 25, 160);
spriteBatch.end();```

The first and last lines in the code are essential, without which you will generate lots of errors. What you really should be concerned with are the `font.setColor()` and `font.draw()` commands.

`font.setColor()` is used here as a precautionary measure. Hypothetically speaking, if the characters in your font's .png file are colored black and your screen's background is also colored black, you will never see your text when it is drawn. `font.setColor()` can change the color of your font to anything within the visible spectrum, and can even take an alpha variable if you want to add transparency to it.

`font.draw()` is where everything happens: The first argument passed is the `SpriteBatch` object. The second argument passed is the `CharSquence` object we defined at the beginning of this example. The third and fourth arguments are `int`s in the x and y position, respectively - in integer values - that indicate the text's starting position.

## Text Color ##

There are several methods included in the BitmapFont class that allow you to format your text.
```java

font.setColor(0.0f, 0.0f, 1.0f, 1.0f); // tint font blue
spriteBatch.begin();
font.draw(spriteBatch, charSeq, xPos, yPos);
spriteBatch.end();

```

TODO: write about scaling fonts to different sizes.

## String builder ##

Another approach is to have the text made up from bitmaps as described in this String builder tutorial:

http://code.google.com/p/libgdx/wiki/StringBuilder