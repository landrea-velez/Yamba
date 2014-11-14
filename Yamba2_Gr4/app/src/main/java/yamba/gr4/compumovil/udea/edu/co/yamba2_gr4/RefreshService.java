package yamba.gr4.compumovil.udea.edu.co.yamba2_gr4;


import android.app.IntentService;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;
import java.util.List;
import android.content.ContentValues;
import android.net.Uri;

public class RefreshService extends IntentService {

    private static final String TAG = RefreshService.class.getSimpleName();
    public RefreshService() {
        super(TAG);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service Successfully Created", Toast.LENGTH_LONG).show();
        Log.v(TAG, "onCreate");
    }

    // Executes on a worker thread
    @Override
    protected void onHandleIntent(Intent intent) {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(this);
        final String username = prefs.getString("student", "student");
        final String password = prefs.getString("password", "password");

           // final String username = prefs.getString("username", "");
           // final String password = prefs.getString("password", "");
// Check that username and password are not empty
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(this,
                        "Please update your username and password",
                        Toast.LENGTH_LONG).show();
                return;
            }
            Log.d(TAG, "onStarted");
            ContentValues values = new ContentValues();
            YambaClient cloud = new YambaClient(username, password);
            try {
                int count = 0;
                List<Status> timeline = cloud.getTimeline(20);
                for (Status status : timeline) {
                    values.clear();
                    values.put(StatusContract.Column.ID,
                            status.getId());
                    values.put(StatusContract.Column.USER,
                            status.getUser());
                    values.put(StatusContract.Column.MESSAGE,
                            status.getMessage());
                    values.put(StatusContract.Column.CREATED_AT,
                            status.getCreatedAt().getTime());
                    Uri uri = getContentResolver().insert(
                            StatusContract.CONTENT_URI, values); //
                    if (uri != null) {
                        count++; //
                        Log.d(TAG,
                                String.format("%s: %s", status.getUser(),
                                        status.getMessage()));
                    }
                }
            } catch (YambaClientException e) {
                Log.e(TAG, "Failed to fetch the timeline", e);
                e.printStackTrace();
            }
            return;
        }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroyed");
    }
}


