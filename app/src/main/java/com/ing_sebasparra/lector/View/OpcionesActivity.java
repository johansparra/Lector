package com.ing_sebasparra.lector.View;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.CerrarSesion;
import com.ing_sebasparra.lector.Recursos.NavegationLateral;
import com.ing_sebasparra.lector.Temas.SeleccionTema;
import com.ing_sebasparra.lector.Temas.Tema;

import java.util.Objects;

public class OpcionesActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;

    Intent intent;
    FrameLayout statusBar;
    RelativeLayout relativeLayoutChooseTheme;

    ViewGroup.LayoutParams layoutParamsStatusBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SeleccionTema selecTema = new SeleccionTema();
        selecTema.theme(this);
        setContentView(R.layout.activity_opciones);

        toolbarStatusBar();
        layoutParamsStatusBar = statusBar.getLayoutParams();

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {

            NavegationLateral navegation =new NavegationLateral();
            navegation.navegationContent(navigationView,this,drawerLayout);
        }


        relativeLayoutChooseTheme = (RelativeLayout) findViewById(R.id.relativeLayoutChooseTheme);
        relativeLayoutChooseTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Tema dialog = new Tema();
                dialog.show(fragmentManager, "fragment_color_chooser");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_acerca) {
            Dialog dialog = new Dialog(OpcionesActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialogo_acerca);
            dialog.show();
            return true;
        }

        if (id == R.id.action_logout) {
            CerrarSesion cerrar = new CerrarSesion();
            cerrar.logout(this);
        }
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(OpcionesActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void toolbarStatusBar() {
        statusBar = (FrameLayout) findViewById(R.id.statusBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.opciones));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
