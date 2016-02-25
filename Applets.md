# Have your GDX app run in the web browser #
(initial version thanks to Kalle H.)


## Introduction ##

Libgdx do support applets. There is couple good reason for using applet distribution with your game or application.

  * Getting user feedback on a game before release can make or break your game. A great way is to distribute a test of the game as an applet inside the web browser.
  * Applet can be used for spreading the word out. Free version of your game as applet and full version for android.
  * Or you can just distribute full game as applet. No separate codebase is needed and some players might just want to play your game at browser.

<b>Really good tutorial with screenshots and code snippets:</b><br />
http://www.thesecretpie.com/2011/05/being-like-minecraft-or-how-to-run-your.html
Tutorial example applet. http://dl.dropbox.com/u/3157173/applets/helloworld/basicapplet.html

  1. Use lwjgl backend
  1. Create lwjglApplet class
  1. Remember lwjgl\_util\_applet.jar
  1. Create html page to config lwjglAppletLoader
  1. Export project as jar, exclude all that are not need to reduce size(libs, bins,etc..)
  1. Clean all signing stuff from jars. Eg. Delete the signature files in the meta-inf directories
  1. Re sign all jars.
  1. Profit

Remember use Gdx.graphics.setVSync(true) so you dont melt users graphics cards.


## Linux ##

Applets may not start on Linux when having IcedTeaPlugin instead of SunJavaPlugin installed and activated.<br />
See lengthy explanations and solution in forum:<br />
http://www.badlogicgames.com/forum/viewtopic.php?f=17&t=1023#p9962


## Relationships ##
http://code.google.com/p/libgdx-users/downloads/detail?name=helloworldapplet.zip


## Dependencies ##
  * lwjgl\_util\_applet.jar
  * gdx\_backend\_lwjgl
  * gdx-backend-lwjgl-natives
  * gdx-natives.jar
  * gdx.jar


## Javadoc links ##
TODO: to find more detailed information (if any)