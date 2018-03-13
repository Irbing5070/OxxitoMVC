package mx.org.irbing.oxxitomvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import mx.org.irbing.oxxitomvc.dao.ProductoDAO;
import mx.org.irbing.oxxitomvc.model.Producto;


/**
 * Created by Irbing on 10/03/2018.
 */

public class ListaProductosActivity extends AppCompatActivity{
    //Se declaran los controles a relacionar de la vista
    private Button btnNuevo;
    private ListView lsvProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);
        //1. Se relacionan los controles de la vista con los controles de la actividad
        btnNuevo = (Button)findViewById(R.id.btn_agregar_producto);
        lsvProductos = (ListView)findViewById(R.id.lsv_productos);
        //2. Se asigna un escuhador de clics al btnNuevo
        btnNuevo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Se crea un intento para iniciar la ActividadNuevoProducto
                Intent intNuePro = new Intent(getApplicationContext(),
                        NuevoProductoActivity.class);
                //Se arranca la actividad
                startActivity(intNuePro);
            }
        });
        //3. Se crea un objeto ProductoDAO para el acceso a los datos
        ProductoDAO dao = new ProductoDAO(getApplicationContext());
        //4. se consulta la lista de productos almacenada y se le asigna a una variable
        List<Producto> listaProductos = new ArrayList<Producto>();

        try {
            //Se ejecuta la consulta
            listaProductos = dao.getAll();
        } catch (Exception e) {
            //En caso de haber alguna excepción esta se muestra mediante un mensaje
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        //5. Se crea una lista que almacenará objetos HashMap, donde cada HashMap tendrá los datos
        //a visualizarse en la lista por cada objeto
        List<HashMap<String, String>> filas = new ArrayList<HashMap<String,String>>();
        //6. Se declara un objeto HashMap que almacenará los datos a mostrar en
        //el ListView por cada objeto en la lista.
        HashMap<String, String> registro;
        //7. Se recorre la lista de objetos producto
        for (Producto prod : listaProductos) {
            //Por cada objeto Producto en la lista se crea un HashMap para
            //asignar sus datos en cada vista del registro
            registro = new HashMap<String, String>();
            //Se agregan los datos al HashMap a mostrar en la vista
            registro.put("codigo", prod.getCodigo());
            registro.put("nombre", prod.getNombre());
            registro.put("precio", String.valueOf(prod.getPrecio()));
            //Se agrega el HashMap a la lista
            filas.add(registro);
        }
        //8. Se crea un adaptador para mostrar los datos en el ListView
        SimpleAdapter adaptador = new SimpleAdapter(
                getApplicationContext(), //se pasa el contexto de la aplicación al adaptador
                filas, //Se indica la lista de registros a cargar en la vista
                R.layout.activity_registro_producto, //Se indica la vista a crear por cada HashMap
                new String[]{"codigo", "nombre","precio"}, //Se indica los datos a cargar por cada HashMap
                new int[]{R.id.txt_codigo2,R.id.txt_producto2,R.id.txt_precio2}); //Se indican los controles de la vista donde se cargarÃƒÂ¡n los valores del HashMap
        //9. Se asigna el adaptador a la ListView
        lsvProductos.setAdapter(adaptador);
        //10. Se asigna un escuchador de clics a los items de la ListView
        lsvProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                //Se obtiene el control que contiene el codigo de barras, del elemento seleccionado de la lista
                TextView txtCodigo = (TextView)arg1.findViewById(R.id.txt_codigo2);

                //Se crea un objeto bundle
                Bundle bundle = new Bundle();
                //Se inserta en el Bundle el codigo de barras del objeto seleccionado
                bundle.putString("codigo", txtCodigo.getText().toString());
                //Se crea un objeto Intent para iniciar la ActividadGestionProducto
                Intent intGesPro = new Intent(getApplicationContext(), GestionProductoActivity.class);
                //Se inserta el bundle en el intento
                intGesPro.putExtras(bundle);
                //Se inicia la actividad
                startActivity(intGesPro);
            }
        });

    }
}
