# Button #

This page is on how to implement a button or clickable image.
There are multiple ways to archieve this for sure depending on what your after.


## the most simple ##

Draw a [Sprite](Sprites.md) (with [SpriteBatch](SpriteBatch.md)) and determine if it was touched from the touched Pixel on screen by evaluating sth. like

```java

if( (touchpoint.x<100) && (touchpoint.y<100) ){
// payload
}```


## using an Actor ##

### Image ###

Subclass the Actor Image class and override it's touchDown method:<br />
http://libgdx.l33tlabs.org/docs/api/com/badlogic/gdx/scenes/scene2d/Actor.html#touchDown%28float,%20float,%20int%29

### ImageButton ###