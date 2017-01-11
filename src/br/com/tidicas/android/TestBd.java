package br.com.tidicas.android;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Classe para manipulação de dados no banco de dados 
 * SQLite no Android
 * 
 * @author Evaldo Junior
 *
 */
public class TestBd extends Activity {
  private static String[] FROM = { "ID", "HORA", "TITULO", };
  private static String ORDER_BY = "HORA DESC";

  private EventosData eventos;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    eventos = new EventosData(this);
    try {
      addEvent("Olá, Android!");
      Cursor cursor = getEvents();
      showEvents(cursor);
    } finally {
      eventos.close();
    }
  }

  private void addEvent(String string) {
	  
    SQLiteDatabase db = eventos.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("hora", System.currentTimeMillis());
    values.put("titulo", string + " - " + System.currentTimeMillis());
    db.insertOrThrow("evento", null, values);
  }

  private Cursor getEvents() {
    SQLiteDatabase db = eventos.getReadableDatabase();
    Cursor cursor = db.query("evento", FROM, null, null, null, null,ORDER_BY);
    startManagingCursor(cursor);
    return cursor;
  }

  private void showEvents(Cursor cursor) {
    StringBuilder builder = new StringBuilder("Eventos salvos:\n");
    while (cursor.moveToNext()) {
      long id = cursor.getLong(0);
      long hora = cursor.getLong(1);
      String titulo = cursor.getString(2);
      builder.append(id).append(": ");
      builder.append(hora).append(": ");
      builder.append(titulo).append("\n");
    }

    TextView text = (TextView) findViewById(R.id.teste);    
    text.setText(builder);
    
    
  }

}

class EventosData extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "eventos.db";
  private static final int DATABASE_VERSION = 1;
  
  public EventosData(Context ctx) {
    super(ctx, DATABASE_NAME, null, DATABASE_VERSION);    
  }

  @Override
  public void onCreate(SQLiteDatabase db) {	  
    db.execSQL("CREATE TABLE evento (id INTEGER PRIMARY KEY AUTOINCREMENT, hora INTEGER,titulo TEXT NOT NULL);");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS evento");
    onCreate(db);
  }
}