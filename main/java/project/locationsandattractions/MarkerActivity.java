package project.locationsandattractions;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.SearchView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.google.android.gms.maps.model.Marker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 2017-01-17.
 */

public class MarkerActivity extends ListActivity {
    ArrayAdapter<Marker> markerAdapter;
    List<Marker> dbMarkerList;
    Database database;
    ListView markerlistview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        database = new Database(this);

        dbMarkerList = database.getMarker();
        markerlistview = (ListView) findViewById(android.R.id.list);
        markerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dbMarkerList);
        markerlistview.setAdapter(markerAdapter);
    }
}
