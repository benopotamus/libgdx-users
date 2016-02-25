# Lua tutorial #
You can download the project with all natives compiled(Android, Linux, Windows, and Mac) on the downloads tab or you can download just the natives on the download tab

<br />
Hello! and welcome to a mind blowing tutorial! :P In this tutorial I will show you how to use Lua with libgdx. This will work with android and any other OS. So ill start out will a list of the things you will need
  * Lua (the latest version)
  * Luajava(the latest and greatest)
  * Androlua(for compiling the android version of Luajava...not needed because you can set this up yourself but it makes things easier...only needed for Android)
  * NDK(If you are using Android)
  * JDK(If you are using anything other then Android)
  * For mac you will need to download the javadeveloper package from http://connect.apple.com
  * A lot of time(You might get mad at how difficult NDK and JDK can be)
  * Knowledge of use of NDK and JDK
  * Knowledge of use of javah(for compiling luajava.h....I will include file somewhere)

Lets get started! To make things more organized lets make a folder called LuaJavaproject, anywhere you want.

## Step number one: Download Lua, Luajava, and Androlua. ##
  * Lua -> http://www.lua.org/ftp/lua-5.1.4.tar.gz
  * Luajava -> http://luaforge.net/frs/?group_id=10 (for windows you can download the already compiled version and you won't need lua)
  * Androlua -> https://github.com/mkottman/AndroLua

For convinence place the extracted folders in LuaJavaproject(the folder we made from before)
Ok so if you are doing the full tutorial(Android and another OS) you should have AndroLua, lua-5.1.4, and luajava-1.1 in your LuaJavaproject folder. Next we will make the natives

## Step number two: Compiling the natives ##
**NOTE**: _I am doing this on **linux**....It might be a slight bit different on Mac and Windows. I will edit this later for Mac but I don't program with windows so your on your own. Luckly for windows users they can download a precompiled version of luajava so you dont even need to compile the natives for your computer. You can skip to Step 3, if you dont want the android native._

**Compiling for Android:**This is easy thanks to Androlua. Open up terminal and go into the Androlua dictionary. Use the NDK Build command
EX. how i did it on my computer
```
cd Desktop/Luajavaproject/AndroLua
/home/cameron/Desktop/AndroidWorkspace/android-ndk-r6/ndk-build
```
It should come up with :
```
StaticLibrary  : liblua.a
SharedLibrary  : libluajava.so
Install        : libluajava.so => libs/armeabi/libluajava.so
```

  1. Now quit terminal and open up Androlua/libs and copy the armeabi folder.
  1. Go backwards into your LuaJavaproject folder and make a new folder called 'libs' and in that make 'android' and past 'armeabi'.
  1. duplicate 'armeabi' and rename the second to 'armeabi-v7a'.
Congratulations! you compiled luajava for android! If you only want it for android then continue on to Step number three. If you want it for your computer too, continue on with this step.

**Compile for linux:**(will work with windows and Mac but you might need a little bit of change): This is where it gets hard. This, I would say, is the hardest part of the tutorial.
First off we need to get lua Library and Includes from http://luabinaries.sourceforge.net/download.html. put these files in Luajavaproject/lua-5.1.4. then back out into luajavaproject and go into luajava-1.1 and edit the config file to your paths. For example mine is:
```
LUA_DIR= /home/cameron/Desktop/Luajavaproject/lua-5.1.4
LUA_LIBDIR= /home/cameron/Desktop/Luajavaproject/lua-5.1.4
LUA_INCLUDES= /home/cameron/Desktop/Luajavaproject/lua-5.1.4/include
JDK= /usr/lib/jvm/java-6-openjdk
LIB_LUA=/home/cameron/Desktop/Luajavaproject/lua-5.1.4/liblua5.1.a
```
you are also going to need to go into luajava-1.1/src/c and make a file called luajava.h and copy the code from below. You can make this with the javah tool using LuaState.class but i figured copying and pasting would be easier. :)

