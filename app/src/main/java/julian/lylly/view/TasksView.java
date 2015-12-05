package julian.lylly.view;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import julian.lylly.R;
import julian.lylly.model.Prospect;
import julian.lylly.model.Tag;
import julian.lylly.model.Task;
import julian.lylly.model.Util;

/**
 * Created by VAIO on 28.11.2015.
 */
public class TasksView {

    private final MainActivity main;

    //TODO: implement budgetListView
    //TODO: implement timer sync
    //private final ListView budgetListView;
    //private final ArrayAdapter budgetAdapter;

    private final ListView taskListView;
    private final ArrayAdapter taskAdapter;
    private Button playPauseButton;


    public TasksView(final MainActivity main) {
        this.main = main;
        taskAdapter =
                new ArrayAdapter<Task>(main, R.layout.task_list_item,
                    main.getOrganizer().getFilteredTasks(new ArrayList<Tag>())) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Task task = getItem(position);
                String descr   = task.getDescr();
                Tag tag        = task.getTag();
                if (convertView == null) {
                    LayoutInflater inflater = main.getLayoutInflater();
                    convertView = inflater.inflate(R.layout.task_list_item, parent, false);
                }
                TextView descrTextView = (TextView) convertView.findViewById(R.id.taskDescr);
                TextView tagTextView   = (TextView) convertView.findViewById(R.id.taskTag);

                updateTimer(convertView, task);
                descrTextView  .setText(descr);
                tagTextView    .setText(tag.toString());

                return convertView;
            }
        };
        taskListView = (ListView) main.findViewById(R.id.taskListView);
        taskListView.setAdapter(taskAdapter);

        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                main.goToTaskEdit((Task) parent.getAdapter().getItem(position));
            }
        };
        taskListView.setOnItemClickListener(mMessageClickedHandler);

    }

    public void onClickTaskPlayPause(View view) {
        Button button = (Button) view;
        View taskListItem = (View) button.getParent();
        Task task = resolveTaskFromView(taskListItem);
        task.playPause();
        updateTimer(taskListItem);
    }

    public void onClickEditTask(View view) {
        View parent = (View) view.getParent();
        int pos = taskListView.getPositionForView(parent);
        Task task = ((Task) taskAdapter.getItem(pos));
        main.goToTaskEdit(task);
    }

    public void onClickTaskFinish(View view) {
        Task task = resolveTaskFromView((View) view.getParent());
        task.finish();
        updateTimer(taskListView);
    }

    private Task resolveTaskFromView(View view) {
        int pos = taskListView.getPositionForView(view);
        return (Task) taskAdapter.getItem(pos);
    }

    private void updateTimer(View taskListItem) {
        updateTimer(taskListItem, resolveTaskFromView(taskListItem));
    }

    private void updateTimer(View taskListItem, Task task) {
        Button button = (Button) taskListItem.findViewById(R.id.taskPlayPause);
        button.setText(task.isActive() ? "||" : "> ");
        Duration timer = task.evalDurationSum();
        TextView timerView = (TextView) taskListItem.findViewById(R.id.taskTimer);
        timerView.setText(Util.durationToHourMinuteSecondString(timer));
    }
}
