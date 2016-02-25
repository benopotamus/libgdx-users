# Coloring a mesh #

Without adding any material the mesh just has the color it is given by glColor4f(...) setting when it is rendered.

Solid colors: Set a glColor4f(...) just before rendering your model. All faces will have that solid color applied.

## Adding color by modeler ##

You can apply color with a modeler program such as Blender. The colors will go to MTL file but are not yet parsed by OBJImporter.


## per vertex coloring ##

There are two ways to archieve this programatically:

  1. define them in the mesh, using a color Vertex Attribute
  1. Render the model in parts with mesh.render(primitive, index, length); and call glColor4f(...) in between rendering each part.