now go into terminal and cd into luajavaproject/luajava-1.1 and type make. It should make it and you will have .so(or .dll or .dylib depending on OS). If you get a error that says
```
"make: No rule to make target `/bin/java', needed by `checkjdk'.  Stop."
```
its because you dont have your java path set correctly or you dont have jdk installed. <br />
Take the .so file from luajavaproject and copy it to luajavaproject/libs/linux(you will need to create the linux folder)the name really doesnt matter. you can name it linux, windows, mac, whatever you want. <br />
Congratulations! you are done compiling the natives!

## Step 3: The example project ##
Now we will get to use Lua with LibGDX! Yay!
Alright so do this:
  1. Start a new Java project and add the libs that libgdx needs.
  1. make a new folder(i call it lib) and add in your OS native file.
  1. rename the native file to libluajava.so(or dll or dylib) or it wont work
  1. go into project properties and into  Java Build Path -> Libraries
  1. click the arrow on JRE System library and edit Native Library Location to the folder containting the native library(for me, lib)
  1. now go into that folder(luajavaproject) from step 1 and 2 and then into androlua.
  1. go into src and copy the org folder.
  1. go into eclipse and paste the org folder on your projects src.
  1. you should have org.keplerproject.luajava now
  1. now in your on package make DesktopStarter and copy the code for DesktopStarter  below(same as beginners tutorial)
  1. now make game.java and copy the code for it below.
  1. now make LoadScript.java and paste the code for it below.
  1. now you can make the android project like the beginners tutorial
  1. add the native files...MAKE SURE THEY ARE IN armeabi AND armeabi-v7a! in the libs folder!
  1. link the assets folder from android project to the desktop project
  1. make a new file and call it helloworld.lua and copy the code below.
  1. run it!
the code is commented but if you have any questions just ask me :) <br />
common issues:
  * didnt rename OS native lib to libluajava (#3)
  * didnt link Native source (#4-5)

DesktopStarter:
```
package tutorial;
import com.badlogic.gdx.backends.jogl.JoglApplication;

public class DesktopStarter {
        public static void main (String[] argv) {
                new JoglApplication(new game(), "My First Triangle", 480, 320, false);               
        }
}
```

game:
```
package tutorial;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class game implements ApplicationListener {
        String text = "ERROR:Not Loading!";
        OrthographicCamera camera;
        GL10 gl;
        SpriteBatch batch;
        BitmapFont font;
        LoadScript script;
        @Override
        public void create() {
        	camera = new OrthographicCamera(1000, 1500);
        	camera.position.set(0,0,0);
        	gl = Gdx.graphics.getGL10();
        	batch = new SpriteBatch();
        	font = new BitmapFont();
        	//Load a script with my LoadScript Class
        	script = new LoadScript("helloworld.lua");
        }

        @Override
        public void dispose() { }

        @Override
        public void pause() { }

        @Override
        public void render() {
        	camera.apply(Gdx.gl10);
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            camera.update();
            camera.apply(gl);
            
            //This is loading a Global with my class
            text = script.getGlobalString("int");
            //Run a Lua Function with my class
            script.runScriptFunction("render", this.gl);
            //Update doesnt work "properly" you can edit the script and the app will change
            //But it will crash every once in a while :/ 
            script.update();
            
            batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.begin();
            //Draw the Global
            font.draw(batch, text, 100, 100);
            //Run a Lua Function with two objects with my class
            //You can edit LoadScript.java and add more objects
            script.runScriptFunction("fontBatch", this.font, this.batch);
            batch.end();
        }

        @Override
        public void resize(int width, int height) { }

        @Override
        public void resume() { }
}
```

LoadScript:
```
package tutorial;

import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
	public class LoadScript {
		public LuaState luaState;
		private String fileName;
/**
* Constructor
* @param fileName File name with Lua script.
*/
		public LoadScript(final String fileName) 
		{
			this.luaState = LuaStateFactory.newLuaState();
			this.luaState.openLibs();
			//This next part we do because Android cant use LdoFile instead
			//we load the lua file using gdx and convert it into a string and load it 
			FileHandle handle = Gdx.files.internal(fileName);
            String file = handle.readString();
            this.luaState.LdoString(file);
            this.fileName = fileName;
		}
		
		public void update()
		{
			FileHandle handle = Gdx.files.internal(this.fileName);
            String file = handle.readString();
			this.luaState.LdoString(file);
		}
/**
* Ends the use of Lua environment.
*/
		public void closeScript() {
			this.luaState.close();
		}
/**
* Call a Lua function inside the Lua script to insert
* data into a Java object passed as parameter
* @param functionName Name of Lua function.
* @param obj A Java object.
*/
		public void runScriptFunction(String functionName, Object obj) {
			this.luaState.getGlobal(functionName);
			this.luaState.pushJavaObject(obj);
			this.luaState.call(1, 0);
		}
		
		//Same thing as above but with another object
		public void runScriptFunction(String functionName, Object obj, Object obj2) {
			this.luaState.getGlobal(functionName);
			this.luaState.pushJavaObject(obj);
			this.luaState.pushJavaObject(obj2);
			this.luaState.call(2, 0);
		}
		
		public String getGlobalString(String globalName)
		{
			this.luaState.getGlobal(globalName);
			return this.luaState.toString(luaState.getTop());
		}
}
```

Helloworld.lua:
```
--This is a 'Lua Global'

