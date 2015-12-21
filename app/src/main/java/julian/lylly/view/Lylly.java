package julian.lylly.view;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.joda.time.Duration;
import org.joda.time.LocalDate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import julian.lylly.R;
import julian.lylly.model.Organizer;
import julian.lylly.model.OrganizerImpl;
import julian.lylly.model.Prospect;
import julian.lylly.model.Tag;
import julian.lylly.model.Task;

public class Lylly extends AppCompatActivity {

    private Organizer organizer;
    private int view;
    private TagsView tagsView;
    private ProspectsView prospectsView;
    private ProspectEdit prospectEdit;
    private TasksView tasksView;
    private TaskEdit taskEdit;
    // To identify the application when logging
    public static final String TAG = "LYLLY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = R.layout.activity_main;
        setContentView(view);
        Log.d(TAG, "Lylly is now online.");
    }

    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume. is organizer null: " + (organizer == null));
        loadIfNecessary();
        //helper();
    }

    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        save();
    }

    public void onSaveInstanceState(Bundle bundle) {//Do we need this?
        Log.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(bundle);
        save();
    }

    public void onRestoreInstanceState(Bundle bundle) {//Do we need this?
        Log.d(TAG, "onRestoreInstanceState");
        super.onRestoreInstanceState(bundle);
        loadIfNecessary();
    }

    private void loadIfNecessary() {
        if (organizer != null) {
            Log.d(TAG, "No loading needed");
            return;
        }
        try{
            FileInputStream fis = openFileInput("lyl.ly");
            ObjectInputStream ois = new ObjectInputStream(fis);
            organizer = (Organizer) ois.readObject();
            ois.close();
            Log.d(TAG,"The load action was successful.");
        } catch (FileNotFoundException ex) {
            organizer = new OrganizerImpl();
        }
        catch (IOException | ClassNotFoundException exc) {
            Log.e(TAG,"The file was not found.",exc);
            exc.printStackTrace();
        }
    }

    void save() {
        String filename = "lyl.ly";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream save = new ObjectOutputStream(outputStream);
            save.writeObject(organizer);
            save.close();
            Log.d(TAG, "The save action was successful.");
        } catch (Exception e) {
            Log.e(TAG,"The save action could not be executed.",e);
            e.printStackTrace();
        }
    }

    public void goToTaskOrganizer() {
        setView(R.layout.activity_task_organizer);
        tasksView = new TasksView(this);
        Log.d(TAG,"View changed to TaskOrganizer.");
    }

    public void goToProspectOrganizer() {
        setView(R.layout.activity_prospect_organizer);
        prospectsView = new ProspectsView(this);
        Log.d(TAG,"View changed to ProspectOrganizer.");
    }

    public void goToTagOrganizer() {
        setView(R.layout.activity_tag_organizer);
        tagsView = new TagsView(this);
        Log.d(TAG,"View changed to TagOrganizer.");
    }

    public void goToProspectEdit(Prospect prospect) {
        setView(R.layout.activity_prospect_edit);
        prospectEdit = new ProspectEdit(this, prospect);
        Log.d(TAG,"View changed to ProspectEdit.");
    }

    public void goToTaskEdit(Task task) {
        setView(R.layout.activity_task_edit);
        taskEdit = new TaskEdit(this, task);
        Log.d(TAG,"View changed to TaskEdit.");
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
        Log.d(TAG, "Back button pressed.");
        switch (view) {
            case (R.layout.activity_interval_edit):
                taskEdit.onClickEditIntervalCancel();
                break;
            case (R.layout.activity_task_edit):
                goToTaskOrganizer();
                break;
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
        List<Integer> weightsPast = new ArrayList<>();
        weightsPast.add(5);
        weightsPast.add(5);
        weightsPast.add(3);
        weightsPast.add(3);
        weightsPast.add(3);
        organizer.addProspect(new Prospect("past", tag1,
                LocalDate.now(), LocalDate.now().plusDays(5),
                new Duration(1000 * 60 * 60 * 2), new Duration(1000 * 60 * 60 * 3), weightsPast));
        List<Integer> weightsFuture = new ArrayList<>();
        weightsFuture.add(6);
        weightsFuture.add(6);
        weightsFuture.add(6);
        organizer.addProspect(new Prospect("future", tag2,
                LocalDate.now().minusDays(1), LocalDate.now().plusDays(2),
                new Duration(1000 * 60), new Duration(1000 * 60 * 30 * 5), weightsFuture));

        Task task1 = new Task();
        task1.setDescr("test task");
        task1.setTag(tag1);
        organizer.addTask(task1);

        Task task2 = new Task();
        task2.setDescr("urg task");
        task2.setUrgency(1);
        task2.setTag(tag2);
        organizer.addTask(task2);

        Task task3 = new Task();
        task3.setDescr("done task");
        task3.setTag(tag3);
        task3.setDonity(true);
        organizer.addTask(task3);
    }

    public void onClickNewTag(View view) {
        tagsView.onClickNewTag();
    }

    public void onClickTagEditOk(View view) {
        tagsView.onClickEditOk();
    }

    public void onClickTagEditCancel(View view) {
        tagsView.onClickCancel();
    }

    public void onClickNewProspect(View view) {
        goToProspectEdit(null);
    }

    public void onClickProspectEditOk(View view) {
        prospectEdit.onClickOk();
    }

    public void onClickProspectEditCancel(View view) {
        prospectEdit.onClickCancel();
    }

    public void onClickTaskPlayPause(View view) {
        tasksView.onClickTaskPlayPause(view);
    }

    public void onClickNewTask(View view) {
        goToTaskEdit(null);
    }

    public void onClickTaskEditOk(View view) {
        taskEdit.onClickOk();
    }

    public void onClickTaskEditCancel(View view) {
        taskEdit.onClickCancel();
    }

    public void onClickEditTask(View view) {
        tasksView.onClickEditTask(view);
    }

    public void onClickTaskFinish(View view) {
        tasksView.onClickTaskFinish(view);
    }

    public void onClickToggle(View view) {
        tasksView.onClickToggle(view);
    }

    public void onClickTagEditDelete(View view) {
        tagsView.onClickDelete();
    }

    public void onClickProspectDelete(View view) {
        prospectEdit.onClickDelete();
    }

    public void onClickTaskDelete(View view) {
        taskEdit.onClickDelete();
    }

    public void onClickEditIntervalOk(View view) {
        taskEdit.onClickEditIntervalOk();
    }

    public void onClickEditIntervalCancel(View view) {
        taskEdit.onClickEditIntervalCancel();
    }

    public void onClickEditIntervalDelete(View view) {
        taskEdit.onClickEditIntervalDelete();
    }

    public void onClickEditTaskAddInterval(View view) {
        taskEdit.onClickAddInterval();
    }


    // Called on configuration change (Orientation, keyboard visible)
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // No further action to not switch to main screen
        //setContentView(R.layout.activity_main);
    }
}
