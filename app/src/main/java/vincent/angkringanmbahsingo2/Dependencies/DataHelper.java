package vincent.angkringanmbahsingo2.Dependencies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {

    public static final String database_name = "db_login";
    public static final String table_name = "table_login";

    public static final String row_username = "Username";
    public static final String row_password = "Password";
    public static final String row_nama = "nama";
    public static final String row_alamat = "alamat";
    public static final String row_nohp = "nohp";

    private SQLiteDatabase db;

    public DataHelper(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    // Membuat tabel baru
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+table_name+" ("+row_username+" TEXT PRIMARY KEY, "+row_password+" TEXT, "+row_nama+" TEXT, "+row_alamat+" TEXT, "+row_nohp+" TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
    }

    // Memasukkan Data
    public void insertData(ContentValues values){
        db.insert(table_name, null, values);
    }


    // Membuat fungsi checkUser untuk dapat Login pada LoginFragment
    public boolean checkUser(String username, String password){
        String[] columns = {row_username};
        SQLiteDatabase db = getReadableDatabase();
        String selection = row_username + "=?" + " and " + row_password + "=?";
        String[] selectionArgs = {username,password};
        Cursor cursor = db.query(table_name, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count>0)
            return true;
        else
            return false;
    }
}
