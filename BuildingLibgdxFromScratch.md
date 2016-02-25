# Introduction #

_NOTE: As of libGDX 0.9.3, some portions of these instructions are obsolete._

This is a WIP that describes the things that I did in order to get libgdx built on my Win7 x64 platform.  I followed the instructions located here: http://code.google.com/p/libgdx/wiki/Building but ran into a couple of snags.  Hopefully, this page helps other people out.

# Details #

## Tool Setup ##

Grab all the tools as described on the official page.  When installing them, I recommend installing to directories that do not contain spaces.  That will save you headaches.

## SVN Checkout ##

Grab the latest and greatest code set from SVN.  Import the projects into your workspace.  You'll need gdx, gdx-backend-android, gdx-backend-yourfavoritehere.  It's also helpful to have all the tests.

## Build targets ##

There are a few build targets to concern yourself with.  The Java source and the native libraries.

## Modding ant files ##

Here's where I started mucking about with the build process.  The first file I jacked around with was gdx\build.xml.  I didn't want to build for all platforms, so I modified this by commenting out all antfile definitions except for build-win32.xml and build-android.xml.

Next, I updated build-win32.xml and changed the property 'prefix' from 'i586-mingw32msvc-' to just 'mingw32-' which matched my installation.

The most difficulty I had was with the build-android.xml.  The invocation of ndk-build refused to work until changed the xml to match the following:

```
	<target name="clean">
		<exec executable="bash" dir="../">
			 <arg value="${env.NDK_HOME}/ndk-build"/>
			 <arg value="clean"/>
		</exec>
	</target>

	<target name="compile-natives">
		<echo>ndk_home: ${env.NDK_HOME}</echo>
		<exec executable="bash" dir="../">
			 <arg value="${env.NDK_HOME}/ndk-build"/>
		</exec>
	</target>
```

Prior to this, I received the cryptic error: "createProcess error=193, %1 is not a valid win 32 application" during the build process.  Bleagh.  Took a bit of Googling and trial and error, but the above seems to work.

Lastly, you will need to modify the root build.xml found at your repo checkout location.  This has a reference to Mario's NDK install.  Change NDK\_HOME to match what you have.  (TBD-- How does this value get used vs. the environment variables that are configured?)

## Building ##

Once everything is set up correctly, this is pretty easy.  First, build the natives by going to the gdx/jni directory from a Cygwin window.  Type "ant".  If everything was successful, you'll have natives built up.  A good indication a fresh timestamp on gdx\libs\windows\gdx.dll.

Finally, go to the root repo directory and issue "ant".  This will create a distribution.

## Final Steps ##

At this point you should be good to go.  Use your desktop backend to test your build.

If you are deploying to an Android device, do not forget to update the related libgdx.so files that are referenced by your Android project.  You will find these in the gdx\libs\armeabi and gdx\libs\armeabi-v7a directories.

I ran into a `java.lang.UnsatisfiedLinkError` due to a new native method when I forgot to perform this step.