package pruebas.dangalrio.com.ejemplocontentprovider.modelo;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract class entre el provider y las aplicaciones
 */

public class TechsContract {

    /**
     * Representación de la tabla a consultar**/
    public static final String ACTIVIDAD = "actividad";

    /**
     * Autoridad del content Provider
     * **/
    public static final String AUTHORITY = "pruebas.dangalrio.com.ejemplocontentprovider.modelo.TechsProvider";
    /**
     * URI del contenido principal
     **/
    public static final Uri CONTENT_URI = Uri.parse("Content://" + AUTHORITY + "/" + ACTIVIDAD);

    /**
     * Código para URI de multiples registros
     **/
    public static final int ALLROWS = 1;
    /**
     * Código para URI de un sólo registro
     * **/
    public static final int SINGLEROW = 2;
    /**
     * Comparador de Uri de contenidos**/
    public static final UriMatcher uriMatcher;

    //Asignación de URIs
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, ACTIVIDAD, ALLROWS);
        uriMatcher.addURI(AUTHORITY, ACTIVIDAD + "/#",SINGLEROW);
    }

    /**
     * Tipo MIME que retorna la oonsulta de una sola fila
     */
    public static final String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + AUTHORITY + ACTIVIDAD;
    /**
     * Tipo MIME que retorna la consulta de {@link CONTENT_URI}
     */
    public static final String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + AUTHORITY + ACTIVIDAD;


    /**
     * Estructura de la tabla**/
    public static class Columnas implements BaseColumns{

        private Columnas(){
            //Sin instancias
        }

        /**
         * Categoria de la actividad
         */
        public final static String CATEGORIA = "categoria";
        /**
         * Descripción de la actividad
         */
        public final static String DESCRIPCION = "descripcion";
        /**
         * Técnico asignado a la actividad
         */
        public static String TECNICO = "tecnico";
        /**
         * Estado en que se encuentra la acitvidad
         */
        public static String ESTADO = "estado";
        /**
         * Prioridad de realización de la actividad
         */
        public static String PRIORIDAD = "prioridad";

    }




}
