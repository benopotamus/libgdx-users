# box2d #

Very popular physics library, libGDX has a lightning fast implementation of this.


<b>How to get Box2D running with the help of the tutorial from dpk's weblog:</b><br />
http://dpk.net/2011/05/08/libgdx-box2d-tiled-maps-full-working-example-part-23/#p3


<b>This is the whole Tutrial's start:</b><br />
http://dpk.net/2011/05/01/libgdx-box2d-tiled-maps-full-working-example-part-1/


<b>Video Tutorials: Box2D for Flash</b><br />
http://www.kerp.net/box2d/


<b>Forum threads:</b><br />
How to render box2d elements: http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1927

Problem with Box2D on Android (Desktop works fine!) http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1794

<b>related blog posts</b><br />
http://www.badlogicgames.com/wordpress/?p=2017


This interesting blog article on separation of concerns might help too:<br />
http://aurelienribon.wordpress.com/2011/04/26/logic-vs-render-separation-of-concerns/

## Timestep / World step ##

What is it?

A fixed time step is recommended!

A frame rate of 45 (timestep= 1/45) is fine on galaxy s s2 and recent htc devices. U can make a simple if(desktop) step=1/300 else step=1/45

http://gafferongames.com/game-physics/fix-your-timestep

## vertice count on polygonal shapes ##

Q: Why is not possible to create polygonal shape with Box2D which has more than 8 vertices?

A: More than 8 vertices is more likely to create 180° angles between two edges, and box2d collision detection sometimes has troubles with these angles. If you need more than 8 vertices, create multiple fixtures for a single body. Each fixture defines one shape, but there is no limit on how many fixtures can be added to a single body.


# Credits #
Thanks for your contributions:

  * stdout
  * bach
  * Obli (forum)