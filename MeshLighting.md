# Lighting a Mesh #

Without any further action, models are flat shaded with the solid color they are given.

"You must specify normals along with your geometry . . . in order for lighting to work as expected" ([OpenGL FAQ](http://www.opengl.org/resources/faq/technical/lights.htm)).  The [ObjLoader class](http://code.google.com/p/libgdx/source/browse/trunk/gdx/src/com/badlogic/gdx/graphics/g3d/loaders/obj/ObjLoader.java) provides an example of setting up a mesh with normals and the [Plane](http://code.google.com/p/libgdx/source/browse/trunk/gdx/src/com/badlogic/gdx/math/Plane.java) class can perform the necessary calculations to determine a normal.

```java


// turns on lighting
gl.glEnable(GL10.GL_LIGHTING);

// Enable up to n=8 light sources: GL_LIGHTn
gl.glEnable(GL10.GL_LIGHT0);
gl.glEnable(GL10.GL_LIGHT1);
gl.glEnable(GL10.GL_LIGHT2);
gl.glEnable(GL10.GL_LIGHT3);
gl.glEnable(GL10.GL_LIGHT4);

// light position
gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, new float[]{0, 0, 1, 1}, 0);

// Light that has been reflected by other objects and hits the mesh in small amounts
gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, new float[]{0.005f, 0.005f, 0.005f, 1f}, 0);

// setting diffuse light color like a bulb or neon tube
gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, new float[]{0.9f, 0.9f, 0.7f, 1f}, 0);

// setting specular light color	like a halogen spot
gl.glLightfv(GL10.GL_LIGHT2, GL10.GL_SPECULAR, new float[]{0.9f, 0.9f, 0.7f, 1f}, 0);


// directional or positional light
// vector4f is x,y,z + w component
// w=0 to create a directional light (x,y,z is the light direction) like the sun
// w=1 to create a positional light like a fireball.
!FloatBuffer fb = !BufferUtils.newFloatBuffer(8);
fb.put(new float[]{1, 1, 1, 1});
gl.glLightfv(GL10.GL_LIGHT3, GL10.GL_POSITION, fb);



// spotlight
// high exponent values makes light cone's center brighter
!FloatBuffer fb_pos = !BufferUtils.newFloatBuffer(8);
fb_pos.put(new float[]{1, 1, 1, 1});
!FloatBuffer fb_dir = !BufferUtils.newFloatBuffer(8);
fb_dir.put(new float[]{2, 2, 2});
gl.glLightfv(GL10.GL_LIGHT4, GL10.GL_POSITION, fb_pos);
gl.glLightfv(GL10.GL_LIGHT4, GL10.GL_SPOT_DIRECTION, fb_dir);
gl.glLightf(GL10.GL_LIGHT4, GL10.GL_SPOT_CUTOFF, angle); // angle [0..180]

gl.glLightf(GL10.GL_LIGHT4, GL10.GL_SPOT_EXPONENT, exp); // exponent [0..128]


// defines how light amount reduces if model gets away from light source
// GL_CONSTANT_ATTENUATION, GL_LINEAR_ATTENUATION, GL_QUADRATIC_ATTENUATION
gl.glLightf(GL10.GL_LIGHT0, GL10.GL_CONSTANT_ATTENUATION, .05f);```