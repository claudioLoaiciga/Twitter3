package com.example.twitter3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class MainActivity extends AppCompatActivity {
//Declaración de objeto LoginButton
    TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);//Statement/Debe ir antes del setContentView xq sino el botton quedará deshabilitado
        setContentView(R.layout.activity_main);

        //Método para inicializar funcion de Login Twitter
        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                //Autenticación de Sesión via Twitter,
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                //Método Login
                login(session);
            }

            public void login(TwitterSession session){// Método que inicializará logueo de usuario/obtendrá nombre de usuario e inicializará una nueva actividad
                //obtener el nombre de usuario
                String username = session.getUserName();
                //este dato tiene que pasarse a la nueva actividad (homepage)
                Intent intent= new Intent(MainActivity.this , HomePage.class);
                //Pasar nombre de usuario
                intent.putExtra("username", username);
                startActivity(intent);//Iniciar actividad y pasar la intención como parametro
            }

            @Override
            public void failure(TwitterException exception) {
                //En caso de fallo de inicio de sesión
                Toast.makeText(MainActivity.this, "Fallo de autenticacion", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
