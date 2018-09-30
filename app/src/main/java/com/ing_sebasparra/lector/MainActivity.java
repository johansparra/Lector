package com.ing_sebasparra.lector;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ing_sebasparra.lector.Maps.MapsActivity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    //CARGAR EL TEMA
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    Boolean homeButton = false, themeChanged;
    FrameLayout statusBar;

    // VARIABLES DEL XML
    private Button siguiente1;

    //comprobando control de versiones
    TextView mInfoText;
    NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Seleccionar el tema guardado por el usuario (siempre antes de setContentView)
        theme();
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Siguiente del tema bar
        toolbarStatusBar();
        themeChanged();
        // hasta aca va el tema

        drawerLayout = findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        setupNavigationDrawerContent(navigationView);


        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter != null) {
      /*      NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
            NdefRecord rec = NdefRecord.createUri("http://www.google.com");
            NdefMessage msg = new NdefMessage(rec);
            adapter.setNdefPushMessage(msg, this);*/
           /* Intent ListSong = new Intent(getApplicationContext(), Beam.class);
            startActivity(ListSong);
*/

            mNfcAdapter.setNdefPushMessage(null, this);

        }

        //CARGAR VARIABLES
        siguiente1 = findViewById(R.id.siguiente);


        siguiente1.setEnabled(false);
        if (validaPermisos()) {
            siguiente1.setEnabled(true);
        } else {
            siguiente1.setEnabled(false);
        }


    }


    //LO PRIMERO ES VALIDAR LOS PERMISOS
    private boolean validaPermisos() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if ((checkSelfPermission(RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(INTERNET) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                ) {
            return true;
        }

        if ((shouldShowRequestPermissionRationale(RECORD_AUDIO)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) ||
                (shouldShowRequestPermissionRationale(INTERNET)) ||
                (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION))
                ) {
            cargarDialogoRecomendacion();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO, INTERNET, ACCESS_FINE_LOCATION}, 100);
        }

        return false;
    }
//nfc

    @Override
    public void onResume() {
        super.onResume();
        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
        PendingIntent pi = PendingIntent.getActivity(
                this,
                0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                0);
        adapter.enableForegroundDispatch(this, pi, null, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length == 4 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                siguiente1.setEnabled(true);
            } else {
                solicitarPermisosManual();
            }
        }

    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones = {"si", "no"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(MainActivity.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Los permisos no fueron aceptados", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO, INTERNET, ACCESS_FINE_LOCATION}, 100);
            }
        });
        dialogo.show();
    }

    //HASTA ACA VALIDAR PERMISOS
    @Override
    protected void onNewIntent(Intent intent) {
        try {
            if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED) ||
                    intent.getAction().equals(NfcAdapter.ACTION_NDEF_DISCOVERED) ||
                    intent.getAction().equals(NfcAdapter.ACTION_TECH_DISCOVERED)) {
                Intent intent3 = new Intent(MainActivity.this, Beam.class);
                startActivity(intent3);


            }
        } catch (Exception e) {
            Log.e("", "onIntent >>> " + e.getMessage());

        }

    }


    // MENUS LATERALES
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_acerca, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_acerca) {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialogo_acerca);
            dialog.show();
            return true;
        }
        if (id == R.id.action_logout) {
            logout();
        }
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_navigation_drawer_inicio:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_perfil:
                                menuItem.setChecked(true);
                                Toast.makeText(MainActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.item_navigation_drawer_gps:
                                menuItem.setChecked(true);
                                Toast.makeText(MainActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent2 = new Intent(MainActivity.this, GpsActivity.class);
                                startActivity(intent2);
                                return true;
                            case R.id.item_navigation_drawer_nfc:
                                menuItem.setChecked(true);
                               // Toast.makeText(MainActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent3 = new Intent(MainActivity.this, Beam.class);
                                startActivity(intent3);
                                return true;
                            case R.id.item_navigation_drawer_configuracion:
                                menuItem.setChecked(true);
                                Toast.makeText(MainActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent4 = new Intent(MainActivity.this, OpcionesActivity.class);
                                startActivity(intent4);
                                return true;
                            case R.id.item_navigation_drawer_maps:
                                /*menuItem.setChecked(true);
                                Toast.makeText(MainActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;*/
                                menuItem.setChecked(true);
                                Toast.makeText(MainActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent6 = new Intent(MainActivity.this, MapsActivity.class);
                                startActivity(intent6);
                                return true;
                        }
                        return true;
                    }
                });
    }
    // HASTA ACA MENUS LATERALES

    // BOTON ATRAS CELULAR
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        System.exit(1);
        finish();

        System.exit(0);
        ActivityCompat.finishAffinity(this);
        System.exit(1);


        System.exit(1);
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    // HASTA ACA BOTON ATRAS
    // TEMA NO CAMBIAR
    public void theme() {
        sharedPreferences = getSharedPreferences("VALUES", Context.MODE_PRIVATE);
        int theme = sharedPreferences.getInt("THEME", 0);
        settingTheme(theme);
    }

    private void themeChanged() {
        themeChanged = sharedPreferences.getBoolean("THEMECHANGED", false);
        homeButton = true;
    }

    public void toolbarStatusBar() {
        statusBar = findViewById(R.id.statusBar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Titulo que se visualiza en en action bar
        getSupportActionBar().setTitle("Inicio");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void settingTheme(int theme) {
        switch (theme) {
            case 1:
                setTheme(R.style.AppTheme);
                break;
            case 2:
                setTheme(R.style.AppTheme2);
                break;
            case 3:
                setTheme(R.style.AppTheme3);
                break;
            case 4:
                setTheme(R.style.AppTheme4);
                break;
            case 5:
                setTheme(R.style.AppTheme5);
                break;
            case 6:
                setTheme(R.style.AppTheme6);
                break;
            case 7:
                setTheme(R.style.AppTheme7);
                break;
            case 8:
                setTheme(R.style.AppTheme8);
                break;
            case 9:
                setTheme(R.style.AppTheme9);
                break;
            case 10:
                setTheme(R.style.AppTheme10);
                break;
            default:
                setTheme(R.style.AppTheme);
                break;
        }
    }
    // HASTA ACA CARGAR EL TEMA

    // XML
    public void siguiente_1(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    //CERRRAR SESION
    private void logout() {
        // Creación de un cuadro de diálogo de alerta para confirmar el cierre de sesión
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(" ¿Seguro que quieres cerrar la sesión? ");
        alertDialogBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        // Salir de las preferencias compartidas
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        // Obtener editor
                        SharedPreferences.Editor editor = preferences.edit();
                        // Poner el valor false para iniciar sesión
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);
                        // Poner valor en blanco en el correo electrónico
                        editor.putString(Config.EMAIL_SHARED_PREF, "");
                        // Guardar las preferencias compartidas
                        editor.commit();
                        //Lanzando el activity del usuario
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
        alertDialogBuilder.setNegativeButton("No ",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        // Mostrar el cuadro de diálogo de alerta
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}

//comentario