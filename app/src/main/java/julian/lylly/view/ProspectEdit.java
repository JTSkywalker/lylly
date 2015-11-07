package julian.lylly.view;

import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import java.util.List;

import julian.lylly.R;
import julian.lylly.model.Prospect;
import julian.lylly.model.Tag;
import julian.lylly.model.Util;

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
        if (editing != null) {
            tagInput.setSelection(((ArrayAdapter) tagInput.getAdapter()).getPosition(editing.getTag()));

            LocalDate start = editing.getStart();
            startInput.updateDate(start.getYear(), start.getMonthOfYear(), start.getDayOfMonth());

            LocalDate end = editing.getEnd();
            endInput.updateDate(end.getYear(), end.getMonthOfYear(), end.getDayOfMonth());

            Duration min = editing.getMin();
            minHourInput.setValue((int) min.getStandardHours());
            minMinuteInput.setValue((int) min.getStandardMinutes());

            Duration max = editing.getMax();
            maxHourInput.setValue((int) max.getStandardHours());
            maxMinuteInput.setValue((int) max.getStandardMinutes());

            weightsProvisionalInput.setText(Util.intListToString(editing.getWeights()));
        }
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

    public void onClickOk() {
        try {
            String name = "";

            Tag tag = (Tag) tagInput.getSelectedItem();

            LocalDate start = new LocalDate(startInput.getYear(),
                                            startInput.getMonth() + 1,
                                            startInput.getDayOfMonth());

            LocalDate end = new LocalDate(endInput.getYear(),
                                          endInput.getMonth() + 1,
                                          endInput.getDayOfMonth());

            Duration min = new Duration(  minHourInput.getValue()*60*60*1000
                                        + minMinuteInput.getValue()*60*1000);

            Duration max = new Duration(  maxHourInput.getValue()*60*60*1000
                                        + maxMinuteInput.getValue()*60*1000);

            List<Integer> weights = Util.calcWeights(weightsProvisionalInput.getText().toString());

            if (weights.size() != (Period.fieldDifference(start,end)).getDays()) {
                return;
            }
            if (editing == null) {
                 main.getOrganizer().addProspect(new Prospect(name, tag, start, end, min, max, weights));
            } else {
                if (editing.isBeforeStart()) {
                    editing.setName(name);
                    editing.setTag(tag);
                    editing.setStartEnd(start, end, weights);
                    editing.setMinMax(min, max);
                } else {
                    editing.setWeights(weights);
                }
            }
        } catch (IllegalArgumentException exc) {
            return;
        }
        main.goToProspectOrganizer();
    }

    public void onClickCancel() {
        main.goToProspectOrganizer();
    }

}
