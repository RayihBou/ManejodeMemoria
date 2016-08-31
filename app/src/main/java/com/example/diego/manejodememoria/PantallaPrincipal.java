package com.example.diego.manejodememoria;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

public class PantallaPrincipal extends AppCompatActivity {

    //Revisar el android Manifest para los permisos

    /* Existen diferentes tipos de uso de memoria Interna o externa del Telefono:
    * Mode_Private: Acceso privado desde nuestra app, sobreescribe el contenido del fichero
    * Mode_Append: Añade contenido a un archivo existente y si no existe lo crea
    * Mode_World_Writable: Permite a cualquier app escribir en el archivo
    * Mode_World_Readable: Permite a cualquier app leer en el archivo.

    Podemos usar el Android Device Monitor para Monitorear el Uso de la memoria interna del Telefono.

    * Tambien se hara uso de las preferencias, en cuanto a mostrar preferencia y guardar preferencia.

    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
    }

    //Metodo para guardar (Preferencia)
    public void guardarPreferencia(View v){

        //Metodo para generar la preferencia, al igual se pueden utilizar diferentes tipos de memoria
        SharedPreferences miPreferenciaCompartida = getSharedPreferences("MisDatosPersonales",Context.MODE_PRIVATE);

        //Es necesario declarar un editor
        SharedPreferences.Editor editor = miPreferenciaCompartida.edit();

        //Declaracion de variables a utilizar de la activity
        EditText edtNombre = (EditText) findViewById(R.id.edtNombre);
        EditText edtCorreo = (EditText) findViewById(R.id.edtCorreo);

        //Pasar los datos ingresados en el editText a toString
        String nombre = edtNombre.getText().toString();
        String correo = edtCorreo.getText().toString();

        //Forma de Insertar los datos, existen diferentes tipos de put
        editor.putString("nombre", nombre);
        editor.putString("correo", correo);

        //Es necesario el commit para insertar los datos
        editor.commit();

        Toast.makeText(PantallaPrincipal.this, "Se ha creado la Preferencia Compartida", Toast.LENGTH_SHORT).show();
        //Colocar el texto en blanco nuevamente despues de guardar las preferencias
        edtNombre.setText("");
        edtCorreo.setText("");
    }

    //Metodo para mostrar (Preferencia)
    public void mostrarPreferencia(View v){
        //El mismo metodo usado para guardar preferencias
        SharedPreferences miPreferenciaCompartida = getSharedPreferences("MisDatosPersonales", Context.MODE_PRIVATE);

        //Solicitud de variable a mostrar, si la variable no existe se genera un mensaje especificando que no existe
        String nombre = miPreferenciaCompartida.getString("nombre", "No existe la variable nombre");
        String correo = miPreferenciaCompartida.getString("correo", "No existe la variable correo");

        //Mostrar las preferencias en el textView
        TextView tvPreferenciaCompartida = (TextView) findViewById(R.id.tvPreferenciaCompartida);
        //Mostrar el texto a utilizar en el textView
        String preferencia = "\nNombre: "+ nombre + "\nCorreo: " + correo;
        //Setear el texto
        tvPreferenciaCompartida.setText(preferencia);




    }

    //Metodo para generar el Archivo (Memoria)
    public void generarArchivo(View v){
        try{

            //Declarando variable del editText a utilizar
            //Ademas de ello pasar el texto recibido a toString para usarlo en el outputSteam Write
            EditText edtNombre = (EditText) findViewById(R.id.edtNombre);
            String nombre = edtNombre.getText().toString();

            FileOutputStream outputStream = null;
            outputStream = openFileOutput("MiArchivo.txt", Context.MODE_PRIVATE);
            //Escribir el archivo o memoria selecionado, lo cual los datos ingresados los pasamos a Bytes para que que la escritura y lectura sea mas rapido
            outputStream.write(nombre.getBytes());
            //Es muy importante cerrar el archivo
            outputStream.close();

            Toast.makeText(PantallaPrincipal.this, "El archivo se ha creado", Toast.LENGTH_SHORT).show();
            //Resetear el editText para que quede en Blanco despues de generar el archivo.
            edtNombre.setText("");

        }catch (Exception e){
            //Si ocurre algun error es metodo nos ayuda a rastrear el error
            e.printStackTrace();
            Toast.makeText(PantallaPrincipal.this, "Hubo un error en la escritura del archivo", Toast.LENGTH_SHORT).show();


        }
    }
}
