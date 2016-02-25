# A simple bones export in 3dstudio max #

There are a lot of formats out there and at this moment several are supported with libgdx and a few are in the testing phase as an extension, but almost ready to be moved to the main libgdx framework. To make sure the right decisions are made in the content production stage. I asked Mario what’s the best thing to choose from, out of many many options at this moment. And the conclusion was that there is no good option. The support of all formats are really fragmented. Every modeling tool has its own interpretation and almost every free plugin and script is heavily outdated. Although most of them are post-y2k.

To use skeletal animation with skinning there aren’t many formats left. Because the only formats supporting them for libgdx are its own native g3d format, the MD5 format and the Ogre format. Collada support is dropped as a lack of resources and there isn’t a g3d exporter for 3dsmax, yet. So I’m going to tell you more about the ogre and md5 format use in 3dstudio max. Not much in detail, but by example. Since we just want to create content instead of understanding the ins and outs of the format, don’t we.

I assume you have some knowledge of 3dstudio max and I assume you know how to use google and install ogremax plugin (http://www.ogremax.com/) and download the “der\_ton” md5 script (http://www.modwiki.net/wiki/MD5_%28file_format%29#Tools).

## Using OgreMax (version 2.3.14) ##
File > Reset
New tab > Geometry button > Cylinder
Draw a cylinder. Make sure it has enough sections in height to bind to the skeleton.
Add the UVW Mapping modifier and set it to cylindrical. Fit the alignment or alter the gizmo as much as needed to fit the mapping. This way we quickly have some valid uv coordinates. Libgdx even supports transparent png’s as textures. So you can have 2d graphics floating in mid-air. Might come in handy (waving trees etc).

New tab > Systems button > Bones
Click on each segment of the cylinder to draw bones inside it to the top.
Convert cylinder to editable mesh (or collapse later on to prevent bugs with ogremax when things get too complex)
Add skin modifier to cylinder.
Envelopes should be fine for this simple test. For the skin-riggin’-newbies: With more complex objects, this is where you specify which part of the skin is deformed in relation to the skeleton.
Animate the bones (cylinder will deform with the bones, since it’s linked through the skin modifier).

### Setting up OgreMax ###

#### Scene settings: ####
![http://www.atomiccrew.com/pastebin/image001.jpg](http://www.atomiccrew.com/pastebin/image001.jpg)


#### Object settings: ####
Choose object: pick the cylinder as the root object.
Mesh animations: add

![http://www.atomiccrew.com/pastebin/image002.jpg](http://www.atomiccrew.com/pastebin/image002.jpg)


I checked copy first animation key to last for convenience purposes only (makes it loopable).

![http://www.atomiccrew.com/pastebin/image003.jpg](http://www.atomiccrew.com/pastebin/image003.jpg)


Click ok to close the dialog.
Select the cylinder and go to the ogremax toolbar menu > export > export selected objects …
In the file dialog make sure to select ogremax mesh (`*`.mesh) in the filetype filter and enter a filename. Don’t worry about the given default extension. It will get renamed to .mesh.xml and .skeleton.xml automatically since we checked the Export xml files in the scene settings earlier.
Open the skeletonmodelviewer.java test project and change the first parameter of the SkeletonModelViewer class constructor to "data/yourfilename.mesh.xml". Run the test and there’s your animated cylinder.

## Bones or Biped ? ##

Biped system rigged models are unable to export using der tons md5 (even the zero-positioned-dummy-hack didn’t work) nor ogremax :(. It’s too bad this doesn’t work. As it prevents us from using motion capture data in 3dstudio max.

## MD5 ##
Just run the script:
MAXScript > run script…
Select the script you downloaded earlier
Select the cylinder mesh

![http://www.atomiccrew.com/pastebin/image004.jpg](http://www.atomiccrew.com/pastebin/image004.jpg)


Hit the first export button
Enter the filename and your done!
With larger models it might look if 3dstudio hangs. Just get yourself a fresh cup of joe. You deserved it !
Der\_ton’s md5 export with standard bones are no problem. But because the ogre format is the preferred format for libgdx, we’ll stick with it for the time being.

## Let’s make it more interesting ##

Now we don’t want some stupid cylinder hopping in our games do we ?
So let’s make some basic character move in our libgdx environment!
To keep things simple we’re gonna draw a simple gingerbread man.
We pick the line tool, draw a gingerbread man outline like this:

![http://www.atomiccrew.com/pastebin/image005.jpg](http://www.atomiccrew.com/pastebin/image005.jpg)


Add an extrude modifier, a meshsmooth to make things smoother and a pro-optimizer in which you may kill 50% of the vertices, to keep it smooth but simple (this starts to sound like a razorblade commercial…). Collapse the modifier stack by doing a right click on the list and choose collapse all.
This way it becomes a nice clean editable mesh. Now it looks like this:

![http://www.atomiccrew.com/pastebin/image006.jpg](http://www.atomiccrew.com/pastebin/image006.jpg)


Now we add some bones.
Tab new > Systems button > Bones
Draw them in your front view to match this pattern:

![http://www.atomiccrew.com/pastebin/image007.jpg](http://www.atomiccrew.com/pastebin/image007.jpg)


Finally the little guy has some bones. You can take the end tip of the hand/head or feet and move it so see the connected bone is moving. If you like you can add some inverse kinematics by adding an IK solver. But to keep this tutorial simple and clean we just go with what we have now.
When you move the bone, the mesh isn’t moving at all. That’s because we need a skin modifier (like the cylinder example) !

First make sure the bones are nicely aligned in the middle in your top view. This way we don’t have much hassle with the envelopes.
Add the skin modifier.
Add all the bones in the skin pane.
And we’re done! Move a bone and you’ll see the sweet little guy is dancing for you in no time!
If his movement is not as you desired, just press the edit envelopes in the skin modifier to select the vertices needed.

Now undo all your modifications because we first have to save his neutral pose. This is important if you want your initial pose back for whatever purpose.
Make sure the animation timeline is at position 0 and click auto key,
select all (mesh and bones) and
click the big button with the key icon to save this state.

Goto frame 1 and do whatever you like with it. Animate it easily by freezing the mesh (select mesh, right click > freeze selection) and move the bones as you desire. For easy keyframing you can select all bones and in auto key just set a few keyframes over the timeline and you are free to move any bone. While the current position is on the keyframe marker and in auto key, it will get recorded.

Now our little dough teaser is twinkling on his toes, it’s time to add some face to it! Unfreeze all (found in the same context menu as you freezed the selection (and I promised myself not to go all beginner-talkish **sigh**)).
Select the mesh.
Add a unwrap uvw modifier to it and drag the modifier so it sits between the skin and the editable mesh in the stack.
Expand the unwrap uvw modifier and click on face.
Select all faces in the mesh en click on the edit button in the parameters pane.
Click on mapping and flatten image. Now all parts are nicely spread inside the rectangle ready for you to paint! (click a face to light up in 3d view to see where the part goes).

![http://www.atomiccrew.com/pastebin/image008.jpg](http://www.atomiccrew.com/pastebin/image008.jpg)


Right click on the map and select render uv template. And there’s your texture color plate to fill. Save this image as a png so you can load it later in libgdx as a texture.

![http://www.atomiccrew.com/pastebin/image009.jpg](http://www.atomiccrew.com/pastebin/image009.jpg)


By changing parameters in the flatten image dialog you can clean out some of the tiny particles. But for this article I don’t care about a clean texture.
Close all dialogs and head back to the main window. All is set now.
Now walk through all steps for an OgreMax export and make sure that, when adding the mesh animation at the object settings, to set the animation from frame 1 to 100 (skip frame 0 as that is our pose frame).

Done modeling ! Open Eclipse so we can get started with the code.
First start using the modelloaders skeleton viewer.
At the bottom replace the filenames with gingerbreadman and make sure you have the gingerbreadman.skeleton.xml, gingerbreadman.mesh.xml, and gingerbreadman.png in your data directory.
When it says “Couldn't load file 'gingerbreadman.skeleton.xml'”, scroll down in the console. If one of the last lines says: "Number of tracks does not equal number of joints" then make sure to uncheck the "Optimize skeleton animations" in the scene settings in OgreMax and make a new export.

When you run the viewer, you’ll see your animation:

![http://www.atomiccrew.com/pastebin/image010.jpg](http://www.atomiccrew.com/pastebin/image010.jpg)


And the mesh, animation sequence and texture are all visible and almost rendered correctly. You’ll first notice the 90 degrees rotation. That is because the axis are different in 3dstudio max due to a legacy thing. It can be changed by placing the mesh rotated inside the world or change the coordinates while importing. Also there are some vertices out of place. It has a few glitches which must have something to do with the envelopes interpretation. But with a few tweaks and some tinkering that must be solvable. Now go paint your texture!

## Conclusion ##

We covered the basics of what is and what isn’t possible with current formats in combination with 3dsmax. For simple meshes or nonhuman characters, the current methods are the best way to go. Nothing beats a custom bone skeleton in simplicity and mocap data for nonhuman characters are only useable when custom made. And since we are not doing a motion picture in libgdx. You can leave your green screens, body suits and markers in the attic for the moment.
For more complex human characters I think it’s best to have a custom maxscript for libgdx’ native g3d export with support for biped or wait for the native g3d (with skeleton support) exporter for blender. Ogre export is still in development for the latest version of blender with no ETA although some projects are keeping up the pace quite well (http://code.google.com/p/blender2ogre/). What is really cool about blender is the ability to import motion capture data directly as an armature. This way there is no separate non-supported adapted system like character studio’s biped.

MD5 support for blender is not working in the latest version at the moment.