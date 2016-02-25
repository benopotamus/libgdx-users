# Introduction #

**NOTE**: This guide has been moved to the official libgdx wiki, located: [here](http://code.google.com/p/libgdx/wiki/GraphicsSkin). That version is much more up-to-date. :-)

This is a an article to help with the usage of Skins within libgdx. Skins are a nifty generalized way to generate components from resources, based on a simple json-file. A lot of people are scared of the API, and therefore I will try to extract that information in layman terms on this page. The Skin API is located [here](http://code.google.com/p/libgdx/wiki/GraphicsSkin).


# Overview #
A skin can hold styles for different widgets, and the resources connected to those widgets (ninepatches, bitmap fonts etc). A skin has only one reference to a texture, but that texture may contain several images or smaller textures. This reduces the number of texture binds for rendering different widgets. The general formatting for a skin file will be on the form (from the API):
```
 {
        resources: {
                className: {
                        name: value,
                        ...
                },
                ...
        },
        styles: {
                className: {
                        name: value,
                        ...
                },
                ...
        }
 }
```

## Resources ##
Resources are the various elements we can bind to a widget. This includes NinePatch, Color, BitmapFont etc. An example of resources:
```
resources: {
                com.badlogic.gdx.graphics.g2d.NinePatch: {
                        image: { x: 0, y: 0, width: 64, height: 32 }
                },
                com.badlogic.gdx.graphics.Color: {
                        white: { r: 1, g: 1, b: 1, a: 1 },
                },
                com.badlogic.gdx.graphics.g2d.BitmapFont: {
                        default-font: { file: default.fnt }
                }
        },
```
The first resources is a [NinePatch](http://libgdx.l33tlabs.org/docs/api/com/badlogic/gdx/graphics/g2d/NinePatch.html). We define an area of the ninepatch, named "image". We could define a lot more areas and named them different things (like "up", "down" if we wanted to have the areas represent button states). Think on the namegiving as variables.

The second resources is a [Color](http://libgdx.l33tlabs.org/docs/api/com/badlogic/gdx/graphics/Color.html). Within this resource we can specify/bind several colors to different "variables". I have only created the "white" and specified values for the color white.

The last resource is the tricky one. You need to create a BitmapFont (both .png and .fnt) and put it within the folder where you save your skin-file (the json-file). Then you'll need to reference the bitmapfont-file as a file (as shown in the above example).

## Styles ##
After all resources are specified it is time to build the styles. You can consider a style as a component built from the resources. First of all you must reference a style to inflate, and then override it's fields with your own resources. An example below:
```
styles: {
                com.badlogic.gdx.scenes.scene2d.ui.TextButton$TextButtonStyle: {
                        default: {
                                font: default-font, 
                                fontColor: white, 
                                up: image
                        }
                }
        }
```
Time to decompose. First of all I reference the style [TextButtonStyle](http://libgdx.l33tlabs.org/docs/api/com/badlogic/gdx/scenes/scene2d/ui/TextButton.TextButtonStyle.html). As you see from the API (if you dare to open it), it has four fields in its field summary. These are the fields which you can "override" by using your own resources (as defined in resources).

As you see from my example we are overriding **font**, **fontColor**, and a last field called **up**. The field **up** is the ninepatch we will show when this TextButton is idle (or not pressed). One important thing to notice now, is that the **up** property is not listed in the field summary of the TextButtonStyle. The reason for this is that the up-property is a field that TextButtonStyle inherits from ButtonStyle.

# Practical example #
We will first create a skin-file by specifying some resources, a style and then put it to use in our code.
## Creating resources ##
I want to create a skin file for my textbuttons. I will call this file buttons.json. It can be created within the text editor in Eclipse, or any other text editor you may like (Vim \o/):
```
{
        resources: {
                com.badlogic.gdx.graphics.g2d.NinePatch: {
                        upImage: { x: 0, y: 0, width: 64, height: 32 }
                },
                com.badlogic.gdx.graphics.Color: {
                        white: { r: 1, g: 1, b: 1, a: 1 },
                        downFontColor: { r: 1, g: 0.1, b: 0.2, a: 1 }
                },
                com.badlogic.gdx.graphics.g2d.BitmapFont: {
                        default-font: { file: default.fnt }
                }
        },
        styles: {
                com.badlogic.gdx.scenes.scene2d.ui.TextButton$TextButtonStyle: {
                        default: {
                                font: default-font, downFontColor: downFontColor,
                                fontColor: white, up: upImage
                        }
                }
        }
 }
```
I define a region of my image as **upImage**, I define a couple of different colors and I define a default font. Then I create the TextButtonStyle, by specifying what resources to use. I save this file within my android-assets file structure (since this is where I place my textures, fonts etc):
```
project/project-android/assets/skins$ ls
buttons.json   default.fnt  default.png
```
I saved it as **buttons.json**. The two other files are the BitmapFont-files. We also need a image to be the resource which we reference to as **upImage**. I place this image within:
```
project/project-android/assets/buttons$ ls
default-button.png
```

## Finally some coding! ##
We are now ready to get down and dirty with some real coding. Well, most of the work has already been done for us as you shall soon see. Before this part I assume you have some sort of Stage that you are already drawing. We'll now initialize our Skin, load the resource file, the image file and then will start making us some TextButtons!
```
// Initialize skin
Skin skin = new Skin(Gdx.files.internal("skins/buttons.json"), Gdx.files.internal("buttons/default-button.png"));
// Inflate a new button
TextButton tbf = new TextButton("myButton", skin.getStyle(TextButtonStyle.class));
```

Thats it! We now instansiated a TextButton with the resources we specified in the style-section. Now you'll only need to add that Actor to your stage, and you'll see the component alive and drawing.

The full power of skins really gets shown when you start specify different styles with different resources. Consider the update styles snippet below:
```
 styles: {
                com.badlogic.gdx.scenes.scene2d.ui.TextButton$TextButtonStyle: {
                        default: {
                                font: default-font, downFontColor: downFontColor,
                                fontColor: white, up: upImage
                        },
                        reverse: {
                                font: default-font, downFontColor: white,
                                fontColor: downFontColor, up: upImage
                        }
                }
        }
```

We have now created a new style which will give a TextButton with another behaviour. To use the new style we only need to specify its name when loading the style:
```
TextButton anotherTbf = new TextButton("myButton2", skin.getStyle("reverse", TextButtonStyle.class));
```
(Notice that we are specifying what Style to load, after skin.getStyle).

# End notes #
This guide was a really quick and dirty mashup, and I'll refine my language and other parts of the text later on =)