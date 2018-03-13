package mx.org.irbing.oxxitomvc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mx.org.irbing.oxxitomvc.dao.ProductoDAO;
import mx.org.irbing.oxxitomvc.model.Producto;

/**
 * Created by Irbing on 10/03/2018.
 */

public class GestionProductoActivity extends AppCompatActivity {

    //Se declaran los controles a recuperar de la vista
    private EditText txtCodigo;
    private EditText txtNombre;
    private EditText txtPrecio;
    private EditText txtExistencias;
    private EditText txtFechaCaducidad;
    private Button btnActualizar;
    private Button btnCancelar;
    private Button btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //Se indica la vista a cargar
        setContentView(R.layout.activity_gestion_producto);
        //1. Se relacionan los controles de la actividad con los controles de la vista
        txtCodigo = (EditText)findViewById(R.id.txt_codigo);
        txtNombre = (EditText)findViewById(R.id.txt_producto);
        txtPrecio = (EditText)findViewById(R.id.txt_precio);
        txtExistencias = (EditText)findViewById(R.id.txt_existencias);
        txtFechaCaducidad = (EditText)findViewById(R.id.txt_fecha_caducidad);
        btnActualizar = (Button)findViewById(R.id.btn_actualizar);
        btnCancelar = (Button)findViewById(R.id.btn_cancelar);
        btnEliminar = (Button)findViewById(R.id.btn_eliminar);
        //2. Se obtiene el valor CODIGO_DE_BARRAS de un bundle de la actividad
        String codigo = getIntent().getExtras().getString("codigo");
        //Toast.makeText(getApplicationContext(), "Error: " + codigoBarras, Toast.LENGTH_SHORT).show();
        //3. Se crea un objeto Producto
        Producto p = new Producto();
        //Toast.makeText(getApplicationContext(), "Error: " + p.getCodigo(), Toast.LENGTH_SHORT).show();
        //4. Se asigna el codigo de barras del bundle al objeto para buscarlo en la BD
        p.setCodigo(codigo);
        //Toast.makeText(getApplicationContext(), "Error: " + p.getCodigo(), Toast.LENGTH_SHORT).show();
        //5. Se crea un objeto DAO para buscar el producto
        ProductoDAO dao = new ProductoDAO(getApplicationContext());
        try {
            p = dao.getById(p);
            //Se asigna el valor a los controles de acuerdo al valor de los atributos del objeto
            txtCodigo.setText(p.getCodigo());
            txtNombre.setText(p.getNombre());
            txtPrecio.setText(String.valueOf(p.getPrecio()));
            txtExistencias.setText(String.valueOf(p.getExistencias()));
            txtFechaCaducidad.setText(p.getFechaCaducidad());
        } catch (Exception e) {
            //En caso de excepciÃƒÂ³n se muestra el mensaje
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //6. Se agrega un escuchador de eventos al btnActualizar
        btnActualizar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Se crea un objeto Producto
                Producto p = new Producto();
                //Se asignan los atributos del objeto
                p.setCodigo(txtCodigo.getText().toString());
                p.setNombre(txtNombre.getText().toString());
                p.setPrecio(Double.valueOf(txtPrecio.getText().toString()));
                p.setExistencias(Integer.valueOf(txtExistencias.getText().toString()));
                p.setFechaCaducidad(txtFechaCaducidad.getText().toString());
                //Se crea un objeto DAO para almacenar el objeto
                ProductoDAO dao = new ProductoDAO(getApplicationContext());
                try {
                    //Se trata de insertar el objeto
                    dao.update(p);
                    //Se muestra un mensaje ÃƒÂ©xito y se cierra la vista
                    Toast.makeText(getApplicationContext(), "Producto actualizado", Toast.LENGTH_SHORT).show();
                    System.exit(0);
                } catch (Exception e) {
                    //En caso de excepciÃƒÂ³n se muestra el mensaje
                    Toast.makeText(getApplicationContext(), "Error Update: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //7. Se agrega un escuchador de eventos al btnEliminar
        btnEliminar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Se crea un objeto Producto
                Producto p = new Producto();
                //Se asignan los atributos del objeto
                p.setCodigo(txtCodigo.getText().toString());
                //Se crea un objeto DAO para almacenar el objeto
                ProductoDAO dao = new ProductoDAO(getApplicationContext());
                try {
                    //Se trata de insertar el objeto
                    dao.delete(p);
                    //Se muestra un mensaje de éxito y se cierra la vista
                    Toast.makeText(getApplicationContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
                    System.exit(0);
                } catch (Exception e) {
                    //En caso de excepción se muestra el mensaje
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //8. Se asigna escuchador de clics al btnCancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Se cierra la vista
                System.exit(0);
            }
        });
    }


}
