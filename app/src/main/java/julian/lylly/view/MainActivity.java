package julian.lylly.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import julian.lylly.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickTaskOrganizer(View v) {
        Intent intent = new Intent(this, TaskOrganizer.class);
        startActivity(intent);
    }

    public void onClickProspectOrganizer(View v) {
        Intent intent = new Intent(this, ProspectOrganizer.class);
        startActivity(intent);
    }

    public void onClickTagOrganizer(View v) {
        Intent intent = new Intent(this, TagOrganizer.class);
        startActivity(intent);
    }
}
