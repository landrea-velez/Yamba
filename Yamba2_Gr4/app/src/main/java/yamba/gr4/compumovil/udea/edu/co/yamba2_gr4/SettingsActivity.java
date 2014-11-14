package yamba.gr4.compumovil.udea.edu.co.yamba2_gr4;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SettingsActivity extends Activity {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check whether this activity was created before
        if (savedInstanceState == null) {
            // Create a fragment
            StatusFragment fragment = new StatusFragment(); //
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit(); //
        }
    }
}

