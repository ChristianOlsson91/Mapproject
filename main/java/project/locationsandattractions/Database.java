package project.locationsandattractions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;
import android.widget.Toast;

import com.google.android.gms.fitness.request.DeleteAllUserDataRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Christian on 2017-01-17.
 */

public class Database extends SQLiteOpenHelper {
    public static final String DATA_POINTS = "DATA_POINTS";
    public static final String COL_ID = "ID";
    public static final String COL_X = "X";
    public static final String COL_Y = "Y";
    public static final String COL_TITLE = "TITLE";
    public static final String COL_TEXT = "TEXT";
    public static final String SQL_CREATE = "CREATE TABLE " + DATA_POINTS + " ( " + COL_ID + " INTEGER PRIMARY KEY NOT NULL, " + COL_X + " REAL," + COL_Y + " REAL," + COL_TITLE + " TEXT," + COL_TEXT + " TEXT );";
    public static final String SQL_DELETE = "DROP TABLE " + DATA_POINTS;

    public Database(Context context) {
        super(context, "markerArray.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String sql = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL", DATA_POINTS, COL_ID, COL_X, COL_Y);

        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersoin, int newVersion) {
        onCreate(db);
    }

    public void storePoints(List<LatLng> points) {
          SQLiteDatabase db = getWritableDatabase();
         db.delete(DATA_POINTS, null, null);

        int i = 0;

        for(LatLng point : points) {
            ContentValues values = new ContentValues();
           // values.put(COL_ID, i);
            values.put(COL_X, point.latitude);
            values.put(COL_Y, point.longitude);
            db.insert(DATA_POINTS, null, values);
            i++;
        }
        db.close();
    }

    public List<LatLng> getPoints() {
        List<LatLng> points = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String  sql = String.format("SELECT * FROM %s", DATA_POINTS);
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            double x = cursor.getDouble(1);
            double y = cursor.getDouble(2);

            points.add(new LatLng(x, y));
        }

        db.close();
        return points;
    }

    public void storeMarkers(List<Marker> markers) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DATA_POINTS, null, null);

        int i = 0;

        for(Marker m : markers) {
            ContentValues values = new ContentValues();
            values.put(COL_ID, i);
            values.put(COL_X, m.getPosition().latitude);
            values.put(COL_Y, m.getPosition().longitude);
            values.put(COL_TITLE, m.getTitle());
            values.put(COL_TEXT, m.getSnippet());
            db.insert(DATA_POINTS, null, values);
            i++;
        }

        db.close();
        System.out.print("StoreMarkers" + getPoints().size());
    }

   public List<Marker> getMarker() {
        List<Marker> markers = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String  sql = String.format("SELECT * FROM %s", DATA_POINTS);
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){

            double lat = cursor.getDouble(1);
            double lon = cursor.getDouble(2);
            String title = cursor.getString(3);
            String text = cursor.getString(4);
            //Marker marker = new Marker(new MarkerOptions().position(new LatLng(lat, lon)).title(title).snippet(text));
            //markers.add(cursor, )
        }

        db.close();
        return markers;
    }
}
