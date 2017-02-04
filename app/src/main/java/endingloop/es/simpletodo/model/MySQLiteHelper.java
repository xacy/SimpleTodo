package endingloop.es.simpletodo.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by xacy on 03/02/2017.
 */

public class MySQLiteHelper  extends SQLiteOpenHelper {
    public static final String TABLE_LISTS = "lists";
    public static final String TABLE_TODOS = "todoItems";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DESCRIPTION = "descripcion";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_TODOID = "todoId";
    public static final String COLUMN_STATUS = "status";

    private static final String DATABASE_NAME = "todos.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_LISTS + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_DESCRIPTION
            + " text not null);";

    private static final String DATABASE_CREATE2 = "create table "
            + TABLE_TODOS + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_DESCRIPTION
            + " text not null, "+COLUMN_TODOID
            + " integer, "+COLUMN_STATUS
            + " integer, "
            + " FOREIGN KEY(todoId) references lists (id));";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE2);
        database.execSQL("INSERT INTO lists (descripcion) values('Compras');");
        database.execSQL("INSERT INTO lists (descripcion) values('Todos');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
        onCreate(db);
    }
}
