package endingloop.es.simpletodo.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xacy on 03/02/2017.
 */

public class TodoDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_DESCRIPTION };

    public TodoDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Listas createList(String texto) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION, texto);
        long insertId = database.insert(MySQLiteHelper.TABLE_LISTS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_LISTS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Listas newList = cursorToList(cursor);
        cursor.close();
        return newList;
    }

    public void deleteList(Listas lista) {
        long id = lista.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_LISTS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Listas> getAllLists() {
        List<Listas> listas = new ArrayList<Listas>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_LISTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Listas lista = cursorToList(cursor);
            listas.add(lista);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listas;
    }

    private Listas cursorToList(Cursor cursor) {
        Listas lista = new Listas();
        lista.setId(cursor.getLong(0));
        lista.setDescripcion(cursor.getString(1));
        return lista;
    }
}
