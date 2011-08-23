package com.wiztoybox.examplelauncher;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wiztoybox.examplelauncher.examples.ColoredBackground;
import com.wiztoybox.examplelauncher.examples.ColoredLines;
import com.wiztoybox.examplelauncher.examples.ColoredSquares;
import com.wiztoybox.examplelauncher.examples.MovingSprites;

public class ExampleLauncher extends ListActivity {

	static String[] strings;

	private Demo[] demos = {
			// Add another line for each new demo.
			// Parameters: Title, class [, orientation]
			new Demo("Colored Background", ColoredBackground.class),
			new Demo("Colored Lines", ColoredLines.class),
			new Demo("Colored Squares", ColoredSquares.class,
					ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE),
			new Demo("Moving Sprites", MovingSprites.class) };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.example_launcher);

		strings = new String[demos.length];
		for (int i = 0; i < demos.length; i++) {
			strings[i] = demos[i].title;
		}

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, strings));
	}

	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {
		Intent intent = new Intent(this, demos[position].classname);
// TODO: both do not work yet
//		this.startService(intent);
		this.startActivity(intent);
	}
}