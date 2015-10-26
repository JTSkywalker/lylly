package julian.lylly.view;

import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import julian.lylly.R;
import julian.lylly.model.Prospect;
import julian.lylly.model.Tag;

/**
 * Created by VAIO on 26.10.2015.
 */
public class ProspectEdit {

    private final MainActivity main;
    private final Prospect editing;

    private final Spinner tagInput;
    private final DatePicker startInput;
    private final DatePicker endInput;
    private final NumberPicker minHourInput, minMinuteInput;
    private final NumberPicker maxHourInput, maxMinuteInput;

    private final EditText weightsProvisionalInput;

    public ProspectEdit(MainActivity main, Prospect editing) {
        this.main = main;
        this.editing = editing;

        tagInput = (Spinner) main.findViewById(R.id.prospectTagInput);
        startInput = (DatePicker) main.findViewById(R.id.prospectStartInput);
        endInput = (DatePicker) main.findViewById(R.id.prospectEndInput);
        minHourInput = (NumberPicker) main.findViewById(R.id.prospectMinHourInput);
        minMinuteInput = (NumberPicker) main.findViewById(R.id.prospectMinMinuteInput);
        maxHourInput = (NumberPicker) main.findViewById(R.id.prospectMaxHourInput);
        maxMinuteInput = (NumberPicker) main.findViewById(R.id.prospectMaxMinuteInput);

        weightsProvisionalInput = (EditText) main.findViewById(R.id.prospectWeightsProvisionalInput);
        initTagInput();
        initStartEndInput();
        initMinMaxInput();
        initWeightsInput();
    }

    private void initTagInput() {
        ArrayAdapter<Tag> tagAdap = new ArrayAdapter<>(main, android.R.layout.simple_spinner_item,
                main.getOrganizer().getTags());
        tagAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagInput.setAdapter(tagAdap);
    }

    private void initStartEndInput() {
        //nothing to do here?
    }

    private void initMinMaxInput() {
        minHourInput.setMaxValue(99);
        maxHourInput.setMaxValue(99);
        minMinuteInput.setMaxValue(59);
        maxMinuteInput.setMaxValue(59);
        //TODO: add scroll through
    }

    private void initWeightsInput() {
        //nothing to do here?
    }

}
