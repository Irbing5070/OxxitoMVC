package mx.org.irbing.oxxitomvc.dao;

/**
 * Created by Irbing on 03/03/2018.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Conexion {
    //Se define una varible Context. A partir de Ã©sta se crea la Base de datos
    private Context contexto;

    /**
     * MÃ©todo constructor que inicializa la variable contexto
     * @param contexto
     */
    public Conexion(Context contexto) {
        this.contexto = contexto;
    }

    /**
     * MÃ©todo para abrir la conexiÃ³n a la base de datos
     * @return
     */
    private SQLiteDatabase abrirConexion(){
        //Se crea un objeto SQLiteDatabase
        SQLiteDatabase conexion = contexto.openOrCreateDatabase(
                "oxxito.db", //NOmbre de la BD
                SQLiteDatabase.OPEN_READWRITE,//Modo de apertura
                null);//Factory para la BD
        //se regresa el objeto SQLiteDatabase
        return conexion;
    }

    /**
     * MÃ©todo para cerrar la conexiÃ³n con la base de datos
     * @param conexion
     */
    private void cerrarConexion(SQLiteDatabase conexion){
        //Si existe una conexiÃ³n
        if(conexion != null){
            //Se cierra la conexiÃ³n
            conexion.close();
        }
    }

    /**
     * MÃ©todo para ejecutar sentencias de SQL(Insert, Update, Delete)
     * @param sentencia
     * @return
     * @throws Exception
     */
    public boolean ejecutarSentencia(String sentencia) throws Exception{
        //Se abre la conexiÃ³n con la base de datos y se almacena
        SQLiteDatabase conexion = abrirConexion();
        try {
            //Se ejecuta la sentencia SQL sobre la conexiÃ³n
            conexion.execSQL(sentencia);
            //Se cierra la conexiÃ³n
            conexion.close();
        } catch (Exception e) {
            //Lanzamos la excepciÃ³n aquien invoca el mÃ©todo para que Ã©l lo maneje
            throw new Exception("Error al ejecutar la sentencia: " + e.getMessage());
        }
        //Si no ha habido ningÃºn error se retorna true
        return true;
    }

    /**
     * MÃ©todo para ejecutar consultas SQL (Select)
     * @param tabla
     * @param campos
     * @param condicion
     * @return
     * @throws Exception
     */
    public List<HashMap<String, String>> ejecutarConsulta(String tabla, String campos[], String condicion) throws Exception{
        //Se crea una lista de objetos HashMap, donde cada Hashmap representarÃ¡ un objeto de la BD
        List<HashMap<String, String>> datos = new ArrayList<HashMap<String,String>>();
        try {
            //Se abre la conexiÃ³n a la base de datos y se almacena
            SQLiteDatabase conexion = abrirConexion();
            //Se consulta de la tabla indicada los campos indicados de acuerdo a la condiciÃ³n
            Cursor resultado = conexion.query(tabla, campos, condicion, null, null, null, null);
            //Se crea un HashMap para formar un registro de la base de datos
            HashMap<String, String> registro;
            //Se itera sobre cada uno de los registros de la consulta
            while (resultado.moveToNext()){
                //Por cada registro en la Base de Datos se crea un HashMap
                registro = new HashMap<String, String>();
                //Se recorre cada uno de los campos solicitadosen la consulta
                for(int i = 0; i < campos.length; i++){
                    //Por cada elemento cmpo se inserta el nombre y el valor en el HashMap
                    registro.put(campos[i], resultado.getString(i));
                }
                //Se agrega el registro (grupo de campos y valores) en la lista
                datos.add(registro);
            }
            //Se cierra la conexiÃ³n
            conexion.close();
        } catch (Exception e) {
            //Lanzamos la excepciÃ³n aquien invoca el método para que Ã©l lo maneje
            throw new Exception("Error al ejecutar la consulta: " + e.getMessage());
        }
        //Si no ha habido ningÃºn error se retorna el resultado de la consulta
        return datos;
    }

    /**
     * MÃ©todo para inicializar (crear estructura) de la base de datos
     * @throws Exception
     */
    public void inicializaBD() throws Exception{
        //Se abre la conexiÃ³n a la base de datos
        SQLiteDatabase conexion = abrirConexion();
        //Se ejecuta sentencia para borrar la tabla de productos si es que ya existe
        conexion.execSQL("DROP TABLE IF EXISTS PRODUCTOS");
        //Se ejecuta la sentencia para crear la tabla productos
        conexion.execSQL("CREATE TABLE PRODUCTOS(codigo TEXT, nombre TEXT, precio REAL, existencias  INTEGER, fecha_caducidad TEXT)");
        //Se cierra la conexiÃ³n
        conexion.close();
    }

}

