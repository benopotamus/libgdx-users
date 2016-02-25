# Introduction #
(initial version by Michael.Lowfyr)

Additional code for a constructor / factory method in the gdx.graphics.Color class for creating a Color instance from a hex value.

# Details #
```java

// Expects a hex value as integer and returns the appropriate Color object.
// @param hex
//            Must be of the form 0xAARRGGBB
// @return the generated Color object
private Color colorFromHex(long hex)
{
float a = (hex & 0xFF000000L) >> 24;
float r = (hex & 0xFF0000L) >> 16;
float g = (hex & 0xFF00L) >> 8;
float b = (hex & 0xFFL);

return new Color(r/255f, g/255f, b/255f, a/255f);
}


// Expects a hex value as String and returns the appropriate Color object
// @param s The hex string to create the Color object from
// @return

private Color colorFromHexString(String s)
{
if(s.startsWith("0x"))
s = s.substring(2);

if(s.length() != 8) // AARRGGBB
throw new IllegalArgumentException("String must have the form AARRGGBB");

return colorFromHex(Long.parseLong(s, 16));
}











```