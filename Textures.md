# Textures and how to use them #



## Performance ##

(also see [generic texture binding](GenericTextureBinding.md))

Blog article about perfomance issues with textures:<br />
<b>Know Your Texture Filters!</b><br />
http://www.badlogicgames.com/wordpress/?p=1403

Using a filter other than Nearest/Nearest for your texture costs performance.
A linear filter applied to a texture filling the whole screen is expensive as the graphics pipeline has to fetch 5 texels per screen pixel instead of only one. This has no bad consequence for small sprites, but if the sprite fills the screen, it will "kill" the gpu.


## Managed & Unmanaged (dynamic) Textures in LibGDX ##

very informative Blog article: http://www.badlogicgames.com/wordpress/?p=1073

Textures are automatically managed if you load them from a FileHandle, but you have to reload them manually in resume() and maybe in resize() method as well if they were loaded from Pixmap.


## Threading and loading Textures ##

http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1565


## Texture data handling: disposal & reloading ##

Q: I have memory issues due to re-creating textures / spriteBatches every loop.

A: You should remove the spritebatch line from the loop and just leave the batch.draws.


Q: How should I be doing cleanup. I noticed the dispose() method but how should I be using that...if I am dealing with frequently used textures it should only be when I am done with those textures that I dispose of them correct?

A: You should never dispose textures! Only if you really exit the app, but then, there are android specifics about the exit notion.


<b>Note: The new AssetManager class will address the pitfalls in the following thread!</b>

Thread needs to be converted in more readable sentences.

(21:17:31) Beta: load a texture in as a static object... dispose of it... exit application and immediately reopen it...

(21:17:43) Beta: remember this?

(21:17:45) Alpha: yes

(21:17:48) Beta: i just tried it

(21:18:11) Alpha: problem with Dalvik's idea of managing the Virtual environment

(21:18:15) Alpha: and?

(21:18:28) Beta: and i dont get background

(21:18:35) Beta: instead i have some letters

(21:18:39) Alpha: yes, that was exactly my point...

(21:18:48) Beta: i think from default font

(21:19:31) Alpha: Dalvik will keep the static object in VM, but the texture data isn't there anymore

(21:19:33) Beta: problem is, i load texture in create

(21:19:51) Beta: just as everybody told me

(21:20:04) Alpha: if you load the texture in create(), you shouldn't have this problem

(21:20:22) Alpha: as long as your not doing a if(staticObjectBlah == null) loadImage(someImage);

(21:20:25) Beta: V.backgroundSprite.getTexture().dispose();

(21:20:28) Beta: in touchDown

(21:20:41) Beta: and i get white background, immediately

(21:20:51) Beta: as expected

(21:20:55) Beta: now, from create

(21:20:57) Alpha: of course, dispose() get's rid of the texturedata

(21:21:26) Beta: 		V.backgroundTexture =

(21:21:26) Beta: 				new
Texture(Gdx.files.internal("background1.jpg"));

(21:21:26) Beta: 		V.backgroundTextureRegion =

(21:21:26) Beta: 				new TextureRegion(V.backgroundTexture, 0, 0, 800, 480);

(21:21:32) Beta: in create

(21:22:49) Alpha: err... where's V.backgroundSprite created?

(21:23:07) Beta: but i don't enter into create

(21:23:20) Beta: wait, another question

(21:23:24) Beta: how should i exit my app?

(21:23:29) Beta: with back button or home?

(21:24:05) Beta: when i "exit" with home, there is this bug, when i exit with back button, i don't have bug

(21:24:14) Beta: but my app is started from beginning

(21:24:15) Alpha: if you did what you just described, V.backgroundSprite.getTexture() now points to a texture in memory that no longer exists

(21:24:52) Beta: but home or back? which button?

(21:25:08) Alpha: it's effectively the same thing

(21:25:24) Beta: no, it's not the same, when i press home and run my app

(21:25:29) Beta: even my android menu is intact

(21:25:47) Beta: whan i hit back and rerun it, i get everything like starting after fresh install

(21:26:38) Alpha: oh you're right... home key does not schedule the app for destruction, back does

(21:27:09) Alpha: "If the user presses HOME, the activity is paused, and then stopped. If the user presses BACK, the app is paused, stopped, and destroyed."

(21:27:39) Beta: so, which case we are talking about?

(21:28:23) Beta: so, maybe we should load textures in resume()?

(21:28:40) Alpha: in the home key case, create() MIGHT not be called the next time you open the app

(21:30:20) Alpha: but, if you didn't call dispose() in the pause() method of your app, that shouldn't actually be a problem

(21:31:37) Beta: i don't understand your last sentence, but conclusion is, create have nothing to do with loading static textures

(21:31:47) Beta: when i "minimize" my app using home key

(21:31:52) Beta: i don't get create called

