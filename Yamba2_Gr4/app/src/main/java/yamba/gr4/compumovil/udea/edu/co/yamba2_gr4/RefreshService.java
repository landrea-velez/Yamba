package yamba.gr4.compumovil.udea.edu.co.yamba2_gr4;


import android.app.IntentService;
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

public class RefreshService extends IntentService {
    static final String TAG = "RefreshService";

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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //The default username and password for the yamba.marakana.com
        //service is student/password.
        final String username = prefs.getString("student", "student");
        final String password = prefs.getString("password", "password");
        //no entraba porque el usr y pss estaba vacio
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Please update your username and password",
                    Toast.LENGTH_LONG).show();
            return;
        }
        Log.d(TAG, "onStarted");//mesaje de prueba en logcat
        YambaClient cloud = new YambaClient(username, password); //
        try {
            List<Status> timeline = cloud.getTimeline(20); //
            for (Status status : timeline) { //
                Log.d(TAG,
                        String.format("%s: %s", status.getUser(),
                                status.getMessage())); //
            }
        } catch (YambaClientException e) { //
            Log.e(TAG, "Failed to fetch the timeline", e);
            e.printStackTrace();
        }
        return;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Successfully Destroyed", Toast.LENGTH_LONG).show();
        Log.v(TAG, "onDestroy");
        super.onDestroy();
    }



}
