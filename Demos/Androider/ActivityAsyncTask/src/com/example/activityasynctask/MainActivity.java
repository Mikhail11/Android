package com.example.activityasynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView myTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myTextView = (TextView) findViewById(R.id.text);

		new TestAsyncTask().execute();
	}

	private class TestAsyncTask extends AsyncTask<Void, Long, Void> {


		private long counter;

		@Override
		protected void onProgressUpdate(Long... progress) {
			myTextView.setText(String.valueOf(progress[0]));
		}
		
		@Override
		protected void onPostExecute(Void result) {
			myTextView.setText(String.valueOf(counter));
		}


		@Override
		protected Void doInBackground(Void... params) {
			
            long i = 0;
            counter = 0;
            while( i <= 500 ) 
            {
                    try{
                            Thread.sleep( 10 );
                            publishProgress( i );
                            i++;
                            counter += i*i;
                    } catch( Exception e ){
                            Log.i("makemachine", e.getMessage() );
                    }
            }
			
			return null;
		}
	}

}