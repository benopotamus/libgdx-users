# Tweening #

A tween is an interpolation(change) from one value to another.


# Tweens #


## opacity tween ##


## non-linear tween ##


### bézier tween ###


# TweenEngine #
TweenEngine is a free java library and it can be used with libgdx.

To include TweenEngine functionality in your project, follow instructions below

1)Download the tween engine
**http://code.google.com/p/java-universal-tween-engine/**

2) Extract the jar files from the zip archive and copy+paste them into your project´s libs folder.

3) Add the tween engine files to your class path the same way you added libgdx files(right click on project---build path---libraries tab----add jars)
When its done successfully, tween engine files should be listed under referenced libraries folder.

4) Now, your classes must implement "**Tweenable**" interface to make TweenEngine animations possible.
After you have added "**implements Tweenable**" to your class(Actor class for example), you must add(or let eclipse do that for you) 2 methods.
```
        @Override
	public int getTweenValues(int tweenType, float[] returnValues) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void onTweenUpdated(int tweenType, float[] returnValues) {
		// TODO Auto-generated method stub
		
	}
```
Both functions return 2 parameters....both of which you will define when you create the new Tween.
The TweenManager variable must be available/accessible to the object that will create the tween.

Now that the Tweenable interface has been implemented, its time to create the TweenManager.
(...for example in the main class that implements ApplicationListener)

```
TweenManager manager;
manager = new TweenManager();
//For android
Tween.setPoolEnabled(true);
```

To update the tween engine values, and call the functions defined above, you need to add one last line
in your render() function...or some other function that runs at the game´s "fps"
`manager.update();`



Everything is now set up and all classes that implement Tweenable interface can be tweened.

```
int px = 100;
int py = 200;
  //Parameters from left to right:
  //this = object that is being moved/tweened
  //GameObject.XY = Tween type(int) defined by you, and this is the value you will receive as the first parameter in functions 
  // "getTweenValues" and "onTweenUpdated"
  //1900 = Animation duration in milliseconds
  //Elastic.INOUT = Easing function that is used. type "aurelienribon.tweenengine.equations." in eclipse and see What other Functions there are
  //.target(px,py) are the values that will be given to you in functions "getTweenValues" and "onTweenUpdated", as an array.
  //first param(px) is stored in returnValues[0], second one in returnValues[2] etc
  //.delay(100) = delays 1 second before tween begins
  //addToManager(this.root.manager) passes this tween to TweenManager, so it would be updated
Tween.to(this, GameObject.XY, 1900, Elastic.INOUT).target( px, py ).delay(100).addToManager(this.root.manager);

```



A demo application can be downloaded here

https://docs.google.com/leaf?id=0BypNinGRq6bjNDhkYmFmZmItODg0My00ZTM0LWE0MmEtYmU1NzVlNmU2MWMz&hl=en_US

Extract and copy the resulting folder into your work workspace