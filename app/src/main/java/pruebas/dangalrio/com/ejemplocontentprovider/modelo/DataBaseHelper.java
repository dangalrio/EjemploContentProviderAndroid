package pruebas.dangalrio.com.ejemplocontentprovider.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase envoltura para el gestor de Base de datos
 */

public class DataBaseHelper extends SQLiteOpenHelper{

    public DataBaseHelper(Context context,
                          String name,
                          SQLiteDatabase.CursorFactory factory,
                          int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTable(sqLiteDatabase); // Crear la tabla actividad
        loadDummyData(sqLiteDatabase); // Cargar 5 datos de prueba
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //actualizaciones
    }

    /**
     * Crear la tabla en la base de datos
     * @param database Instancia de la base de datos
     */
    private void createTable(SQLiteDatabase database){
        String cmd = "CREATE TABLE " + TechsContract.ACTIVIDAD + " ("+
                TechsContract.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TechsContract.Columnas.CATEGORIA + " TEXT, " +
                TechsContract.Columnas.PRIORIDAD + " TEXT, " +
                TechsContract.Columnas.ESTADO + " TEXT, " +
                TechsContract.Columnas.TECNICO + " TEXT, " +
                TechsContract.Columnas.DESCRIPCION + " TEXT);";
        database.execSQL(cmd);
    }

    /**
     * Carga datos de ejemplo en la tabla
     * @param database Instancia de la base de datos
     */
    private void loadDummyData(SQLiteDatabase database){

        ContentValues values = new ContentValues();
        values.put(TechsContract.Columnas.CATEGORIA, "Factibilidad");
        values.put(TechsContract.Columnas.PRIORIDAD, "Media");
        values.put(TechsContract.Columnas.ESTADO, "Abierta");
        values.put(TechsContract.Columnas.TECNICO, "Juan Pedrozo");
        values.put(TechsContract.Columnas.DESCRIPCION, "LLevar router MX230");
        database.insert(TechsContract.ACTIVIDAD, null, values);

        values = new ContentValues();
        values.put(TechsContract.Columnas.CATEGORIA, "Reparación");
        values.put(TechsContract.Columnas.PRIORIDAD, "Alta");
        values.put(TechsContract.Columnas.ESTADO, "En Curso");
        values.put(TechsContract.Columnas.TECNICO, "Mirta Gomez");
        values.put(TechsContract.Columnas.DESCRIPCION, "Internet intermitente");
        database.insert(TechsContract.ACTIVIDAD, null, values);

        values = new ContentValues();
        values.put(TechsContract.Columnas.CATEGORIA, "Traslado");
        values.put(TechsContract.Columnas.PRIORIDAD, "Baja");
        values.put(TechsContract.Columnas.ESTADO, "Cerrada");
        values.put(TechsContract.Columnas.TECNICO, "Carlos Gutierrez");
        values.put(TechsContract.Columnas.DESCRIPCION, "Nueva dirección: Cra 4 #2C-90");
        database.insert(TechsContract.ACTIVIDAD, null, values);

        values = new ContentValues();
        values.put(TechsContract.Columnas.CATEGORIA, "Migración");
        values.put(TechsContract.Columnas.PRIORIDAD, "Baja");
        values.put(TechsContract.Columnas.ESTADO, "Abierta");
        values.put(TechsContract.Columnas.TECNICO, "Gloria Quiñonez");
        values.put(TechsContract.Columnas.DESCRIPCION, "Sustitución cable soporte ipV6");
        database.insert(TechsContract.ACTIVIDAD, null, values);

        values = new ContentValues();
        values.put(TechsContract.Columnas.CATEGORIA, "Mantenimiento");
        values.put(TechsContract.Columnas.PRIORIDAD, "Media");
        values.put(TechsContract.Columnas.ESTADO, "En Curso");
        values.put(TechsContract.Columnas.TECNICO, "Julian Arreondo");
        values.put(TechsContract.Columnas.DESCRIPCION, "LLevar Lista de checkeo rendimiento");
        database.insert(TechsContract.ACTIVIDAD, null, values);
    }
}
