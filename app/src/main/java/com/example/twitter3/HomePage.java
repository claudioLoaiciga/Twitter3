package com.example.twitter3;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;


public class HomePage extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);//Contenido de la vista de homepage
        //Obtener intencion
        String username= getIntent().getStringExtra("username");

        //Crear un textView
        TextView uname= findViewById(R.id.tv_username);
        uname.setText(username);
    }
}
