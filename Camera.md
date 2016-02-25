# Camera #

There are two flavors of camera in libGDX: Orthographic and Perspective Camera.

2D and 3D notions, viewport setup and projections:

http://code.google.com/p/libgdx/wiki/GraphicsFundamentalsViewport


learn about a viewport's proportions, how to specify a projection, and to use Camera:

http://code.google.com/p/libgdx/wiki/ProjectionViewportCamera


Blog article on new Camera classes:<br />
http://www.badlogicgames.com/wordpress/?p=1550


## Orthographic Camera ##

Orthographic Camera uses parallel projection which ignores objects z-values. Object's width and height does not change when changing z-value.

The OrthographicCamera is a wrapper for orthographic projection used in 2D graphics:

http://code.google.com/p/libgdx/wiki/OrthographicCamera


## Perspective Camera ##

Perspective Camera uses perspective projection where object's width and height changes according to the objects' z-value as it is experienced in real world.


## Isometric View ##

Special "pseudo 3D" setting where camera looks at scene from a double 45° angle (sideways, upwards).

Isometric Tilemap Rendering with libgdx<br />
http://www.badlogicgames.com/wordpress/?p=2032