package julian.lylly.view;

import android.graphics.Typeface;
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
import java.util.List;

import julian.lylly.R;
import julian.lylly.model.Pair;
import julian.lylly.model.Tag;
import julian.lylly.model.Task;
import julian.lylly.model.Util;

/**
 * Created by VAIO on 28.11.2015.
 */
public class TasksView {

    private final MainActivity main;

    //TODO: implement timer sync
    /*private final ListView budgetListView;
    private final ArrayAdapter budgetAdapter;*/

    private final ListView taskListView;
    private final ArrayAdapter taskAdapter;

    private Handler handler = new Handler();
    private List<Pair<TextView, Task>> activeVT = new ArrayList<>();

    /*private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            TextView timerView;
            Duration timer;
            Task task;
            for(Pair<TextView, Task> vt : activeVT) {
                timerView = vt.getFirst();
                task = vt.getSecond();
                if (task.isActive()) {
                    timer = task.evalDurationSum();
                    timerView.setText(Util.durationToHourMinuteSecondString(timer));
                }

                handler.postDelayed(this, 1000);
            }
        }
    };*/

    public TasksView(final MainActivity main) {
        this.main = main;

        //budgets:
        /*budgetAdapter =
                new ArrayAdapter<Map<Tag, Pair<Duration, Duration>>>
                        (main, R.layout.budget_list_item,
                                (List<Map<Tag, Pair<Duration, Duration>>>) main.getOrganizer().getTodaysBudgets()) {
//TODO map to list
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        if (convertView == null) {
                            LayoutInflater inflater = main.getLayoutInflater();
                            convertView = inflater.inflate(R.layout.budget_list_item, parent, false);
                        }
                        //fixme
                        return convertView;
                    }

                };
        budgetListView = (ListView) main.findViewById(R.id.budgetListView);
        budgetListView.setAdapter(budgetAdapter);*/


        //tasks:
        taskAdapter =
                new ArrayAdapter<Task>(main, R.layout.task_list_item,
                    main.getOrganizer().getFilteredTasks(new ArrayList<Tag>())) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Task task = getItem(position);
                if (convertView == null) {
                    LayoutInflater inflater = main.getLayoutInflater();
                    convertView = inflater.inflate(R.layout.task_list_item, parent, false);
                }

                updateTask(convertView, task);

                return convertView;
            }
        };
        taskListView = (ListView) main.findViewById(R.id.taskListView);
        taskListView.setAdapter(taskAdapter);

        AdapterView.OnItemClickListener onTaskClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                main.goToTaskEdit((Task) parent.getAdapter().getItem(position));
            }
        };
        taskListView.setOnItemClickListener(onTaskClickListener);

        //handler.postDelayed(runnable, 1000);
    }

    public void onClickTaskPlayPause(View view) {
        Button button = (Button) view;
        View taskListItem = (View) button.getParent();
        Task task = resolveTaskFromView(taskListItem);
        task.playPause();
        //updateActiveVT((TextView) taskListItem.findViewById(R.id.taskTimer), task);
        updateTimer(taskListItem);
    }

    /*private void updateActiveVT(TextView timerView, Task task) {
        Pair<TextView, Task> vt = new Pair<>(timerView, task);
        if (task.isActive()) {
            activeVT.add(vt);
        } else {
            activeVT.remove(vt);
        }
    }*/

    public void onClickEditTask(View view) {
        View parent = (View) view.getParent();
        int pos = taskListView.getPositionForView(parent);
        Task task = ((Task) taskAdapter.getItem(pos));
        main.goToTaskEdit(task);
    }

    public void onClickTaskFinish(View view) {
        View taskListItem = (View) view.getParent();
        Task task = resolveTaskFromView(taskListItem);
        task.finish();

        //updateActiveVT((TextView) taskListItem.findViewById(R.id.taskTimer), task);
        updateTask(taskListItem, task);
    }

    private Task resolveTaskFromView(View view) {
        int pos = taskListView.getPositionForView(view);
        return (Task) taskAdapter.getItem(pos);
    }

    private void updateTimer(View taskListItem) {
        updateTimer(taskListItem, resolveTaskFromView(taskListItem));
    }

    private static void updateTimer(View taskListItem, Task task) {
        Button button = (Button) taskListItem.findViewById(R.id.taskPlayPause);
        button.setText(task.isActive() ? "||" : "> ");
        Duration timer = task.evalDurationSum();
        TextView timerView = (TextView) taskListItem.findViewById(R.id.taskTimer);
        timerView.setText(Util.durationToHourMinuteSecondString(timer));
    }

    private void updateTask(View taskListItem) {
        updateTask(taskListItem, resolveTaskFromView(taskListItem));
    }

    private static void updateTask(View taskListItem, Task task) {
        updateTimer(taskListItem, task);

        boolean done   = task.isDone();
        String descr   = task.getDescr();
        Tag tag        = task.getTag();
        int urgency    = task.getUrgency();

        Button playPauseButton = (Button)   taskListItem.findViewById(R.id.taskPlayPause);
        Button finishButton    = (Button)   taskListItem.findViewById(R.id.taskFinish);
        TextView descrTextView = (TextView) taskListItem.findViewById(R.id.taskDescr);
        TextView tagTextView   = (TextView) taskListItem.findViewById(R.id.taskTag);

        if (descr != null)
            descrTextView  .setText(descr);

        if (tag != null)
            tagTextView    .setText(tag.toString());

        if(done) {
            taskListItem.setBackgroundColor(0xFF78CC78);
            playPauseButton.setEnabled(false);
            finishButton.setEnabled(false);
        } else {
            taskListItem.setBackgroundColor(0xFFFFFFFF);
            playPauseButton.setEnabled(true);
            finishButton.setEnabled(true);
        }

        if(urgency > 0) {
            descrTextView.setTypeface(null, Typeface.BOLD);
        } else {
            descrTextView.setTypeface(null, Typeface.NORMAL);
        }
    }
}

/*class VT {

    private final TextView timerView;
    private final Task task;

    public VT (TextView timerView, Task task) {
        this.timerView = timerView;
        this.task = task;
    }

    void updateTimer() {

    }

}*/
