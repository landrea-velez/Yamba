package yamba.gr4.compumovil.udea.edu.co.yamba2_gr4;

        import android.app.Fragment;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
        import android.os.Bundle;
        import android.preference.PreferenceFragment;
        import android.preference.PreferenceManager;
        import android.text.TextUtils;
        import android.util.Log;

        import android.app.ProgressDialog;
        import android.graphics.Color;
        import android.os.AsyncTask;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;


public class StatusFragment extends Fragment {
    private static final String TAG = StatusFragment.class.getSimpleName();
    private Button mButtonTweet;
    private EditText mTextStatus;
    private TextView mTextCount;
    private int mDefaultColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_status_activity, null, false);

        mButtonTweet = (Button) v.findViewById(R.id.buttonTweet);
        mTextStatus = (EditText) v.findViewById(R.id.editStatus);
        mTextCount = (TextView) v.findViewById(R.id.textCount);
        mTextCount.setText(Integer.toString(140));
        mDefaultColor = mTextCount.getTextColors().getDefaultColor();

        mButtonTweet.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String status = mTextStatus.getText().toString();
                PostTask postTask = new PostTask();
                postTask.execute(status);
                Log.d(TAG, "onClicked");
            }

        });

        mTextStatus.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                int count = 140 - s.length();
                mTextCount.setText(Integer.toString(count));

                if (count < 50) {
                    mTextCount.setTextColor(Color.RED);
                } else {
                    mTextCount.setTextColor(mDefaultColor);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

        });

        Log.d(TAG, "onCreated");

        return v;
    }

    class PostTask extends AsyncTask<String, Void, String> {
        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(getActivity(), "Posting",
                    "Please wait...");
            progress.setCancelable(true);
        }

        // Executes on a non-UI thread
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

        // Called after doInBackground() on UI thread
        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();
            if (getActivity() != null && result != null)
                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
        }

    }
}
