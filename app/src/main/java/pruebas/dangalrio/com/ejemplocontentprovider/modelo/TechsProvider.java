package pruebas.dangalrio.com.ejemplocontentprovider.modelo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Conter Provider personalizado para las actividades
 */

public class TechsProvider extends ContentProvider{

    /**
     * Nombre de la base de datos
     */
    private static final String DATABASE_NAME = "techs.db";
    /**
     * Versión actual de la base de datos
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * Instancia del administrador de BD
     */
    private DataBaseHelper dataBaseHelper;

    @Override
    public boolean onCreate() {

        // Inicializando gestor BD
        dataBaseHelper = new DataBaseHelper(getContext(),
                DATABASE_NAME,
                null,
                DATABASE_VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //Obtener base de datos
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        //Comparar Uri
        int match = TechsContract.uriMatcher.match(uri);

        Cursor c;

        switch (match){
            case TechsContract.ALLROWS:
                // Consultando todos los registros
                c = db.query(TechsContract.ACTIVIDAD, projection,
                        selection,selectionArgs,null,null,sortOrder);
                c.setNotificationUri(getContext().getContentResolver(),
                        TechsContract.CONTENT_URI);
                break;
            case TechsContract.SINGLEROW:
                //Consultando un solo registro basado en el id del Uri
                long idActividad = ContentUris.parseId(uri);
                c = db.query(TechsContract.ACTIVIDAD, projection,
                        TechsContract.Columnas._ID + " = " + idActividad,
                        selectionArgs, null, null, sortOrder);
                c.setNotificationUri(getContext().getContentResolver(),
                        TechsContract.CONTENT_URI);
                break;
            default:
                throw new IllegalArgumentException("Uri no soportada: "+uri);
        }
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (TechsContract.uriMatcher.match(uri)){
            case TechsContract.ALLROWS:
                return TechsContract.MULTIPLE_MIME;
            case TechsContract.SINGLEROW:
                return TechsContract.SINGLE_MIME;
            default:
                throw new IllegalArgumentException("Tipo de actividad desconocida: " + uri);

        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        //Validar la uri
        if(TechsContract.uriMatcher.match(uri) != TechsContract.ALLROWS){
            throw new IllegalArgumentException("Uri desconocida: " + uri);
        }
        ContentValues contentValues1 = null;
        if(contentValues != null){
            contentValues1 = new ContentValues(contentValues);
        }else{
            contentValues = new ContentValues();
        }
        //Si es necesario, verificamos los valores
        //Inserción de una nueva fila
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        long rowId = db.insert(TechsContract.ACTIVIDAD,
                null,contentValues1);
        if(rowId > 0){
            Uri uri_actividad = ContentUris.withAppendedId(
                    TechsContract.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(uri_actividad,null);
            return uri_actividad;
        }
        throw new SQLException("Falla al insertar fila en: " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        int match = TechsContract.uriMatcher.match(uri);
        int affected;

        switch (match) {
            case TechsContract.ALLROWS:
                affected = db.delete(TechsContract.ACTIVIDAD,
                        selection,
                        selectionArgs);
                break;
            case TechsContract.SINGLEROW:
                long idActividad = ContentUris.parseId(uri);
                affected = db.delete(TechsContract.ACTIVIDAD,
                        TechsContract.Columnas._ID + "=" + idActividad
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                getContext().getContentResolver().
                        notifyChange(uri, null);
                break;
            default:
                throw new IllegalArgumentException("Elemento actividad desconocido: " +
                        uri);
        }
        return affected;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        int affected;
        switch (TechsContract.uriMatcher.match(uri)){
            case TechsContract.ALLROWS:
                affected = db.update(TechsContract.ACTIVIDAD,
                        contentValues,
                        selection,selectionArgs);
                break;
            case TechsContract.SINGLEROW:
                String idActividad = uri.getPathSegments().get(1);
                affected = db.update(TechsContract.ACTIVIDAD, contentValues,
                        TechsContract.Columnas._ID + "=" + idActividad
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Uri desconocida: "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return affected;
    }

}
