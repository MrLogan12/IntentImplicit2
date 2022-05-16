package com.example.intentimplicit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    //defnimos los atributos de las vistas
   private EditText etTelefono;
   private ImageButton btnLlamar, btnCamara;

    private String numeroTelefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarVistas();
        obtenerInformacion();
        configurarIntentImplicito();
    }

    private void configurarIntentImplicito() {
        if(!numeroTelefono.isEmpty()) {
            //primer problema
            //las llamadas han cambiado desde la version 6 o sdk23
            //a partir de esa version se hace el codigo con algunos cambios
            //antes de esa version tenia otra manera de hacer el codigo

             //validar si vesion de tu proyecto es mayor o igual
            //a la version de android donde cammbio su forma de procesar esta llamada
            //ej: SDK_INT = 24 ej: VERSION_CODES.M=230
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //para versiones nuevas
            }else{
                //versiones antiguas
            configurarVersionesAntiguas();
            }
        }

    }

    private void configurarVersionesAntiguas() {
        //configuraresmos el intent para versiones anteriores
        //intent implicito ->1 accion que se desea realizar
        //                 ->2 que datos quieres enviar en el intent
        //URI es como la URL de web donde configuras las cabceras
        // tu ruta donde quieres pasasr datos
        Intent intentLlamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+numeroTelefono));
        startActivity(intentLlamada);

    }

    private void obtenerInformacion(){
        numeroTelefono= etTelefono.getText().toString();   }

    private void inicializarVistas() {

        etTelefono = findViewById(R.id.etTelefono);
        btnLlamar = findViewById(R.id.btnLlamar);
        btnCamara = findViewById(R.id.btnCamara);
    }
}