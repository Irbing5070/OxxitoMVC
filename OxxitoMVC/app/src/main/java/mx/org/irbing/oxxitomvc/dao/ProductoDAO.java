package mx.org.irbing.oxxitomvc.dao;

/**
 * Created by Irbing on 03/03/2018.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.content.Context;
import mx.org.irbing.oxxitomvc.model.Producto;

public class ProductoDAO {
    private Context contexto;

    /**
     * Método constructor que inicializa la variable contexto
     * @param contexto
     */
    public ProductoDAO(Context contexto) {
        this.contexto = contexto;
    }

    /**
     * Método para insertar un objeto Producto en la BD
     * @param obj
     */
    public void insert(Producto obj) throws Exception{
        //Se crea la sentencia a ejecutar en la base de datos
        String comando = "INSERT INTO PRODUCTOS(codigo, nombre, precio, existencias, fecha_caducidad) " +
                "VALUES('" + obj.getCodigo() + "', '" + obj.getNombre() + "', " + obj.getPrecio() +
                ", " + obj.getExistencias() + ",'" + obj.getFechaCaducidad() + "' )";
        //Se crea un objeto Conexion
        Conexion con = new Conexion(contexto);
        try {
            //Se ejecuta la sentencia
            con.ejecutarSentencia(comando);
        } catch (Exception e) {
            //Lanzamos la excepciÃ³n aquien invoca el método para que el lo maneje
            throw new Exception("Error al insertar: " + e.getMessage());
        }
    }

    /**
     * MÃ©todo para actualizar un objeto Producto en la BD
     * @param obj
     */
    public void update(Producto obj) throws Exception{
        //Se crea la sentencia a ejecutar en la base de datos
        String comando = "UPDATE PRODUCTOS SET " +
                "nombre='" + obj.getNombre() + "'," +
                "precio=" + obj.getPrecio() + "," +
                "existencias=" + obj.getExistencias() + "," +
                "fecha_caducidad='" + obj.getFechaCaducidad() + "' " +
                "WHERE codigo='" + obj.getCodigo() + "'";
        //Se crea un objeto Conexion
        Conexion con = new Conexion(contexto);
        try {
            //Se ejecuta la sentencia
            con.ejecutarSentencia(comando);
        } catch (Exception e) {
            //Lanzamos la excepción aquien invoca el método para que el lo maneje
            throw new Exception("Error al actualizar: " + e.getMessage());
        }
    }

    /**
     * MÃ©todo para eliminar un objeto Producto en la BD
     * @param obj
     */
    public void delete(Producto obj) throws Exception{
        //Se crea la sentencia a ejecutar en la base de datos
        String comando = "DELETE FROM PRODUCTOS WHERE codigo='" + obj.getCodigo() + "'";
        //Se crea un objeto Conexion
        Conexion con = new Conexion(contexto);
        try {
            //Se ejecuta la sentencia
            con.ejecutarSentencia(comando);
        } catch (Exception e) {
            //Lanzamos la excepción aquien invoca el método para que el lo maneje
            throw new Exception("Error al eliminar: " + e.getMessage());
        }
    }

    /**
     * Método para listar todos los objetos Producto de la BD
     * @return
     */
    public List<Producto> getAll() throws Exception{
        //Se especifica el nombre de la tabla a consultar
        String tabla = "PRODUCTOS";
        //Se indica los campos a consultar de la tabla
        String campos[] = new String[]{"codigo","nombre","precio","existencias","fecha_caducidad"};
        //Se crea una lista para almacenar los objetos producto almacenados en la tabla
        List<Producto> listaProductos = new ArrayList<>();
        //Se abre la conexión a la BD
        Conexion con = new Conexion(contexto);
        //Se consulta mediante la conexiÃ³n todos los registros y campos de la tabla
        List<HashMap<String, String>> resultado = con.ejecutarConsulta(tabla, campos, null);

        //Se crea una referencia a un objeto Producto
        Producto pro;
        //se recorre cada uno de los registros regresados de la consulta.
        for (HashMap<String, String> reg : resultado) {
            //Por cada registro se crea un objeto Producto
            pro = new Producto();
            //Se asigna cada uno de los atributos del objeto producto.
            pro.setCodigo(reg.get("codigo"));
            pro.setNombre(reg.get("nombre"));
            pro.setPrecio(Double.valueOf(reg.get("precio")));
            pro.setExistencias(Integer.valueOf(reg.get("existencias")));
            pro.setFechaCaducidad(reg.get("fecha_caducidad"));
            //Se inserta el objeto al producto en la lista
            listaProductos.add(pro);
        }
        return listaProductos;
    }

    /**
     * MÃ©todo para buscar un objeto Producto por su ID en la BD
     * @param obj
     * @return
     */
    public Producto getById(Producto obj) throws Exception{
        //Se especifica el nombre de la tabla a consultar
        String tabla = "PRODUCTOS";
        //Se indica los campos a consultar de la tabla
        String campos[] = new String[]{"codigo","nombre","precio","existencias","fecha_caducidad"};
        //Se especifica la condiciÃ³n para realizar la consulta.
        String condicion = "codigo = '" + obj.getCodigo() + "'";
        //Se abre la conexiÃ³n a la BD
        Conexion con = new Conexion(contexto);
        //Se consulta mediante la conexiÃ³n todos los registros y campos de la tabla
        List<HashMap<String, String>> resultado = con.ejecutarConsulta(tabla, campos, condicion);
        //Se crea una referencia a un objeto Producto
        Producto pro = null;
        //se recorre cada uno de los registros regresados de la consulta.
        for (HashMap<String, String> reg : resultado) {
            //Por cada registro se crea un objeto Producto
            pro = new Producto();
            //Se asigna cada uno de los atributos del objeto producto.
            pro.setCodigo(reg.get("codigo"));
            pro.setNombre(reg.get("nombre"));
            pro.setPrecio(Double.valueOf(reg.get("precio")));
            pro.setExistencias(Integer.valueOf(reg.get("existencias")));
            pro.setFechaCaducidad(reg.get("fecha_caducidad"));

        }
        //Se retorna el objeto producto
        return pro;
    }
}
