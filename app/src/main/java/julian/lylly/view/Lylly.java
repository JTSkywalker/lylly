package julian.lylly.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = R.layout.activity_main;
        setContentView(view);
    }

    protected void onResume() {
        super.onResume();
        load();
        //helper();
    }

    private void load() {
        try{
            FileInputStream fis = openFileInput("lyl.ly");
            ObjectInputStream ois = new ObjectInputStream(fis);
            organizer = (Organizer) ois.readObject();
            ois.close();
        } catch (FileNotFoundException ex) {
            organizer = new OrganizerImpl();
        }
        catch (IOException | ClassNotFoundException exc) {
            exc.printStackTrace();
        }
    }

    protected void onPause() {
        save();
        super.onPause();
    }

    void save() {
        String filename = "lyl.ly";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream save = new ObjectOutputStream(outputStream);
            save.writeObject(organizer);
            save.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToTaskOrganizer() {
        setView(R.layout.activity_task_organizer);
        tasksView = new TasksView(this);
    }

    public void goToProspectOrganizer() {
        setView(R.layout.activity_prospect_organizer);
        prospectsView = new ProspectsView(this);
    }

    public void goToTagOrganizer() {
        setView(R.layout.activity_tag_organizer);
        tagsView = new TagsView(this);
    }

    public void goToProspectEdit(Prospect prospect) {
        setView(R.layout.activity_prospect_edit);
        prospectEdit = new ProspectEdit(this, prospect);
    }

    public void goToTaskEdit(Task task) {
        setView(R.layout.activity_task_edit);
        taskEdit = new TaskEdit(this, task);
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
}
