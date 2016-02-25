# Using A Splash Screen #
You want a splash screen or menu for your game, but aren't sure of a best practice for switching between these two views? Have no fear! You've come to the right place.

## Setup ##
If you followed this tutorial in setting up your game in the first place:
http://code.google.com/p/libgdx/wiki/ProjectSetup

Then you're good all the way up until first part with actual code. Really, you want your overarching game class to look like this:

```
public class TMBGame extends Game
{
	@Override
	public void create()
	{		
		this.setScreen(new Splash(this));
	}
}
```
(You might have more methods than this, that's fine, this is a barebones example)

You'll see we've extended `Game` which actually already extends `ApplicationListener` for us.

## Creating the screens ##
Now you need to make two more classes, both of which will implement `Screen`. In our example, I call these classes `Splash` and `GameScreen`.

`Screen` is a nice class that provides all the same methods you're used to in your `ApplicationListener` class, but has the added benefit of being able to be called by the `Game` extending class as we've done above.

Here is an example of a Screen designed to be used as a splash screen:
```
public class Splash implements Screen
{
	private SpriteBatch spriteBatch;
	private Texture splsh;
	private Game myGame;
	
	/**
	 * Constructor for the splash screen
	 * @param g Game which called this splash screen.
	 */
	public Splash(Game g)
	{
		myGame = g;
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
		spriteBatch.draw(splsh, 0, 0);
		spriteBatch.end();
		
		if(Gdx.input.justTouched())
			myGame.setScreen(new GameScreen());
	}
	
	@Override
	public void show()
	{
		spriteBatch = new SpriteBatch();
		splsh = new Texture(Gdx.files.internal("splash.gif"));
	}

    ...
```

I've omitted some methods for conciseness. You'll also note I've made a constructor which accepts a `Game`. Here we want to pass our instance of the game class we just created. That way (as you can see in the render method) when we detect user input, whatever way that may be, or however else you might want to do it, we can tell our Game class it's time to switch over to a new screen, in this case the game itself, represented by the `GameScreen` class.

The second class you're adding to the project is `GameScreen`. It also implements `Screen`, and all your game rendering logic etc that used to be in your class that implemented `ApplicationListener` can now go in there. It will look much like `Splash`, requiring the same methods to be implemented, except now your render logic will be about rendering the game itself, you show logic will be about setting it up, and so forth as you would have normally made your game had you not had a splash screen.