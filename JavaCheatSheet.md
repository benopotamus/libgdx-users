# Java cheat sheet #


## setting variables the right way / pass-by-WTF?! O.o ##

"repeat with me: Java is pass-by-copy-of-the-variable/reference-value"<br />
(http://stackoverflow.com/questions/40480/is-java-pass-by-reference)

(thx Eric S. for the following explanation in other words)

simplified and pseudocode...

```java

public class JavaIsNotExactlyPassByReference {

// This creates a String in memory set to the value "Test"
// The variable named word points to this location of the String in memory
String word = "Test";

public void main(...) {

tryToChangeTheString(word);

// This still prints "Test"!
// The word variable defined in the class still points to the original
// String object we created.
System.out.println(word)

// So how the hell do you change what the class member word points to?
// Quite simple, and it can be called from any method inside the class
// even one that has a "local" word variable - it just needs "this."
// prepended to it
this.word = "Changed";
}

public void tryToChangeTheString(String word) {
// The word variable inside this method is a new variable that points
// to the same location in memory as the word variable defined at the
// beginning of the class - but they are not the same variable.

// This prints "Test" as expected
System.out.println(word);

// This creates a new String in memory set to the value "Changed"
// and points the methods local word variable to it. This has no
// effect on the classes word variable
word = "Changed";

// This prints "Changed" as expected
System.out.println(word)

}
}```

If you reassign the variable inside the method using someObject = new Object(...); you only change what the methods local variable is pointing to. It has zero effect on the original variable that was passed into the method.

But if you are manipulating the obejct in any other way, it's fine... the local variable and the one that was passed in point to the same Obejct in memory so it will change for both.
I.e. In the pseudocode above ... calling word += "s suck" would cause both the local word variable and the class member to show "Tests suck" when you print them





## Reflection ##

<b>Java Reflection: Accessing private fields and methods<br /></b>
http://tutorials.jenkov.com/java-reflection/private-fields-and-methods.html


## Generics ##

What do they look like?

```java
List<Type>```

This is to be read as "List of Type".

"Generics provides a way for you to communicate the type of a collection to the compiler, so that it can be checked."<br />
http://download.oracle.com/javase/1,5.0/docs/guide/language/generics.html

```java
List<?>```

This is read as "List of type unknown" and means, that the type is unknown and thus can be everything; ? acts as a wildcard here.

<b>Generics tutorial:</b><br />
http://java.sun.com/j2se/1.5/pdf/generics-tutorial.pdf


## Varargs ##

What do they look like?

```java
public static boolean isPartOfMesh(String meshId, Vector3... vertices3d);```

Noticed the three dots '...' in method signature? Yep, that's them! This means that you can pass an arbitrary (not fixed) number of arguments of the given type.

http://download.oracle.com/javase/1,5.0/docs/guide/language/varargs.html



## FloatBuffer ##

What do they look like?

```java

!FloatBuffer fb_pos = !BufferUtils.newFloatBuffer(8);
fb_pos.put(new float[]{1, 1, 1, 1});```

(with libGdx BufferUtils...
If you use the "java style" you might get
Exception in thread "LWJGL Application" java.lang.IllegalArgumentException: FloatBuffer is not direct)

You need them for e.g. spotlight
```java

gl.glLightfv(GL10.GL_LIGHT4, GL10.GL_POSITION, fb_pos);
gl.glLightfv(GL10.GL_LIGHT4, GL10.GL_SPOT_DIRECTION, fb_dir);```



http://download.oracle.com/javase/1.5.0/docs/api/java/nio/FloatBuffer.html



## dummy ##

What do they look like?

```java
```

LINK\_HERE