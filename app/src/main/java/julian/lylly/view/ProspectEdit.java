package julian.lylly.view;

import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.GregorianCalendar;
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
            int start = editing.getStart();
            startInput.updateDate(Util.getYearFromDays(start),
                                  Util.getMonthFromDays(start),
                                  Util.getDayFromDays(start));
            int end = editing.getEnd();
            endInput.updateDate(Util.getYearFromDays(end),
                    Util.getMonthFromDays(end),
                    Util.getDayFromDays(end));
            long min = editing.getMin();
            minHourInput.setValue(Util.getHourFromMillis(min));
            minMinuteInput.setValue(Util.getMinuteFromMillis(min));
            long max = editing.getMax();
            maxHourInput.setValue(Util.getHourFromMillis(max));
            maxMinuteInput.setValue(Util.getMinuteFromMillis(max));
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
            int start = Util.millisToDay(
                    (new GregorianCalendar(startInput.getYear(),
                            startInput.getMonth(),
                            startInput.getDayOfMonth())
                            .getTimeInMillis()));
            int end = Util.millisToDay(
                    (new GregorianCalendar(endInput.getYear(),
                            endInput.getMonth(),
                            endInput.getDayOfMonth())
                            .getTimeInMillis()));
            long min = minHourInput.getValue()*60*60*1000 + minMinuteInput.getValue()*60*1000;
            long max = maxHourInput.getValue()*60*60*1000 + maxMinuteInput.getValue()*60*1000;
            List<Integer> weights = Util.calcWeights(weightsProvisionalInput.getText().toString());

            if (weights.size() != end - start) {
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