(21:31:56) Beta: and i am fucked up

(21:32:02) Alpha: so, home key case: app is paused()... if user returns to the app before Dalvik cleans up the process, resume() is called. Any managed textures that haven't been explicity dispose()'d of are reloaded by libgdx

(21:32:47) Alpha: back key case:  app is paused() and scheduled for destruction... if user returns to app, create() is called

(21:35:23) Alpha: home key case #2: app is paused() for a period of time, dalvik decides it needs the memory back... when user returns to app, create() is called

(21:36:44) Beta: so if textures is disposed implicitly, that means application was destroyed and get create called

(21:37:21) Alpha: where are you calling dispose()?

(21:37:40) Beta: touchDown

(21:37:44) Beta: :)

(21:38:11) Alpha: ok... whether or not you dispose() of an asset has nothing to do with whether or not create() is called

(21:38:56) Beta: sure

(21:40:23) Beta: i still don't understand how anybody can get their textures disposed and how they manage to not load them

(21:40:48) Alpha: if you are loading your assets in ApplicationListener.create(), you should dispose() of them in ApplicationListener.dispose()

(21:41:19) Alpha: if you dispose() of an asset on pause(), you will need to reload it in resume();

(21:42:40) Beta: so people do this advanced things, and don't remember to call them in resume? or what?

(21:42:57) Beta: i read here about complains about white objects

(21:43:00) Beta: and missing textures

(21:43:04) Beta: how they manage to do this?

(21:44:29) Alpha: two ways that can happen:

(21:45:14) Alpha: 1. the assets are not managed - developer forgets to dispose and reload them on pause() resume().

(21:46:44) Alpha: 2. The assets are loaded and stored as static objects. Developer does not reload these assets on create().

(21:46:55) Alpha: For #2, a certain set of events has to take place...

(21:47:18) Alpha: the user must exit the application (via back key or w/e).

(21:48:00) Alpha: Dalvik decides to keep the VM alive (which just means that all the classes stay loaded in memory)

(21:48:20) Alpha: since static objects belong to the class, that means they stay in memory too

(21:49:24) Alpha: when the app is restarted, that static object is still in memory. It never got detroyed. The Texture object (or whatever it was) is still loaded in memory, just as it was when the user exited from the application last

(21:49:34) Beta: maybe sth like this, can produce error: static Texture backgroundTexture = methodForLoadingTexture();

(21:50:04) Alpha: the problem is, the actual texturedata that was loaded on the GPU is long gone. So when you try to render this static texture, you just get white

(21:51:35) Gamma: I did have that white texture problem and I solved it when I changes static finals to statics

(21:51:42) Gamma: so final was the bad boy

(21:51:50) Alpha: the typical problem a developer will run into is this: if (Assets.textureA == null) Assets.textureA = new Texture("blah);

(21:53:36) Alpha: if the Assets class was never unloaded by Dalvik Assets.textureA is still a valid Texture object so this check fails.

(21:53:50) Alpha: but the actual textureData is gone so you get white when you try to render it

(21:54:08) Beta: i don't check if this null

(21:54:25) Beta: because i thought i can enter create just once

(21:55:11) Alpha: if you are loading your objects in create() without any checks... just always do Assets.textureA = new Texture("blah"); then you are safe

(21:56:29) Delta: Will it reload textures when application is resumed?

(21:56:33) Alpha: the only way that you would run into an issue is if you dispose() of the objects prematurely

(21:56:56) Alpha: managed textures are automatically reloaded when an application is resumed

(21:57:27) Delta: What are managed textures?

(21:57:34) Alpha: for unmanaged textures, you need to handle this yourself in the pause() and resume() methods

(21:58:01) Alpha: whether something is managed or not depends on how you created it

(21:58:26) Alpha: if you just load a Texture from a file, for example - it's managed by default

(21:58:54) Alpha: if you create a Texture by combining Pixmaps or something like that it is not managed

(22:00:28) Epsilon: oh, that explains a problem I had for my last project with lost textures on pause()

(22:00:58) Alpha: generally speaking, if you are  loading your assets directly from disk, they are managed

(22:01:37) Alpha: if you are creating them on the fly with Texture(Pixmap) Texture.draw(Pixmap) calls and the like, they ar enot managed

(22:34:24) Beta: Alpha: one more question, managed textures (loaded from files or through textureAtlas) should be disposed in pause and loaded in resume to conserve memory?

(22:52:08) Alpha: if you want to be nice to the users device, I suppose you could do that. It's not a requirement, though.


# TexturePacker #

Command-line tool for automatically integrating textures to a single image:<br />
http://code.google.com/p/libgdx/wiki/TexturePacker

GUI for TexturePacker:<br />
http://www.badlogicgames.com/forum/viewtopic.php?f=17&t=825


# Credits #

Thanks for contributions

  * Obli (forum)