int = "Hello World from a Lua Global!"



--This is a function gl is the object we pushed(this.gl)

function render(gl)

--set the color of the screen

	gl:glClearColor(0, 0, 0, 1)

end



function fontBatch(font, batch)

--set the color for this font and draw it

	font:setColor(1, 1, 0, 1)

	font:draw(batch, "Hello World from a Lua Function!", 120, 200)

end
```

That should be about it! Have fun scripting! I probably messed up somewhere so if anything doesnt work, post a comment or email me at cam.smith13.cs@gmail.com

LuaJava.h:
```
/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_keplerproject_luajava_LuaState */

#ifndef _Included_org_keplerproject_luajava_LuaState
#define _Included_org_keplerproject_luajava_LuaState
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _open
 * Signature: ()Lorg/keplerproject/luajava/CPtr;
 */
JNIEXPORT jobject JNICALL Java_org_keplerproject_luajava_LuaState__1open
  (JNIEnv *, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _close
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1close
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _newthread
 * Signature: (Lorg/keplerproject/luajava/CPtr;)Lorg/keplerproject/luajava/CPtr;
 */
JNIEXPORT jobject JNICALL Java_org_keplerproject_luajava_LuaState__1newthread
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _getTop
 * Signature: (Lorg/keplerproject/luajava/CPtr;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1getTop
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _setTop
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1setTop
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushValue
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushValue
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _remove
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1remove
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _insert
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1insert
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _replace
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1replace
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _checkStack
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1checkStack
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _xmove
 * Signature: (Lorg/keplerproject/luajava/CPtr;Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1xmove
  (JNIEnv *, jobject, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isNumber
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isNumber
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isString
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isString
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isCFunction
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isCFunction
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isUserdata
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isUserdata
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _type
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1type
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _typeName
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_keplerproject_luajava_LuaState__1typeName
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _equal
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1equal
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _rawequal
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1rawequal
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _lessthan
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1lessthan
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _toNumber
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)D
 */
JNIEXPORT jdouble JNICALL Java_org_keplerproject_luajava_LuaState__1toNumber
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _toInteger
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1toInteger
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _toBoolean
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1toBoolean
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _toString
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_keplerproject_luajava_LuaState__1toString
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _objlen
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1objlen
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _toThread
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)Lorg/keplerproject/luajava/CPtr;
 */
JNIEXPORT jobject JNICALL Java_org_keplerproject_luajava_LuaState__1toThread
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushNil
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushNil
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushNumber
 * Signature: (Lorg/keplerproject/luajava/CPtr;D)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushNumber
  (JNIEnv *, jobject, jobject, jdouble);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushInteger
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushInteger
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushString
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushString__Lorg_keplerproject_luajava_CPtr_2Ljava_lang_String_2
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushString
 * Signature: (Lorg/keplerproject/luajava/CPtr;[BI)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushString__Lorg_keplerproject_luajava_CPtr_2_3BI
  (JNIEnv *, jobject, jobject, jbyteArray, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushBoolean
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushBoolean
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _getTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1getTable
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _getField
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1getField
  (JNIEnv *, jobject, jobject, jint, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _rawGet
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1rawGet
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _rawGetI
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1rawGetI
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _createTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1createTable
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _getMetaTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1getMetaTable
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _getFEnv
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1getFEnv
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _setTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1setTable
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _setField
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1setField
  (JNIEnv *, jobject, jobject, jint, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _rawSet
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1rawSet
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _rawSetI
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1rawSetI
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _setMetaTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1setMetaTable
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _setFEnv
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1setFEnv
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _call
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1call
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pcall
 * Signature: (Lorg/keplerproject/luajava/CPtr;III)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1pcall
  (JNIEnv *, jobject, jobject, jint, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _yield
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1yield
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _resume
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1resume
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _status
 * Signature: (Lorg/keplerproject/luajava/CPtr;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1status
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _gc
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1gc
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _error
 * Signature: (Lorg/keplerproject/luajava/CPtr;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1error
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _next
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1next
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _concat
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1concat
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pop
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pop
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _newTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1newTable
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _strlen
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1strlen
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isFunction
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isFunction
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isTable
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isNil
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isNil
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isBoolean
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isBoolean
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isThread
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isThread
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isNone
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isNone
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isNoneOrNil
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isNoneOrNil
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _setGlobal
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1setGlobal
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _getGlobal
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1getGlobal
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _getGcCount
 * Signature: (Lorg/keplerproject/luajava/CPtr;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1getGcCount
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LdoFile
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LdoFile
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LdoString
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LdoString
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LgetMetaField
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LgetMetaField
  (JNIEnv *, jobject, jobject, jint, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LcallMeta
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LcallMeta
  (JNIEnv *, jobject, jobject, jint, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _Ltyperror
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1Ltyperror
  (JNIEnv *, jobject, jobject, jint, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LargError
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LargError
  (JNIEnv *, jobject, jobject, jint, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LcheckString
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_keplerproject_luajava_LuaState__1LcheckString
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LoptString
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_keplerproject_luajava_LuaState__1LoptString
  (JNIEnv *, jobject, jobject, jint, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LcheckNumber
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)D
 */
JNIEXPORT jdouble JNICALL Java_org_keplerproject_luajava_LuaState__1LcheckNumber
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LoptNumber
 * Signature: (Lorg/keplerproject/luajava/CPtr;ID)D
 */
JNIEXPORT jdouble JNICALL Java_org_keplerproject_luajava_LuaState__1LoptNumber
  (JNIEnv *, jobject, jobject, jint, jdouble);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LcheckInteger
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LcheckInteger
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LoptInteger
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LoptInteger
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LcheckStack
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1LcheckStack
  (JNIEnv *, jobject, jobject, jint, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LcheckType
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1LcheckType
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LcheckAny
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1LcheckAny
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LnewMetatable
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LnewMetatable
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LgetMetatable
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1LgetMetatable
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _Lwhere
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1Lwhere
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _Lref
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1Lref
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LunRef
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1LunRef
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LgetN
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LgetN
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LsetN
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1LsetN
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LloadFile
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LloadFile
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LloadBuffer
 * Signature: (Lorg/keplerproject/luajava/CPtr;[BJLjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LloadBuffer
  (JNIEnv *, jobject, jobject, jbyteArray, jlong, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LloadString
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LloadString
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _Lgsub
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_keplerproject_luajava_LuaState__1Lgsub
  (JNIEnv *, jobject, jobject, jstring, jstring, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LfindTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_keplerproject_luajava_LuaState__1LfindTable
  (JNIEnv *, jobject, jobject, jint, jstring, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openBase
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openBase
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openTable
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openIo
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openIo
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openOs
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openOs
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openString
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openString
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openMath
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openMath
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openDebug
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openDebug
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openPackage
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openPackage
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openLibs
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openLibs
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    luajava_open
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState_luajava_1open
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _getObjectFromUserdata
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_org_keplerproject_luajava_LuaState__1getObjectFromUserdata
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isObject
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)Z
 */
JNIEXPORT jboolean JNICALL Java_org_keplerproject_luajava_LuaState__1isObject
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushJavaObject
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/Object;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushJavaObject
  (JNIEnv *, jobject, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushJavaFunction
 * Signature: (Lorg/keplerproject/luajava/CPtr;Lorg/keplerproject/luajava/JavaFunction;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushJavaFunction
  (JNIEnv *, jobject, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isJavaFunction
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)Z
 */
JNIEXPORT jboolean JNICALL Java_org_keplerproject_luajava_LuaState__1isJavaFunction
  (JNIEnv *, jobject, jobject, jint);

#ifdef __cplusplus
}
#endif
#endif
```
# Credit #

Thanks for your contributions:

  * CamSmith