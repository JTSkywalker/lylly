package julian.lylly.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import julian.lylly.R;
import julian.lylly.model.Prospect;
import julian.lylly.model.Tag;
import julian.lylly.model.Util;

/**
 * Created by VAIO on 24.10.2015.
 */
public class ProspectsView {

    private final MainActivity main;

    private final ListView listView;

    public ProspectsView(final MainActivity main) {
        this.main = main;

        ArrayAdapter<Prospect> tagAdapter =
                new ArrayAdapter<Prospect>(main, R.layout.prospect_list_item,
                        main.getOrganizer().getProspects()){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = main.getLayoutInflater();
                View view = inflater.inflate(R.layout.prospect_list_item, parent, false);
                Prospect p = getItem(position);
                long running = main.getOrganizer().getInvestedTime(p);
                long toMin = p.getMin() - running;
                long toMax = p.getMax() - running;
                ((TextView) view.findViewById(R.id.running))
                        .setText(Util.millisToHourMinuteString(running));
                ((TextView) view.findViewById(R.id.tagName))
                        .setText(p.getTag().getName());
                ((TextView) view.findViewById(R.id.minMinusRunning))
                        .setText(" " + Util.millisToHourMinuteString(toMin) + " ");
                ((TextView) view.findViewById(R.id.maxMinusRunning))
                        .setText(" " + Util.millisToHourMinuteString(toMax) + " ");
                ((TextView) view.findViewById(R.id.startDate))
                        .setText(Util.daysToDate(p.getStart()));
                ((TextView) view.findViewById(R.id.endDate))
                        .setText(Util.daysToDate(p.getEnd()));

                return view;
            }
        };
        listView = (ListView) main.findViewById(R.id.prospectListView);
        listView.setAdapter(tagAdapter);
        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                main.setView(R.layout.activity_prospect_edit);
                main.goToProspectEdit((Prospect) parent.getAdapter().getItem(position));
            }
        };
        listView.setOnItemClickListener(mMessageClickedHandler);
    }

}
