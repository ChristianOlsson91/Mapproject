package project.locationsandattractions;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.MotionEvent;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity
       implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, DialogInterface, Serializable {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    List<Marker> markers;
    ArrayList<String> arrayList;
    Marker marker1;
    Marker marker2;
    List<LatLng> points = new ArrayList<>();
    private List<Marker> dbMarkers;
    private Database db;

    public void dismiss() {
    }

    public void cancel() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        db = new Database(this);
       // populate(mMap);
        Toast.makeText(this, " " + db.getPoints().size(), Toast.LENGTH_LONG).show();
    }

    public List<Marker> populate(GoogleMap googleMap) {
        mMap = googleMap;
        markers = new ArrayList<Marker>();
        LatLng location1 = new LatLng(60.674881, 17.141954);
        LatLng location2 = new LatLng(60.673462, 17.142620);
        marker1 = mMap.addMarker(new MarkerOptions().position(location1)
                .title("Brända Bocken")
                .snippet("Restaurangen Brända Bocken på Stortorget"));

        marker2 = mMap.addMarker(new MarkerOptions().position(location2)
                .title("Church Street Saloon")
                .snippet("Restaurangen Church Street Saloon"));
        markers.add(marker1);
        markers.add(marker2);
        db.storeMarkers(markers);
        dbMarkers = db.getMarker();
        for (int i = 0; i<dbMarkers.size(); i++) {
            LatLng latlng = new LatLng(dbMarkers.get(i).getPosition().latitude, dbMarkers.get(i).getPosition().longitude);
            mMap.addMarker(new MarkerOptions().position(latlng).title(dbMarkers.get(i).getTitle()).snippet(dbMarkers.get(i).getSnippet()));
        }
        return markers;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        //populate(mMap);
        dbMarkers = getMarkers();
        System.out.println(db.getPoints().size());

       /* for(int i = 0; i<db.getPoints().size(); i++) {
            mMap.addMarker(new MarkerOptions().position(db.getPoints().get(i)).title(db.getColTitle()).snippet(db.getColText()));
        }*/

        // Set the latitude and longitude for Gävle and move the camera and zoom in
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(60.674880, 17.141273), 14.0f));

        // Set the latitude and longitude for a place and add a marker
   /*    LatLng location1 = new LatLng(60.674881, 17.141954);
        LatLng location2 = new LatLng(60.673462, 17.142620);
        marker1 = mMap.addMarker(new MarkerOptions().position(location1)
                .title("Brända Bocken")
                .snippet("Restaurangen Brända Bocken på Stortorget"));

        Marker marker2 = mMap.addMarker(new MarkerOptions().position(location2)
                .title("Church Street Saloon")
                .snippet("Restaurangen Church Street Saloon"));

        markers = new ArrayList<Marker>();
        markers.add(marker1);
        markers.add(marker2);
*
        //db.storeMarkers(markers);

        points.add(marker1.getPosition());
        points.add(marker2.getPosition());
*/
  //     db.storePoints(points);

        LatLng location1 = new LatLng(60.674881, 17.141954);

    //    marker1 = mMap.addMarker(new MarkerOptions().position(location1)
      //          .title("Brända Bocken")
        //        .snippet("Restaurangen Brända Bocken på Stortorget"));

        for( Marker marker : dbMarkers) {
            LatLng latLngTemp = marker.getPosition();

            mMap.addMarker(new MarkerOptions().position(latLngTemp));
        }
        mMap.setIndoorEnabled(false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    public void onMapClick(LatLng point) {
                        AlertDialog.Builder dialogbox = new AlertDialog.Builder(MainActivity.this);
                        LinearLayout layout = new LinearLayout(MainActivity.this);
                        layout.setOrientation(LinearLayout.VERTICAL);
                        final EditText titlebox = new EditText(MainActivity.this);
                        final EditText textbox = new EditText(MainActivity.this);
                        titlebox.setHint("Titel");
                        layout.addView(titlebox);
                        textbox.setHint("Text");
                        layout.addView(textbox);
                        dialogbox.setView(layout);

                       final Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(point.latitude, point.longitude)));

                        dialogbox.setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        });

                        dialogbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                marker.setTitle(titlebox.getText().toString());
                                marker.setSnippet(textbox.getText().toString());
                            }
                        });
                       // points.add(marker.getPosition());

                        dialogbox.show();
                        dbMarkers.add(marker);
                        //db.storePoints(points);
                        System.out.println(db.getPoints().size());
                      db.storeMarkers(dbMarkers);
                    }
                });
            }
        });
    }

    public void saveMarkers(List<Marker> markerdb) {
        List<Marker> markerlist = markerdb;

        //db.storeMarkers(markerlist);
    }

    public List<Marker> getMarkers() {
        List<Marker> markers = new ArrayList<>();

        List<LatLng> points = db.getPoints();
        for( LatLng ll : points)
        {
            Marker temp = mMap.addMarker(new MarkerOptions().position(ll))
              //      .title("Brända Bocken")
                //    .snippet("Restaurangen Brända Bocken på Stortorget"))
                    ;
            markers.add(temp);
        }

        return markers;
    }
    public void storeMarkers(List<Marker> markers) {
       db.storeMarkers(markers);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, " " + db.getPoints().size(), Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        db.close();
    }

    @Override
    protected void onStop() {
        super.onStop();
        MapStateManager mgr = new MapStateManager(this);
      //  mgr.saveMapState(mMap);

    }

    @Override
    protected void onResume() {
        super.onResume();
       /* MapStateManager mgr = new MapStateManager(this);
        CameraPosition position = mgr.getSavedCameraPosition();
        if(position != null) {
            CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
            mMap.moveCamera(update);
        }*/

       // db.close();
    }

    @Override
        protected void onRestart() {
            super.onRestart();

            System.out.println(db.getPoints().size());
        }

    @Override
    protected void onStart() {
        super.onStart();
        for(int i = 0; i<db.getPoints().size(); i++) {
            double latitude = db.getPoints().get(i).latitude;
            double longitude = db.getPoints().get(i).longitude;
            //mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
        }
    }

    @Override
        public void onBackPressed () {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            switch (id) {
                case R.id.normal:
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    break;
                case R.id.satellite:
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    break;
                case R.id.terrain:
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    break;
                case R.id.hybrid:
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    break;
                case R.id.none:
                    mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                    break;
            }

            return super.onOptionsItemSelected(item);
        }

        @Override
        public boolean onNavigationItemSelected (MenuItem item){
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.mapview) {

            } else if (id == R.id.markerlist) {
                Intent intent = new Intent(this, MarkerActivity.class);
                startActivity(intent);
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
}