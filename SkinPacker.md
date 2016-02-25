# Skin Packer #
The SkinPacker found in the svn under gdx-tools can be used to automatically pack resources (TextureRegions and NinePatches) into your skin.json file.
# Summary of Steps #
  1. Create resources and save them into the input folder
  1. Setup and run the SkinPacker
  1. Copy files into project
# Details of Steps #
## Create resources ##
### TextureRegions ###
Using your favorite image editor (GIMP for example) create your images any size.  Save them into your input folder as `<filename>`.png.  That's it for TextureRegion.
### NinePatch ###
Begin the same as with TextureRegion.  When your image is drawn, add two pixels of pad on the left and top of the image.  Draw a black line of pixels in the first column and row of the pad, to designate where to split the NinePatch (left side black line should align with the height of the center patch, and the top row black line should align with the width of the center patch).

Save the file as `<filename>`.9.png in the same input folder. (don't forget the .9 part)
## Setup and Run SkinPacker ##
### Finding the SkinPacker ###
Get the Libgdx SVN Checkout, and import the projects into eclipse.  Find the "gdx-tools" project, and the SkinPacker.java is there under the com.badlogic.gdx.tools.skins package.  Scroll to the bottom of the SkinPacker.java file, and you will see the static public void main (String[.md](.md) args) method.  Here is where you can specify your paths and settings.
### File Paths ###
  1. input directory: the folder where all your raw resources will be before packing
  1. skin file: can be null.  If you specify an existing skin.json file, it will overwrite that file with the new resources but keep the styles already set.  Usually you will create a skin.json with this as null at first, and then add styles manually, and then afterward specify that file for future skin packing as you update resources.
  1. image file: the location of the packed png file.  Will create if not exist, or overwite if exists.
### Settings ###
// TODO
### Run SkinPacker ###
After paths and settings are set, simply right-click the SkinPacker.java file and Run As... Java Application.
## Copy Files into project ##
Get the resultant skin.json (or whatever you called it) and the packed png, put it into your assets/data folder and use it in your project.