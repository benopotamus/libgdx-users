# Asynchronous loading #

AsyncTasks are non-blocking, which means that they are returning directly to the previous task and do their job in the background.
To prevent NullPointerExceptions in the code being executed before the AsyncTask returns, objects depending on or created by the loading file(s) must not be accessed!<br />
Instead you should query if the AsyncTask has returned.


http://developer.android.com/reference/android/os/AsyncTask.html

```java

private class !DownloadFilesTask extends !AsyncTask<URL, Integer, Long> {
// payload
}```

In case you don't need the parameters, set them individually or all to Void:

```java

private class !DownloadFilesTask extends !AsyncTask<Void, Void, Void> {
// payload
}```


## Load screen ##

General approach:

  1. asynchrounous loading (in the background)
  1. displaying a load screen, e.g. by setContentView(R.id.loadscreen); <b>// in Android Activity's onCreate()</b>
  1. reset display to normal when background loading has finished by onPostExecute(oldValueOfContentView);


The load screen could contain a simple textview showing "loading..." and an "indeterminate progress bar" for the user getting the feeling that something is still happening.



### indeterminate progress bar ###

http://developer.android.com/reference/android/widget/ProgressBar.html

http://androidyou.blogspot.com/2010/07/android-showing-indeterminate-progress.html

See section "ProgressBar View" in:
http://mobiforge.com/designing/story/understanding-user-interface-android-part-2-views

http://developer.android.com/resources/samples/ApiDemos/src/com/example/android/apis/view/ProgressBar3.html

http://stackoverflow.com/questions/3225889/how-to-center-progress-indicator-in-progressdialog-easily-when-no-title-text-pas

http://download.oracle.com/javase/1.4.2/docs/guide/swing/1.4/pb.html