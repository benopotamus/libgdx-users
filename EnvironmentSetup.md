## Official Guide ##
Please follow this very clear guide to get going:
http://code.google.com/p/libgdx/wiki/ProjectSetup

The page below adds more detail and explains what's going on.

## Gory details on project setup ##

If you develop much for android, you're probably really sick of waiting for your .apk to go to the desktop emulator or your phone. a couple minutes each time you make a source code change gets old **really** fast.
Developing with libGDX takes a smarter approach. We create a desktop app and an android wrapper. So you can basically develop blazing fast just compiling a new desktop app each time. This makes for a great dev experience, but takes a little bit more time to setup.

There are a few ways to setup.

## Simple ##
Easiest way is copy all the needed libraries into the /libs/ folder of your app. This is fine for one app, but you will end up with multiple copies of the libraries, and updating is a huge hassle.

## Shared folder for libGDX libs ##
this is a better way to work since you only need to update one location.
on unix/osx you can also use symbolic links for your /libs/ directory
however, using Eclipse virtual/linked folders will **not** work as a real /libs/ directory is needed (in the compiled .apk?) when android app starts up.

## Assets folder ##
Android and PC app are separate directory structures, but the PC is smart enough to reference assets in the android tree.
Rather than maintain two copies of all the files, the desktop project can be configured to find the assets in the Android project:

In Eclipse:
Right mouse button click on your Project Folder -> properties -> Java Build Path (left side)

Now there are 4 tabs to the right.
The first of these tabs is called "Source".

Click the Source tab, click Link Source, Browse, select the "assets" folder from your Android project and click OK.
Specify "assets" for the folder name and click Finish then OK.

### Using **relative** pathnames ###
This is even better for sharing with others. If your android and desktop projects reside in the same parent folder you should enter this as assets' path

PARENT-1-PROJECT\_LOC/YOURPROJECTSBASENAME-android/assets

(change YOURPROJECTSBASENAME according to your needs)

**re Assets folder**
http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1636&p=9166&hilit=assets#p9166

![http://wiki.libgdx-users.googlecode.com/git/images/eclipse-linking.png](http://wiki.libgdx-users.googlecode.com/git/images/eclipse-linking.png)

## Attaching source to a .jar file ##

You can attach source using the properties for a project. This allows you to quickly (F3 in eclipse) navigate to the source for GDX functions.

Go to Properties (for the Project) -> Java Build Path -> Libraries

Select the Library you want to attach source/javadoc for and then expand it, you'll see a list like so:

```
Source Attachment: (none)
Javadoc location: (none)
Native library location: (none)
Access rules: (No restrictions)
```
Select Javadoc location and then click Edit on the right hahnd side. It should be quite straight forward from there.

![http://wiki.libgdx-users.googlecode.com/git/images/attach-source.png](http://wiki.libgdx-users.googlecode.com/git/images/attach-source.png)


---

### Other resources ###