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
                boolean active = task.isActive();
                Duration timer = task.evalDurationSum();
                String descr   = task.getDescr();
                Tag tag        = task.getTag();
                if (convertView == null) {
                    LayoutInflater inflater = main.getLayoutInflater();
                    convertView = inflater.inflate(R.layout.task_list_item, parent, false);
                }
                Button playPauseButton = (Button) convertView.findViewById(R.id.taskPlayPause);
                TextView timerTextView = (TextView) convertView.findViewById(R.id.taskTimer);
                TextView descrTextView = (TextView) convertView.findViewById(R.id.taskDescr);
                TextView tagTextView   = (TextView) convertView.findViewById(R.id.taskTag);

                playPauseButton.setText(active ? "||" : "> ");
                timerTextView  .setText(Util.durationToHourMinuteSecondString(timer));
                descrTextView  .setText(descr);
                tagTextView    .setText(tag.toString());

                return convertView;
            }
        };

        taskListView = (ListView) main.findViewById(R.id.taskListView);
        taskListView.setAdapter(taskAdapter);

        //TODO: doesn't work v
        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                main.goToTaskEdit((Task) parent.getAdapter().getItem(position));
            }
        };
        taskListView.setOnItemClickListener(mMessageClickedHandler);

    }

    public void onClickTaskPlayPause(View view) {
        Button button = (Button) view;
        View parent = (View) button.getParent();
        int pos = taskListView.getPositionForView(parent);
        Task task = ((Task) taskAdapter.getItem(pos));
        task.playPause();
        button.setText(task.isActive() ? "||" : "> ");
        Duration timer = task.evalDurationSum();
        TextView timerView = (TextView) parent.findViewById(R.id.taskTimer);
        timerView.setText(Util.durationToHourMinuteSecondString(timer));//bad style
    }

    public void onClickEditTask(View view) {
        View parent = (View) view.getParent();
        int pos = taskListView.getPositionForView(parent);
        Task task = ((Task) taskAdapter.getItem(pos));
        main.goToTaskEdit(task);
    }
}