package julian.lylly.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;

import julian.lylly.R;
import julian.lylly.model.Organizer;
import julian.lylly.model.OrganizerImpl;
import julian.lylly.model.Tag;

public class MainActivity extends AppCompatActivity {

    private Organizer organizer;
    private int view;
    private TagsView tavi;
    private ProspectsView prvi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = R.layout.activity_main;
        setContentView(view);

        helper();//TODO:delete
        //create screens:

    }

    public void goToTaskOrganizer() {
        setView(R.layout.activity_task_organizer);
    }

    public void goToProspectOrganizer() {
        setView(R.layout.activity_prospect_organizer);
        prvi = new ProspectsView(this);
    }

    public void goToTagOrganizer() {
        setView(R.layout.activity_tag_organizer);
        tavi = new TagsView(this);
    }
    
    public void onClickTaskOrganizer(View v) {
        goToTaskOrganizer();
    }

    public void onClickProspectOrganizer(View v) {
        goToProspectOrganizer();
    }

    public void onClickTagOrganizer(View v) {
        goToTagOrganizer();
    }

    public void onBackPressed() {
        switch (view) {
            case (R.layout.activity_prospect_edit):
                goToProspectOrganizer();
                break;
            case (R.layout.activity_tag_edit):
                goToTagOrganizer();
                break;
            case (R.layout.activity_prospect_organizer):
            case (R.layout.activity_tag_organizer):
            case (R.layout.activity_task_organizer):
                setView(R.layout.activity_main);
                break;
            default:
                super.onBackPressed();
        }
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
        setContentView(view);
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    private void helper() {//TODO:delete
        organizer = new OrganizerImpl();
    }

    public void onClickNewTag(View view) {
        tavi.onClickNewTag();
    }

    public void onClickTagEditOk(View view) {
        tavi.onClickEditOk();
    }

    public void onClickTagEditCancel(View view) {
        tavi.onClickCancel();
    }

    public void onClickNewProspect(View view) {
        prvi.onClickNewProspect();
    }
}
