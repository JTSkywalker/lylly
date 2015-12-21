package julian.lylly.view;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;

import julian.lylly.R;
import julian.lylly.model.Tag;
import julian.lylly.model.Task;
import julian.lylly.model.Util;

/**
 * Created by VAIO on 01.12.2015.
 */
public class TaskEdit {

    private final Lylly main;
    private Task editing;
    private IntervalEdit intervalEdit;
    private Interval editingInterval;

    private final EditText nameInput;
    private final Spinner tagInput;
    private final CheckBox isUrgentInput;
    private final CheckBox isDoneInput;
    private final ListView intervalList;

    public TaskEdit(Lylly main, Task editing) {
        this.main = main;
        this.editing = editing;

        nameInput     = (EditText) main.findViewById(R.id.taskEditNameInput);
        tagInput      =  (Spinner) main.findViewById(R.id.taskEditTagInput);
        isUrgentInput = (CheckBox) main.findViewById(R.id.taskEditIsUrgentInput);
        isDoneInput   = (CheckBox) main.findViewById(R.id.taskEditIsDone);
        intervalList  = (ListView) main.findViewById(R.id.taskEditIntervals);

        initTagInput();
        initIntervalList();

        if (editing != null) {
            nameInput.setText(editing.getDescr());
            tagInput.setSelection(((ArrayAdapter) tagInput.getAdapter()).getPosition(editing.getTag()));
            isUrgentInput.setChecked(editing.getUrgency() > 0);//TODO: no isUrgent!!
            isDoneInput.setChecked(editing.isDone());
        }
    }

    private void initTagInput() {
        ArrayAdapter<Tag> tagAdap = new ArrayAdapter<>(main,
                                                       android.R.layout.simple_spinner_item,
                                                       main.getOrganizer().getTags());
        tagAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagInput.setAdapter(tagAdap);
    }

    private void initIntervalList() {
        List<Interval> intervals = new ArrayList<>();
        if (editing != null) {
            intervals = editing.getIntervals();
        }
        ArrayAdapter<Interval> intervalAdapter = new ArrayAdapter<Interval>(main,
                                                                            R.layout.text_view,
                                                                            intervals) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = main.getLayoutInflater();
                    convertView = inflater.inflate(R.layout.text_view, parent, false);
                }
                Interval interval = getItem(position);                                    //W
                String startTxt = Util.dateTimeToString(interval.getStart().plusHours(1)),//E
                         endTxt = Util.dateTimeToString(  interval.getEnd().plusHours(1));//A
                ((TextView) convertView).setText(startTxt + " / " + endTxt);              //K
                return convertView;
            }
        };
        intervalList.setAdapter(intervalAdapter);
        AdapterView.OnItemClickListener listener
                = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                saveChanges();//DANGER?
                Interval editInterval = (Interval) parent.getAdapter().getItem(position);
                editingInterval = editInterval;
                main.setView(R.layout.activity_interval_edit);
                intervalEdit = new IntervalEdit(main, editInterval);
            }
        };
        intervalList.setOnItemClickListener(listener);
    }

    public void onClickOk() {
        saveChanges();
        main.goToTaskOrganizer();
    }

    private void saveChanges() {
        String descr = nameInput.getText().toString();
        Tag      tag = (Tag) tagInput.getSelectedItem();
        int  urgency = isUrgentInput.isChecked() ? 1 : 0;
        boolean done = isDoneInput.isChecked();
        //TODO: list of intervals…

        Task task;
        if (editing == null) {
            task = new Task();
        } else {
            task = editing;
        }

        task.setDescr(descr);
        task.setTag(tag);
        task.setUrgency(urgency);
        task.setDonity(done);

        if (editing == null) {
            main.getOrganizer().addTask(task);
        }
        editing = task;
    }

    public void onClickCancel() {
        main.goToTaskOrganizer();
    }

    public void onClickDelete() {
        if(editing != null) {
            main.getOrganizer().removeTask(editing);
        }
        main.goToTaskOrganizer();
    }

    public void onClickEditIntervalOk() {
        List<Interval> intervals = editing.getIntervals();
        intervals.remove(editingInterval);
        intervals.add(intervalEdit.getInterval());
        main.goToTaskEdit(editing);
    }

    public void onClickEditIntervalCancel() {
        main.goToTaskEdit(editing);
    }

    public void onClickEditIntervalDelete() {
        List<Interval> intervals = editing.getIntervals();
        intervals.remove(editingInterval);
        editing.setIntervals(intervals);
        main.goToTaskEdit(editing);
    }

    public void onClickAddInterval() {
        saveChanges();//DANGER?
        editingInterval = null;
        main.setView(R.layout.activity_interval_edit);
        intervalEdit = new IntervalEdit(main, null);
    }
}
