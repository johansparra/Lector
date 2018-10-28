package com.ing_sebasparra.lector.Recursos;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.Window;

import com.ing_sebasparra.lector.R;

public class NavegationLateral {
    IraActividades iraActividades = new IraActividades();


    public void navegationContent(NavigationView navigationView, final Context context, final DrawerLayout drawerLayout) {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {

                            case R.id.item_navigation_drawer_cuenta:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                iraActividades.iraCuenta(context);
                                return true;
                            case R.id.item_navigation_drawer_nfc:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                iraActividades.iraPagoTransmilenio(context);
                                return true;
                            case R.id.item_navigation_drawer_recargar:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                iraActividades.iraTarjetaCredito(context);
                                return true;
                            case R.id.item_navigation_drawer_configuracion:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                iraActividades.iraOpciones(context);
                                return true;
                            case R.id.item_navigation_drawer_maps:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                iraActividades.iraMaps(context);
                                return true;
                            case R.id.item_navigation_drawer_acercade:
                                menuItem.setChecked(true);
                                Dialog dialog = new Dialog(context);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.dialogo_acerca);
                                dialog.show();
                                return true;
                        }
                        return true;
                    }
                });
    }
}
