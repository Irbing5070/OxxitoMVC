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

public class NuevoProductoActivity extends AppCompatActivity{

    //Se declaran los controles a recuperar de la vista
    private EditText txtCodigo;
    private EditText txtNombre;
    private EditText txtPrecio;
    private EditText txtExistencias;
    private EditText txtFechaCaducidad;
    private Button btnGuardar;
    private Button btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);
        //1. Se relacionan los controles de la actividad con los controles de la vista
        txtCodigo = (EditText)findViewById(R.id.txt_codigo);
        txtNombre = (EditText)findViewById(R.id.txt_producto);
        txtPrecio = (EditText)findViewById(R.id.txt_precio);
        txtExistencias = (EditText)findViewById(R.id.txt_existencias);
        txtFechaCaducidad = (EditText)findViewById(R.id.txt_fecha_caducidad);
        btnGuardar = (Button)findViewById(R.id.btn_guardar);
        btnCancelar = (Button)findViewById(R.id.btn_cancelar);
        //2. Se asigna escuchador de clics al btnGuardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), "datos: " , Toast.LENGTH_LONG);
                //Se crea un objeto Producto
                Producto p = new Producto();
                //Se asignan los atributos del objeto
                p.setCodigo(txtCodigo.getText().toString());
                p.setNombre(txtNombre.getText().toString());
                p.setPrecio(Double.valueOf(txtPrecio.getText().toString()));
                p.setExistencias(Integer.valueOf(txtExistencias.getText().toString()));
                p.setFechaCaducidad(txtFechaCaducidad.getText().toString());
                //Se crea un objeto DAO para almacenar el objeto
                Toast.makeText(getApplicationContext(), "datos: " + p.toString() , Toast.LENGTH_LONG);
                ProductoDAO dao = new ProductoDAO(getApplicationContext());
                try {
                    //Se trata de insertar el objeto
                    dao.insert(p);
                    //Se muestra un mensaje ÃƒÂ©xito y se cierra la vista
                    Toast.makeText(getApplicationContext(), "Prodcuto almacenado", Toast.LENGTH_SHORT).show();
                    System.exit(0);
                } catch (Exception e) {
                    //En caso de excepciÃƒÂ³n se muestra el mensaje
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //3. Se asigna escuchador de clics al btnCancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Se cierra la vista
                System.exit(0);
            }
        });
    }


}
