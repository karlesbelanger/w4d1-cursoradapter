package ca.kgb.simplecursoradapter;



import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityTAG_";
    private static final String EXAMPLE_NAME = "Karles";
    private static final int EXAMPLE_AGE = 29;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void fillDatabase(View view) {
        KarlesDatabaseHelper karlesDatabaseHelper = new KarlesDatabaseHelper(getApplicationContext());
        SQLiteDatabase db = karlesDatabaseHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            for (int i = 0; i < 5; i++) {
                ContentValues values = new ContentValues();
                values.put(KarlesDatabaseHelper.KEY_USER_NAME, EXAMPLE_NAME + " " + i);
                values.put(KarlesDatabaseHelper.KEY_AGE, EXAMPLE_AGE + i);

                db.insertOrThrow(KarlesDatabaseHelper.TABLE_USERS, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }
    public void removeAll()
    {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        KarlesDatabaseHelper karlesDatabaseHelper = new KarlesDatabaseHelper(getApplicationContext());
        SQLiteDatabase db = karlesDatabaseHelper.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(KarlesDatabaseHelper.TABLE_USERS, null, null);
    }

    public void clearDB(View view) {
        removeAll();
    }

    public void fillListView(View view) {
        try {
            KarlesDatabaseHelper karlesDatabaseHelper = new KarlesDatabaseHelper(getApplicationContext());

            final String POSTS_SELECT_QUERY = "SELECT * FROM users";

            SQLiteDatabase db = karlesDatabaseHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);

            ListView lvItems = (ListView) findViewById(R.id.a_main_listview);
            CustomAdapter todoAdapter = new CustomAdapter(this, cursor, 0);
            lvItems.setAdapter(todoAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}