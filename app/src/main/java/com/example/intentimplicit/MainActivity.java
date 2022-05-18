package com.example.intentimplicit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //defnimos los atributos de las vistas
    private EditText etTelefono;
    private ImageButton btnLlamar, btnCamara;
    private final int PHONE_CODE = 100;
    private String numeroTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarVistas();
        obtenerInformacion();
        configurarIntentImplicito();
        versionesNuevas();
    }

    private void versionesNuevas() {

    }

    private void configurarIntentImplicito() {
        if (!numeroTelefono.isEmpty()) {
            //primer problema
            //las llamadas han cambiado desde la version 6 o sdk23
            //a partir de esa version se hace el codigo con algunos cambios
            //antes de esa version tenia otra manera de hacer el codigo

            //validar si vesion de tu proyecto es mayor o igual
            //a la version de android donde cammbio su forma de procesar esta llamada
            //ej: SDK_INT = 24 ej: VERSION_CODES.M=230
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //
                //
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CODE);
                //para versiones nuevas

            } else {
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
        if (revisarPermisos(Manifest.permission.CALL_PHONE)) {
            Intent intentLlamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numeroTelefono));
            startActivity(intentLlamada);
        } else {
            Toast.makeText(this, "permisos denegados", Toast.LENGTH_SHORT);
        }

    }

    private void obtenerInformacion() {
        numeroTelefono = etTelefono.getText().toString();
    }

    private void inicializarVistas() {

        etTelefono = findViewById(R.id.etTelefono);
        btnLlamar = findViewById(R.id.btnLlamar);
        btnCamara = findViewById(R.id.btnCamara);

    }

    private boolean revisarPermisos(String permiso) {
        //android maneja los permisos de sta manera:
        //granted: permiso otrogado
        //denied: permiso no otrogado
        //validar si el permiso a evaluar en su aplicacion tiene  el valor de android
        // que maneja android para otorgar un permiso otrogado que seria
        //granted
        int resultadoPermiso = checkCallingOrSelfPermission(permiso);
        return resultadoPermiso == PackageManager.PERMISSION_GRANTED;


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Verificar el servicio a evaluar
        switch (requestCode) {
            case PHONE_CODE:
                String permiso = permissions[0];
                int valorPermiso = grantResults[0];
                //evitar que hayan errores
                if (permiso.equals(Manifest.permission.CALL_PHONE)) {
                    if (valorPermiso == PackageManager.PERMISSION_GRANTED) { //Evaluar si el permiso ha sido otorgado o denegado
                        Intent intentLlamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numeroTelefono));
                        startActivity(intentLlamada);
                    } else {
                        Toast.makeText(this, "El permiso esta denegado", Toast.LENGTH_LONG);
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
}




