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
import java.util.Map;

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
    private final ListView budgetListView;
    private final ArrayAdapter budgetAdapter;

    private final ListView taskListView;
    private final ArrayAdapter taskAdapter;

    private Handler handler = new Handler();
    private List<Pair<TextView, Task>> activeVT = new ArrayList<>();

    public TasksView(final MainActivity main) {
        this.main = main;

        //budgets:
        budgetAdapter =
                new ArrayAdapter<Pair<Tag, Pair<Duration, Duration>>>
                        (main, R.layout.budget_list_item,
                                mapToPairList(main.getOrganizer().getTodaysBudgets())) {
//TODO map to list
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        if (convertView == null) {
                            LayoutInflater inflater = main.getLayoutInflater();
                            convertView = inflater.inflate(R.layout.budget_list_item, parent, false);
                        }

                        Pair<Tag, Pair<Duration,Duration>> budget = getItem(position);
                        updateBudget(convertView, budget);

                        return convertView;
                    }

                };
        budgetListView = (ListView) main.findViewById(R.id.budgetListView);
        budgetListView.setAdapter(budgetAdapter);


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
        View taskListItem = (View) view.getParent();
        Task task = resolveTaskFromView(taskListItem);
        task.finish();

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

    private void updateBudget(View view, Pair<Tag, Pair<Duration,Duration>> budget) {
        TextView runningView = (TextView) view.findViewById(R.id.running);
        TextView minMinusView= (TextView) view.findViewById(R.id.minMinusRunning);
        TextView maxMinusView= (TextView) view.findViewById(R.id.maxMinusRunning);
        TextView tagNameView = (TextView) view.findViewById(R.id.tagName);

        Tag tag = budget.getFirst();
        Duration min = budget.getSecond().getFirst();
        Duration max = budget.getSecond().getSecond();
        Duration running = main.getOrganizer().getTodaysInvTime(tag);
        Duration toMin = Util.max(Duration.ZERO, min.minus(running));
        Duration toMax = Util.max(Duration.ZERO, max.minus(running));

        runningView.setText(Util.durationToHourMinuteString(running));
        tagNameView.setText(tag.getName());
        minMinusView.setText(" " + Util.durationToHourMinuteString(toMin) + " ");
        maxMinusView.setText(" " + Util.durationToHourMinuteString(toMax) + " ");

        if (toMin == Duration.ZERO) {
            view.setBackgroundColor(0xFF024000);
        }

        if (toMax == Duration.ZERO) {
            view.setBackgroundColor(0xFF401E00);
        }
    }

    private static <K,E> List<Pair<K,E>> mapToPairList(Map<K,E> map) {
        List result = new ArrayList<>();
        for (K k : map.keySet()) {
            result.add((Pair<K,E>) new Pair(k, map.get(k)));
        }
        return result;
    }
}
