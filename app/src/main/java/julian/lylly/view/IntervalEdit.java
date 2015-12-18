package julian.lylly.view;

import android.widget.DatePicker;
import android.widget.TimePicker;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.Interval;

import julian.lylly.R;

/**
 * Created by VAIO on 18.12.2015.
 */
public class IntervalEdit {

    private final Lylly main;

    private final Interval editing;

    private final DatePicker startDateInput;
    private final TimePicker startTimeInput;
    private final DatePicker endDateInput;
    private final TimePicker endTimeInput;

    public IntervalEdit(Lylly main, Interval editing) {
        this.main = main;
        this.editing = editing;

        this.startDateInput = (DatePicker) main.findViewById(R.id.editIntervalStartDate);
        this.startTimeInput = (TimePicker) main.findViewById(R.id.editIntervalStartTime);
        this.endDateInput   = (DatePicker) main.findViewById(R.id.editIntervalEndDate);
        this.endTimeInput   = (TimePicker) main.findViewById(R.id.editIntervalEndTime);

        if (editing != null) {
            DateTime start = editing.getStart();
            startDateInput.updateDate(start.getYear(),
                    // -1 because DatePicker is weird and counts months from 0 to 11
                    start.getMonthOfYear() - 1,
                    start.getDayOfMonth());
            startTimeInput.setCurrentHour(start.getHourOfDay());
            startTimeInput.setCurrentMinute( start.getMinuteOfHour());

            DateTime end   = editing.getEnd();
            endDateInput.updateDate(end.getYear(),
                    // -1 because DatePicker is weird and counts months from 0 to 11
                    end.getMonthOfYear() - 1,
                    end.getDayOfMonth());
            endTimeInput.setCurrentHour(end.getHourOfDay());
            endTimeInput.setCurrentMinute( end.getMinuteOfHour());
        }
    }

    public Interval getInterval() {
        DateTime start = new DateTime(startDateInput.getYear(),
                // +1 because DatePicker is weird and counts months from 0 to 11
                                      startDateInput.getMonth()+1,
                                      startDateInput.getDayOfMonth(),
                startTimeInput.getCurrentHour(),
                startTimeInput.getCurrentMinute());
        DateTime end   = new DateTime(endDateInput.getYear(),
                // +1 because DatePicker is weird and counts months from 0 to 11
                                      endDateInput.getMonth()+1,
                                      endDateInput.getDayOfMonth(),
                endTimeInput.getCurrentHour(),
                endTimeInput.getCurrentMinute());
        //FIXME: this fucks up other timezones…
        return new Interval(start.plusHours(1).toInstant(), end.plusHours(1).toInstant());
    }

}
