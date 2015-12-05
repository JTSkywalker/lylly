package julian.lylly.view;

import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import julian.lylly.R;
import julian.lylly.model.Tag;
import julian.lylly.model.Task;

/**
 * Created by VAIO on 01.12.2015.
 */
public class TaskEdit {

    private final Lylly main;
    private Task editing;

    private final EditText nameInput;
    private final Spinner tagInput;
    private final CheckBox isUrgentInput;
    private final CheckBox isDoneInput;

    public TaskEdit(Lylly main, Task editing) {
        this.main = main;
        this.editing = editing;

        nameInput = (EditText) main.findViewById(R.id.taskEditNameInput);
        tagInput = (Spinner) main.findViewById(R.id.taskEditTagInput);
        isUrgentInput = (CheckBox) main.findViewById(R.id.taskEditIsUrgentInput);
        isDoneInput = (CheckBox) main.findViewById(R.id.taskEditIsDone);

        initTagInput();

        if (editing != null) {
            nameInput.setText(editing.getDescr());
            tagInput.setSelection(((ArrayAdapter) tagInput.getAdapter()).getPosition(editing.getTag()));
            isUrgentInput.setChecked(editing.getUrgency() > 0);//TODO: no isUrgent!!
            isDoneInput.setChecked(editing.isDone());
        }
    }

    private void initTagInput() {
        ArrayAdapter<Tag> tagAdap = new ArrayAdapter<>(main, android.R.layout.simple_spinner_item,
                main.getOrganizer().getTags());
        tagAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagInput.setAdapter(tagAdap);
    }

    public void onClickOk() {
        String descr = nameInput.getText().toString();
        Tag      tag = (Tag) tagInput.getSelectedItem();
        int  urgency = isUrgentInput.isChecked() ? 1 : 0;
        boolean done = isDoneInput.isChecked();

        Task task;
        if (editing == null) {
            task = new Task();
        } else {
            task = editing;
        }

        task.setDescr(descr);
        task.setTag(tag);
        task.setUrgency(urgency);
        task.setDone(done);

        if (editing == null) {
            main.getOrganizer().addTask(task);
        }
        main.goToTaskOrganizer();
    }

    public void onClickCancel() {
        main.goToTaskOrganizer();
    }
}
