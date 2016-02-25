# Android native ui elements with 3 tier project setup #
(thx to mcortez for the basics!)



This way sending Toast message, opening Alert Box, presenting a ListView and raising an Intent worked from renderThread to uiThread. All of these are implemented using Android native Java methods.

Please note that the Interface has to be implemented for each target as this example is written for 3-tier setup (http://code.google.com/p/libgdx/wiki/ProjectSetup). I use Android target only- desktop target has to be implemented accordingly.

# Summary #
To call native Android elements from Libgdx, you need a callback interface, which will be referenced in both the ApplicationListener on the Libgdx side, and the AndroidApplication on the Android side.

## Steps in loose order ##

### Libgdx Side with the ApplicationListener ###
  1. Create your interface (Called "ActionResolver" in this Wiki's code example).  Give it methods that you will call within the Libgdx ApplicationListener, that will be implemented in the AndroidApplication.
  1. Create a constructor in your ApplicationListener that takes the interface as a parameter.  Save a reference to this parameter in your ApplicationListener, so you can use it to call its methods (which run on the Android side). Make it public or pass it to your screens (or whatever will need to call native android elements)


### Android side with the AndroidApplication ###
  1. Implement the interface.  Either have the AndroidApplication implement it or create a new class that does, such as the ActionResolverAndroid class in this wiki's code example.
  1. Pass the interface to the ApplicationListener when initializing it, using the constructor you made that takes the interface as a parameter.  This part happens in the AndroidApplication's onCreate(..) method, before the initialize(..) call.
# Code Example #
## Add Interface to main project ##
```java

package some.package.name;

public interface !ActionResolver {
public void showToast(!CharSequence toastMessage, int toastDuration);
public void showAlertBox(String alertBoxTitle, String alertBoxMessage, String alertBoxButtonText);
public void openUri(String uri);
public void showMyList();
}
```


## Add concrete Interface to Android project ##
```java

package some.package.name

import android.app.!AlertDialog;
import android.content.Context;
import android.content.!DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;

public class !ActionResolverAndroid implements !ActionResolver {
Handler uiThread;
Context appContext;

public !ActionResolverAndroid(Context appContext) {
uiThread = new Handler();
this.appContext = appContext;
}

@Override
public void showShortToast(final !CharSequence toastMessage) {
uiThread.post(new Runnable() {
public void run() {
Toast.makeText(appContext, toastMessage, Toast.LENGTH_SHORT)
.show();
}
});
}

@Override
public void showLongToast(final !CharSequence toastMessage) {
uiThread.post(new Runnable() {
public void run() {
Toast.makeText(appContext, toastMessage, Toast.LENGTH_LONG)
.show();
}
});
}

@Override
public void showAlertBox(final String alertBoxTitle,
final String alertBoxMessage, final String alertBoxButtonText) {
uiThread.post(new Runnable() {
public void run() {
new !AlertDialog.Builder(appContext)
.setTitle(alertBoxTitle)
.setMessage(alertBoxMessage)
.setNeutralButton(alertBoxButtonText,
new !DialogInterface.!OnClickListener() {
public void onClick(!DialogInterface dialog,
int whichButton) {
}
}).create().show();
}
});
}

@Override
public void openUri(String uri) {
Uri myUri = Uri.parse(uri);
Intent intent = new Intent(Intent.ACTION_VIEW, myUri);
appContext.startActivity(intent);
}


@Override
public void showMyList() {
appContext.startActivity(new Intent(this.appContext, !MyListActivity.class));
}
}
```


## Modify main class in main project ##
```java

package some.package.name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.!InputProcessor;
import com.badlogic.gdx.graphics.GL10;

public class !ToastTest implements !ApplicationListener, !InputProcessor{
GL10 gl;
!ActionResolver actionResolver;

// This var is set by clicked item no. in !MyListActivity (see below)
public static int myListVar = 0;


!ToastTest(!ActionResolver actionResolver)
{
this.actionResolver = actionResolver;
}

@Override
public void create() {
gl = Gdx.app.getGraphics().getGL10();

gl.glMatrixMode(GL10.GL_MODELVIEW);
gl.glLoadIdentity();

actionResolver.showToast("Tap screen to open !AlertBox!", 5000);

Gdx.input.setInputProcessor(this);
}

@Override
public void render() {
// clear screen and set it to white
gl.glClearColor(1, 1, 1, 1);
gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
}

@Override
public boolean touchDown(int x, int y, int pointer, int button) {
actionResolver.showAlertBox("!AlertBox title", "!AlertBox message", "Button text");
//		actionResolver.openUri("http://www.google.com/");
return true;
}

@Override
public boolean keyDown(int keyCode) {
switch (keyCode) {
// for !ListActivity

case Input.Keys.MENU:
actionResolver.showMyList();
return true;
}
return false;
}
}
```


## Modify AndroidActivity in Android project ##
```java

package some.package.name;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.!AndroidApplication;

public class !ToastTestAndroidActivity extends !AndroidApplication {
!ActionResolverAndroid actionResolver;

@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);

actionResolver = new !ActionResolverAndroid(this);

// starts libGDX render thread
initialize(new !ToastTest(actionResolver), false);
}
}```



## Add new Class for ListActivity in Android project ##

```java

package some.package.name;

import android.app.!ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.!ArrayAdapter;
import android.widget.!ListView;

public class !MyListActivity extends !ListActivity{

/** Called when the activity is first created. */
public void onCreate(Bundle icicle) {
super.onCreate(icicle);

// Create an array of Strings that
// will be put in our !ListActivity

String[] items = new String[] {
"First",
"Second",
"Third"};

// Create an !ArrayAdapter, that will actually
// make the Strings above appear in the !ListView

this.setListAdapter(new !ArrayAdapter<String>(this,
android.R.layout.simple_list_item_1, items));
}

@Override
protected void onListItemClick(!ListView l, View v, int position, long id) {

// Vibrate for given milliseconds as user feedback
// Set the right for this in !AndroidManifest.xml !
long milliseconds = 100;
Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
vibrator.vibrate(milliseconds);

super.onListItemClick(l, v, position, id);

// Get the item that was clicked
//		Object o = this.getListAdapter().getItem(position);
//		String keyword = o.toString();

Intent toastTest = new Intent(this, !ToastTestAndroidActivity.class);

// given myListVar is a static variable in main class...
!ToastTest.myListVar = position;

startActivity(toastTest);
}

}```