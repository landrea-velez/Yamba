package yamba.gr4.compumovil.udea.edu.co.yamba2_gr4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //
        // Inflate the menu items to the action bar.
        getMenuInflater().inflate(R.menu.main, menu); //
        return true; //
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) { //
            case R.id.Mtweet:
                startActivity(new Intent(this, SettingsActivity.class)); //
                return true; //
            case R.id.action_tweet:
                startActivity(new Intent(this, SettingsActivity.class)); //
                return true; //
            case R.id.about:
                startActivity(new Intent(this, about_activity.class)); //
                return true; //
            default:
                return false;
        }
    }
}



