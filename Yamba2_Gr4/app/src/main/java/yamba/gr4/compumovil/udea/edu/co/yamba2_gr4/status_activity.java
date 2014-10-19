package yamba.gr4.compumovil.udea.edu.co.yamba2_gr4;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class status_activity extends Activity implements OnClickListener {

    private static final String TAG = "StatusActivity";
    private EditText editStatus;
    private Button buttonTweet;
    private TextView textCount;
    private int defaultTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_activity);

        editStatus = (EditText) findViewById(R.id.editStatus);
        buttonTweet = (Button) findViewById(R.id.buttonTweet);
        buttonTweet.setOnClickListener(this);

        textCount = (TextView) findViewById(R.id.textCount); //First, we need to find the textCount in the inflated layout
        textCount.setText(Integer.toString(140));
        defaultTextColor = textCount.getTextColors().getDefaultColor();
        buttonTweet.setOnClickListener(this);


        editStatus.addTextChangedListener(new TextWatcher(){
            // TextWatcher methods
            @Override
            public void afterTextChanged(Editable s) {int count = 140 - editStatus.length();
                textCount.setText(Integer.toString(count));
                textCount.setTextColor(Color.GREEN);
                if (count < 10)
                    textCount.setTextColor(Color.RED);
                else
                    textCount.setTextColor(defaultTextColor);
            }
            @Override
            public void beforeTextChanged(CharSequence s,
                int start, int count,int after) {
            }
            @Override
            public void onTextChanged(CharSequence s,
                 int start, int before, int count) {
            }
        });
    }

    // Called when button is clicked
    @Override
    public void onClick(View view) {
        String status = editStatus.getText().toString();
        new PostTask().execute(status);
        editStatus.setText("");
        Log.d(TAG, "onClicked with status: " + status);
        }

    // Asynchronously posts to twitter
    private final class PostTask extends AsyncTask<String, Void, String> {
        // Called to initiate the background activity
        @Override
        protected String doInBackground(String... params) {
            YambaClient yambaCloud = new YambaClient("student", "password");
            try {
                yambaCloud.postStatus(params[0]);
                return "Successfully posted";
            } catch (YambaClientException e) {
                Log.e(TAG, "Failed to connect to twitter service", e);
                return "Failed to post to yamba service";
            }
        }

        // Called once the background activity has completed
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(status_activity.this, result, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.status_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_tweet) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
