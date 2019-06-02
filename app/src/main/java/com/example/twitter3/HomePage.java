package com.example.twitter3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;


public class HomePage extends AppCompatActivity {

    private TabLayout tabLayout;

    private ViewPager viewPager;
    private EditText multiText;

    public void goFinalActivity(View view) {/// Método que me envía o redirige hacia una vista que maneja una clase
        Intent intent = new Intent( this, HomePage.class);
        startActivity(intent);
    }
    public void tweets(View view) {/// Método que me envía o redirige hacia una vista que maneja una clase
        final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                .getActiveSession();
        final Intent intent = new ComposerActivity.Builder(this)
                .session(session)
                .text(String.valueOf(multiText.getText()))
                .hashtags("#twitter")
                .createIntent();
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        tabLayout=(TabLayout)findViewById(R.id.tabLayoutId);// asocio con el id que se le da en la vista del xml

        viewPager= (ViewPager)findViewById(R.id.viewPagerId);
        multiText= (EditText) findViewById(R.id.multiText);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());// Me falta de entender bien este metodo
        //aca se llama al metodo addFragment creado en la clase ViewPageAdapters donde se añaden los Fragmentos/Tabs
        adapter.addFragments(new UserTimelineFragment(),"TIMELINE");
        adapter.addFragments(new TweetsFragment(),"TWEETS");

        //Configurando el adapter
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        String use = getIntent().getStringExtra("username");
        Toast.makeText(HomePage.this,use, Toast.LENGTH_LONG).show();
    }






}
