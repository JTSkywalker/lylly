package julian.lylly.view;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import julian.lylly.R;
import julian.lylly.model.Tag;

/**
 * Created by VAIO on 24.10.2015.
 */
public class TagsView {

    private MainActivity main;
    private ArrayAdapter<Tag> tagAdapter;
    private ListView listView;
    private Tag editing;

    // Create a message handling object as an anonymous class.


    public TagsView(MainActivity mainActivity) {
        this.main = mainActivity;

        tagAdapter = new ArrayAdapter<>(mainActivity, R.layout.text_view,
                                        mainActivity.getOrganizer().getTags());
        listView = (ListView) mainActivity.findViewById(R.id.tagListView);
        listView.setAdapter(tagAdapter);
        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                main.setView(R.layout.activity_tag_edit);
                editing = (Tag) parent.getAdapter().getItem(position);
            }
        };
        listView.setOnItemClickListener(mMessageClickedHandler);
    }

    public void onClickNewTag() {
        main.setView(R.layout.activity_tag_edit);
        editing = null;
    }

    public void onClickEditOk() {
        String name = ((EditText) main.findViewById(R.id.tagEditText)).getText().toString();
        if (editing == null) {
            main.getOrganizer().addTag(new Tag(name));
        } else {
            editing.setName(name);
        }
        main.goToTagOrganizer();
    }

    public void onClickCancel() {
        main.goToTagOrganizer();
    }

}
