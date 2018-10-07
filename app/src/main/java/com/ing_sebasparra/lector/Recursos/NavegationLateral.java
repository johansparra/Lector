package com.ing_sebasparra.lector.Recursos;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.Window;

import com.ing_sebasparra.lector.GpsActivity;
import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.View.MapsActivity;
import com.ing_sebasparra.lector.View.OpcionesActivity;
import com.ing_sebasparra.lector.View.PagoTransmilenioActivity;
import com.ing_sebasparra.lector.View.PerfilActivity;

public class NavegationLateral {


    public void navegationContent(NavigationView navigationView, final Context context,final DrawerLayout drawerLayout) {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {

                            case R.id.item_navigation_drawer_perfil:
                                menuItem.setChecked(true);
                                Intent perfil = new Intent(context, PerfilActivity.class);
                                context.startActivity(perfil);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_nfc:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent pago = new Intent(context, PagoTransmilenioActivity.class);
                                context.startActivity(pago);
                                return true;
                            case R.id.item_navigation_drawer_recargar:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent recargar = new Intent(context, GpsActivity.class);
                                context.startActivity(recargar);
                                return true;
                            case R.id.item_navigation_drawer_historial:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent historial = new Intent(context, GpsActivity.class);
                                context.startActivity(historial);
                                return true;
                            case R.id.item_navigation_drawer_configuracion:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent1 = new Intent(context, OpcionesActivity.class);
                                context.startActivity(intent1);
                                return true;
                            case R.id.item_navigation_drawer_maps:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent maps = new Intent(context, MapsActivity.class);
                                context.startActivity(maps);
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
