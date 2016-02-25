# How to add a native ui confirm dialog that returns the button clicked #

This example will run you through the process of implementing a native ui confirm dialog with that returns which button is clicked to the Libgdx render thread.

First you'll need to make two new interfaces in the main Libgdx project:

```java

public interface RequestHandler {
public void confirm(ConfirmInterface confirmInterface);
}
```

```java

public interface ConfirmInterface {
public void yes();
public void no();
}
```

Next you'll need to let your Activity class implement RequestHandler:
```java

public class ConfirmTestAndroidActivity extends AndroidApplication implements RequestHandler {

View gameView;

@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
RelativeLayout layout = new RelativeLayout(this);
// starts libGDX render thread
gameView = initialize(new ConfirmTest(this), false);
layout.addView(gameView);
setContentView(layout);
}

@Override
public void confirm(final ConfirmInterface confirmInterface) {
gameView.post(new Runnable() {
public void run() {
new AlertDialog.Builder(ConfirmTestAndroidActivity.this)
.setTitle("Confirm")
.setMessage("Are you sure?")
.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {
confirmInterface.yes();
dialog.cancel();
}
})
.setNegativeButton("No", new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {
dialog.cancel();
}
})
.create().show();
}
});
}
}
```

And finally, in your game class:

```java

RequestHandler requestHandler;

public ConfirmTest(RequestHandler requestHandler)
{
this.requestHandler = requestHandler;
}

private void showConfirmDialog(){
requestHandler.confirm(new ConfirmInterface(){
@Override
public void yes() {
// The user clicked yes!
}

@Override
public void no() {
// The user clicked no!

}
});
}
```

That's all there is to it :)



# Details #

Add your content here.  Format your content with:
  * Text in **bold** or _italic_
  * Headings, paragraphs, and lists
  * Automatic links to other wiki pages