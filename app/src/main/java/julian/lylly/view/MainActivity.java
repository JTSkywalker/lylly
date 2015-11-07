package julian.lylly.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.joda.time.Duration;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import julian.lylly.R;
import julian.lylly.model.Organizer;
import julian.lylly.model.OrganizerImpl;
import julian.lylly.model.Prospect;
import julian.lylly.model.Tag;

public class MainActivity extends AppCompatActivity {

    private Organizer organizer;
    private int view;
    private TagsView tavi;
    private ProspectsView prvi;
    private ProspectEdit pred;

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

    public void goToProspectEdit(Prospect prospect) {
        setView(R.layout.activity_prospect_edit);
        pred = new ProspectEdit(this, prospect);
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
        Tag tag1 = new Tag("du"), tag2 = new Tag("dömel"), tag3 = new Tag("kenguru");
        organizer = new OrganizerImpl();
        organizer.addTag(tag1);
        organizer.addTag(tag2);
        organizer.addTag(tag3);
        organizer.addTag(new Tag("düm düm düüüüm düdüum"));
        List<Integer> weights = new ArrayList<>();
        weights.add(5);
        weights.add(5);
        weights.add(3);
        weights.add(3);
        weights.add(3);
        weights.add(5);
        weights.add(5);
        organizer.addProspect(new Prospect("test", tag1,
                new LocalDate(2015,10,10), new LocalDate(2015,10,15),
                new Duration(1000*60*60*2), new Duration(1000*60*60*3), weights));
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
        goToProspectEdit(null);
    }

    public void onClickProspectEditOk(View view) {
        pred.onClickOk();
    }

    public void onClickProspectEditCancel(View view) {
        pred.onClickCancel();
    }
}
