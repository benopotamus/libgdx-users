# DepthBuffer #

These lines enable DepthBuffer for depth testing:

usually called in create()
```java

Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
Gdx.gl10.glDepthFunc(GL10.GL_LESS);```

usually called in render()
```java

gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);```