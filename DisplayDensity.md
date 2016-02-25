# Display density #

To get the density of your screen, use

```java
Gdx.graphics.getDensity();```

The value is relative to a 160 dpi screen: If you have a 160 dpi screen installed, then 160 `*` Gdx.graphics.getDensity() equalt the actual density.