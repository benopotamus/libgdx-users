# Introduction #
http://libgdx.l33tlabs.org/docs/api/com/badlogic/gdx/graphics/g2d/NinePatch.html

NinePatch is a Helper class into G2D GDX to help users to draw windows, buttons or other drawing object that needs a repeated background, like this example:<br>
<img src='http://indierpgs.com/wordpress/wp-content/uploads/2010/05/Ara-Fell.jpg' /><br>
The menus on this example used a nine patch system, spliting the image like that:<br>
<img src='http://img85.imageshack.us/img85/4492/splitted.png' border='0' height='100' />

<h1>Using NinePatch</h1>
To easy the work, use the image below:<br>
<img src='http://img84.imageshack.us/img84/4407/menuskin.png' border='0' />
<br>
Create a Helper class like that:<br>
<pre><code>import com.badlogic.gdx.Gdx;<br>
import com.badlogic.gdx.graphics.Texture;<br>
import com.badlogic.gdx.graphics.g2d.NinePatch;<br>
<br>
public class MenuNinePatch extends NinePatch {<br>
	private static MenuNinePatch instance;<br>
	<br>
	private MenuNinePatch(){<br>
		super(new Texture(Gdx.files.internal("system/menuSkin.png")), 8, 8, 8, 8);<br>
	}<br>
	<br>
	public static MenuNinePatch getInstance(){<br>
		if(instance == null){<br>
			instance = new MenuNinePatch();<br>
		}<br>
		return instance;<br>
	}<br>
}<br>
</code></pre>
Now, to use, call the singleton instance:<br>
<pre><code>MenuNinePatch nine;<br>
...<br>
nine = MenuNinePatch.getInstance();<br>
</code></pre>
And Draw:<br>
<pre><code>this.batch.begin();<br>
nine.draw(this.batch, 0, 0, Gdx.graphics.getWidth(), 100);<br>
this.batch.end();<br>
</code></pre>
The Example above draws an window using the entire width of the current screen:<br>
<img src='http://img713.imageshack.us/img713/5776/windowsb.png' border='0' />