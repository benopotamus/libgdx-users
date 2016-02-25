# Introduction #



md2 is a file format originally developed by id software for the Quake II engine.

libgdx supports importing md2 files, returning a renderable KeyframedModel.  This feature is currently available only via the model-loaders extension, but is planned to be part of the 1.0 release.


# Exporting md2 models from Blender #

For Blender 2.5+ use this addon:

[Blender MD2 Export Script](http://groups.google.com/group/junaio-developer/browse_thread/thread/f4d51a48fe55dbcc?pli=1)

Note: This script has to be manually run in the Blender text editor:

  * Open the Text-Editor-Window in Blender (click in the lower left corner of a blender window and you will see the Text-Editor option)
  * Open the Export Python file http://www.junaio.com/publisherDownload/md2_export_Blender2.58.zip
  * Run this script once (Alt + P)
  * Now you will see in the default export window the option to export to md2 (File -> Export -> Export to md2)


# Importing md2 models to your libgdx project #

## Still model ##

Here is some text missing for importing still models.

## Animated model ##

Please see [MD2 Keyframe Animation Tutorial](MD2_Keyframe_Animation.md) on this.


## Links ##

http://en.wikipedia.org/wiki/MD2_%28file_format%29