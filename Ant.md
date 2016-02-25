

# Introduction #

This is a quick run down of how we, [Brew Engine](http://www.brewengine.com), utilized an Ant script to
automatically build the latest version of our game, [Roaring Skies](http://www.brewengine.com/games/roaringskies), from an [EC2](http://aws.amazon.com/ec2/)
instance.

Our project setup is similar to that described by the [libGDX ProjectSetup guide](http://code.google.com/p/libgdx/wiki/ProjectSetup),
whereas we have separate Eclipse projects for the various platforms (e.g. Android,
desktop) and then a project for the core game code:
```
RoaringsSkies
  roaringskies-android
  roaringskies-desktop
  roaringskies
```

# Details #

## Android ##

We added our Ant building to an existing project, if you're looking to use Ant
on a brand new project, you may want to refer to some of the details laid out in
[Managing Projects from the Command Line](http://developer.android.com/guide/developing/projects/projects-cmdline.html).

### Instructions ###

We started by generating a basic build.xml file from within our existing project
(roaringskies-android):
```
$ android update project --path .
```

Then because we needed to customize the **compile** target, we copied that target
from the main\_rules.xml (found in the [Android SDK](http://developer.android.com/sdk/index.html)) and pasted it into our basic
build.xml as outlined in the instructions in the generated build.xml.

Our final build.xml ended up looking like:

**build.xml**
```
<?xml version="1.0" encoding="UTF-8"?>
<project name="RoaringSkies" default="help">

    <property name="root.dir" value=".." />
    <property name="main.project.dir" value="${root.dir}/roaringskies" />
    
    <!-- The local.properties file is created and updated by the 'android' tool.
         It contains the path to the SDK. It should *NOT* be checked into
         Version Control Systems. -->
    <property file="local.properties" />

    <!-- The ant.properties file can be created by you. It is only edited by the
         'android' tool to add properties to it.
         This is the place to change some Ant specific build properties.
         Here are some properties you may want to change/update:

         source.dir
             The name of the source directory. Default is 'src'.
         out.dir
             The name of the output directory. Default is 'bin'.

         For other overridable properties, look at the beginning of the rules
         files in the SDK, at tools/ant/build.xml

         Properties related to the SDK location or the project target should
         be updated using the 'android' tool with the 'update' action.

         This file is an integral part of the build system for your
         application and should be checked into Version Control Systems.

         -->
    <property file="ant.properties" />

    <!-- The project.properties file is created and updated by the 'android'
         tool, as well as ADT.

         This contains project specific properties such as project target, and library
         dependencies. Lower level build properties are stored in ant.properties
         (or in .classpath for Eclipse projects).

         This file is an integral part of the build system for your
         application and should be checked into Version Control Systems. -->
    <loadproperties srcFile="project.properties" />

    <!-- quick check on sdk.dir -->
    <fail
            message="sdk.dir is missing. Make sure to generate local.properties using 'android update project' or to inject it through an env var"
            unless="sdk.dir"
    />

    <!--
        Import per project custom build rules if present at the root of the project.
        This is the place to put custom intermediary targets such as:
            -pre-build
            -pre-compile
            -post-compile (This is typically used for code obfuscation.
                           Compiled code location: ${out.classes.absolute.dir}
                           If this is not done in place, override ${out.dex.input.absolute.dir})
            -post-package
            -post-build
            -pre-clean
    -->
    <import file="custom_rules.xml" optional="true" />

    <!-- Import the actual build file.

         To customize existing targets, there are two options:
         - Customize only one target:
             - copy/paste the target into this file, *before* the
               <import> task.
             - customize it to your needs.
         - Customize the whole content of build.xml
             - copy/paste the content of the rules files (minus the top node)
               into this file, replacing the <import> task.
             - customize to your needs.

         ***********************
         ****** IMPORTANT ******
         ***********************
         In all cases you must update the value of version-tag below to read 'custom' instead of an integer,
         in order to avoid having your file be overridden by tools such as "android update project"
    -->
    
    <!-- Compiles this project's .java files into .class files. -->
    <target name="-compile" depends="-build-setup, -pre-build, -code-gen, -pre-compile">
        <do-only-if-manifest-hasCode elseText="hasCode = false. Skipping...">
            <!-- If android rules are used for a test project, its classpath should include
                 tested project's location -->
            <condition property="extensible.classpath"
                    value="${tested.project.absolute.dir}/bin/classes"
                    else=".">
                <isset property="tested.project.absolute.dir" />
            </condition>
            <condition property="extensible.libs.classpath"
                    value="${tested.project.absolute.dir}/${jar.libs.dir}"
                    else="${jar.libs.dir}">
                <isset property="tested.project.absolute.dir" />
            </condition>
            <javac encoding="${java.encoding}"
                    source="${java.source}" target="${java.target}"
                    debug="true" extdirs="" includeantruntime="false"
                    destdir="${out.classes.absolute.dir}"
                    bootclasspathref="android.target.classpath"
                    verbose="${verbose}"
                    classpath="${extensible.classpath}"
                    classpathref="project.libraries.jars"
                    fork="${need.javac.fork}">
                <src path="${source.absolute.dir}" />
                <src path="${gen.absolute.dir}" />
                <src path="${main.project.dir}" />
                <classpath>
                    <fileset dir="${extensible.libs.classpath}" includes="*.jar" />
                </classpath>
                <compilerarg line="${java.compilerargs}" />
            </javac>
            <!-- if the project is a library then we generate a jar file -->
            <if condition="${project.is.library}">
                <then>
                    <echo>Creating library output jar file...</echo>
                    <property name="out.library.jar.file" location="${out.absolute.dir}/classes.jar" />
                    <if>
                        <condition>
                            <length string="${android.package.excludes}" trim="true" when="greater" length="0" />
                        </condition>
                        <then>
                            <echo>Custom jar packaging exclusion: ${android.package.excludes}</echo>
                        </then>
                    </if>

                    <propertybyreplace name="manifest.package.path" input="${manifest.package}" replace="." with="/" />

                    <jar destfile="${out.library.jar.file}">
                        <fileset dir="${out.classes.absolute.dir}"
                                includes="**/*.class"
                                excludes="${manifest.package.path}/R.class ${manifest.package.path}/R$*.class ${manifest.package.path}/Manifest.class ${manifest.package.path}/Manifest$*.class ${manifest.package.path}/BuildConfig.class"/>
                        <fileset dir="${source.absolute.dir}" excludes="**/*.java ${android.package.excludes}" />
                    </jar>
                </then>
            </if>

            <!-- if the project is instrumented, intrument the classes -->
            <if condition="${build.is.instrumented}">
                <then>
                    <echo>Instrumenting classes from ${out.absolute.dir}/classes...</echo>
                    <!-- It only instruments class files, not any external libs -->
                    <emma enabled="true">
                        <instr verbosity="${verbosity}"
                               mode="overwrite"
                               instrpath="${out.absolute.dir}/classes"
                               outdir="${out.absolute.dir}/classes">
                            <filter excludes="${manifest.package}.R,${manifest.package}.R$$*,${manifest.package}.BuildConfig" />
                            <filter value="${emma.filter}" />
                        </instr>
                    </emma>
                </then>
            </if>
        </do-only-if-manifest-hasCode>
    </target>
    
    <!-- version-tag: custom -->
    <import file="${sdk.dir}/tools/ant/build.xml" />

</project>
```

**custom\_rules.xml**
```
<?xml version="1.0" encoding="UTF-8"?>

<project name="custom_rules">
	<target name="-pre-build">
		<!-- We copy over the gdx.jar to the Android project's libs directory
			 because all attempts to reference to JARs in external directories
			 were unsuccessful. This isn't quite as pretty but it works!
			 Ignoring the copied JAR in version control removes problems with
			 duplication. -->
		<copy todir="libs" verbose="${verbose}">
			<fileset dir="${root.dir}/roaringskies/libs" includes="gdx.jar" />
		</copy>
	</target>
</project>
```

Some of the variables for the Ant script need to be defined and this was done in
the build.properties file. You'll have to replace some of the information (e.g.
the XXX parts, etc) with the correct information based on how you've setup your
project. Information related to generating a keystore can be found at [Signing Your Applications](http://developer.android.com/guide/publishing/app-signing.html).

**ant.properties**
```
ant.project.name=RoaringSkies
verbose=false
out.dir=build
proguard.enabled=true
proguard.config=proguard.cfg
key.store=roaring-skies.keystore
key.alias=XXX
```

**local.properties**
```
key.store.password=XXX
key.alias.password=XXX
```

We also had to update the default Android ProGuard configuration; our final
configuration looks like:

**proguard.cfg**
```
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# http://stackoverflow.com/questions/4525661/android-proguard-cant-find-dynamically-referenced-class-javax-swing
-dontwarn java.awt.**
-dontnote java.awt.**
-dontwarn com.badlogic.gdx.jnigen.**

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep class com.badlogic.**
-keep class * implements com.badlogic.gdx.utils.Json*
-keep class com.google.**

# https://ofdev.zendesk.com/entries/20461397-android-pointless-proguard-cfg
-keep class com.openfeint** { <methods>; }

-keepnames class * implements java.io.Serializable

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep class com.android.vending.billing.**
```

From within the Android project we could then generate an obfuscated APK (into
the build/ sub directory) with the following command:
```
$ ant release
```

### Caveats ###

Unfortunately we could not figure out a clean way to reference external JARs
using the Ant script. So we ended up having the Ant script copy the gdx.jar from
the core project's lib/ directory over to the Android project's lib/ directory
prior to building.