package com.example.listactivitywithtwolist;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	String[] month = { "January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December" };

	String[] DayOfWeek = new String[] { "Sunday", "Monday", "Tuesday",
			"Wednesday", "Thursday", "Friday", "Saturday" };

	ArrayAdapter<String> monthAdapter, weekOfDayAdapter;
	String strMonth, strDayOfWeek;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		monthAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, month);
		weekOfDayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, DayOfWeek);

		setListAdapter(monthAdapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		if (getListAdapter() == monthAdapter) {
			strMonth = (String) getListView().getItemAtPosition(position);
			setListAdapter(weekOfDayAdapter);
			weekOfDayAdapter.notifyDataSetChanged();
		} else {
			strDayOfWeek = (String) getListView().getItemAtPosition(position);
			Toast.makeText(getBaseContext(), strMonth + ":" + strDayOfWeek,
					Toast.LENGTH_LONG).show();
			setListAdapter(monthAdapter);
			monthAdapter.notifyDataSetChanged();
		}
	}
}
