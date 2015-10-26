package julian.lylly.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import julian.lylly.R;
import julian.lylly.model.Prospect;

/**
 * Created by VAIO on 24.10.2015.
 */
public class ProspectsView {

    private final MainActivity main;

    private final ListView listView;

    public ProspectsView(MainActivity main) {
        this.main = main;

        ArrayAdapter<Prospect> tagAdapter =
                new ArrayAdapter<Prospect>(main, R.layout.prospect_list_item,
                        main.getOrganizer().getProspects()){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return super.getView(position, convertView, parent);
            }
        };
        listView = (ListView) main.findViewById(R.id.prospectListView);
        listView.setAdapter(tagAdapter);
    }

}
