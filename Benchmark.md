# Benchmark #

During development you want to know how parts of your implementation perform.
Benchmarking will give you an idea of this.

You can log the results to console (desktop target) / LogCat (Android target) or render them to screen (which also costs a small amount of performance).


## code fragment benchmark ##

```java

// for benchmarking
long before, after;

before = System.currentTimeMillis();
// your payload here!
after = System.currentTimeMillis();
Log.out("Execution took " + (after-before) + "ms");```



## render time benchmark ##

<b>add to render()</b><br />
```java

// the time span between the current frame
// and the last frame in seconds
float deltaTime = Gdx.graphics.getDeltaTime();```