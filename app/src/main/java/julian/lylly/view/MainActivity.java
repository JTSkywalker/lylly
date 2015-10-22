package julian.lylly.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import julian.lylly.R;
import julian.lylly.model.Organizer;

public class MainActivity extends AppCompatActivity {

    private Organizer organizer;
    private int view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = R.layout.activity_main;
        setContentView(view);
    }

    public void onClickTaskOrganizer(View v) {
        view = R.layout.activity_task_organizer;
        setContentView(view);
    }

    public void onClickProspectOrganizer(View v) {
        view = R.layout.activity_prospect_organizer;
        setContentView(view);
    }

    public void onClickTagOrganizer(View v) {
        view = R.layout.activity_tag_organizer;
        setContentView(view);
    }

    public void onBackPressed() {
        if (view == R.layout.activity_prospect_organizer
                || view == R.layout.activity_tag_organizer
                || view == R.layout.activity_task_organizer) {
            view = R.layout.activity_main;
            setContentView(view);
        } else {
            super.onBackPressed();
        }
    }
}
