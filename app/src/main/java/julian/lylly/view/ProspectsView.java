package julian.lylly.view;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.Duration;

import julian.lylly.R;
import julian.lylly.model.Prospect;
import julian.lylly.model.Util;

/**
 * Created by VAIO on 24.10.2015.
 */
public class ProspectsView {

    private final Lylly main;

    private final ListView listView;

    public ProspectsView(final Lylly main) {
        this.main = main;

        ArrayAdapter<Prospect> tagAdapter =
                new ArrayAdapter<Prospect>(main, R.layout.prospect_list_item,
                        main.getOrganizer().getProspects()){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null) {
                    LayoutInflater inflater = main.getLayoutInflater();
                    convertView = inflater.inflate(R.layout.prospect_list_item, parent, false);
                }
                Prospect p = getItem(position);
                Duration running = main.getOrganizer().getInvestedTime(p);
                Duration toMin = Util.max(Duration.ZERO, p.getMin().minus(running));
                Duration toMax = Util.max(Duration.ZERO, p.getMax().minus(running));

                TextView tagView = (TextView) convertView.findViewById(R.id.tagName);
                TextView runningView = (TextView) convertView.findViewById(R.id.running);
                TextView minMinusView = (TextView) convertView.findViewById(R.id.minMinusRunning);
                TextView maxMinusView = (TextView) convertView.findViewById(R.id.maxMinusRunning);
                TextView startDateView = (TextView) convertView.findViewById(R.id.startDate);
                TextView endDateView = (TextView) convertView.findViewById(R.id.endDate);

                tagView.setText(p.getTag().getName());
                if(main.getOrganizer().getDiscardedProspects().contains(p)) {
                    tagView.setPaintFlags(tagView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                runningView.setText(Util.durationToHourMinuteString(running));
                minMinusView.setText(" " + Util.durationToHourMinuteString(toMin) + " ");
                maxMinusView.setText(" " + Util.durationToHourMinuteString(toMax) + " ");
                startDateView.setText(Util.daysToDate(p.getStart()));
                endDateView.setText(Util.daysToDate(p.getEnd()));

                return convertView;
            }
        };
        listView = (ListView) main.findViewById(R.id.prospectListView);
        listView.setAdapter(tagAdapter);
        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                main.goToProspectEdit((Prospect) parent.getAdapter().getItem(position));
            }
        };
        listView.setOnItemClickListener(mMessageClickedHandler);

        Thread updater = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    main.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        updater.start();
    }

}
