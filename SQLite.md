# SQLite #

SQLite is the native database for Android devices. Database files typically reside on the filesystem under /data/data/my.app.name/databases/

There are a few considerations when decided to work with SQLite as a data source.

## How to ship an app with SQLite data ##

There are two options, bundle an actual SQLite file in your assets folder or recreate the SQLite database on installation.

### Bundling ###

Pros:
  * Fast installation

Cons:
  * For backwards compatibility your uncompressed database file cannot be bigger than 1Mb ([solution](http://androidsnips.blogspot.in/2010/10/copying-larger-database-files-in.html))
  * It will use up twice the space on the device, one for the install file and one for the installed app.
  * Increases the size of your installation file.

### Recreating ###

Pros:
  * Smaller installation file
  * Only single database stored on device

Cons:
  * Slow installation

Either way, for large data files there are problems. Either it is going to be slow to recreate the entire database or it is going to be more to download and store on the device. Its up to you which approach you want to take.

## How to recreate the database on installation ##

  1. Create your database. You can use the [SQLite Manager](https://addons.mozilla.org/en-US/firefox/addon/sqlite-manager/) Firefox extension.
  1. Dump the database to a text file using [sqlite3](http://www.sqlite.org/download.html)
  1. Include the dump in your assets folder
  1. On installation, run the sql commands to recreate the database.

### Dumping the database to a text file ###

You can use the following commands to get SQLite to dump out a text file that will recreate your database.

```
sqlite3.exe database.sqlite
.output dump.sql
.dump
```

Then just copy dump.sql to your assets folder somewhere and on first run of your application, read the file and execute the SQL to recreate your database.

## Running SQL ##

This is where it gets a little tricky. Android has its own drivers for SQLite which are not available for libgdx when running in desktop mode. A solution is to create a wrapper for your database calls and implement different drivers for each platform.

## SQLDroid ##

Alternatively you can use JDBC drivers with the assistance of [SQLDroid](https://github.com/SQLDroid/SQLDroid). Apparently Android ships with the necessary interfaces needed to use JDBC drivers, but it does not officially ship with a driver for its built-in SQLite database engine. SQLDroid is a JDBC driver for Android's sqlite database.

To begin with you will need to create an action resolver as described in [Integrating Android Native UI Elements](http://code.google.com/p/libgdx-users/wiki/IntegratingAndroidNativeUiElements).

Here is a basic ActionResolver interface for connecting to a database
```
import java.sql.Connection;
public interface ActionResolver {
        public Connection getConnection();
}
```

Here is the implemenation for DesktopActionResolver
```
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DesktopActionResolver implements ActionResolver {

    @Override
    public Connection getConnection() {
            String url = "jdbc:sqlite:data.sqlite";
            try {
                    Class.forName("org.sqlite.JDBC");
                    return DriverManager.getConnection(url);
            } catch (ClassNotFoundException e) {
                    e.printStackTrace();
            } catch (SQLException e) {
                    e.printStackTrace();
            }
            return null;
    }

}
```

And for AndroidActionResolver
```
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class AndroidActionResolver implements ActionResolver {

    Handler uiThread;
    Context appContext;

    public AndroidActionResolver(Context appContext) {
            uiThread = new Handler();
            this.appContext = appContext;
    }

    @Override
    public Connection getConnection() {
            String url = "jdbc:sqldroid:/data/data/my.app.name/databases/data.sqlite";
            try {
                    Class.forName("org.sqldroid.SQLDroidDriver").newInstance();
                    return DriverManager.getConnection(url);
            } catch (InstantiationException e) {
                    Log.e("sql", e.getMessage());
            } catch (IllegalAccessException e) {
                    Log.e("sql", e.getMessage());
            } catch (ClassNotFoundException e) {
                    Log.e("sql", e.getMessage());
            } catch (SQLException e) {
                    Log.e("sql", e.getMessage());
            }
            return null;
    }
}
```

These two implementations of ActionResolver allow both your Android application and desktop application to get a connection to the SQLite database. Because these are both JDBC drivers you can code your application to use standard JDBC calls and it will work on both platforms.