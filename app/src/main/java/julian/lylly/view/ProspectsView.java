package julian.lylly.view;

import julian.lylly.R;
import julian.lylly.model.Prospect;

/**
 * Created by VAIO on 24.10.2015.
 */
public class ProspectsView {

    private MainActivity main;
    private Prospect editing;

    public ProspectsView(MainActivity main) {
        this.main = main;
    }

    public void onClickNewProspect() {
        main.setView(R.layout.activity_prospect_edit);
        editing = null;
    }

}
