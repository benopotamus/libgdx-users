# Native libraries #

If you are getting an UnsatisfiedLinkError you should check the following:

  * Did you put all libs' jars to the correct "libs" folders within your projects?
  * Did you set classpaths right to those libs and sources?

Maybe this helps, then:

```java

import com.badlogic.gdx.utils.GdxNativesLoader;
GdxNativesLoader.load();```


# Credits #

Thanks for contributions:

  * coherence length (forum)