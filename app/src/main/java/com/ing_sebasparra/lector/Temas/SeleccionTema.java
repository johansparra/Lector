package com.ing_sebasparra.lector.Temas;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ing_sebasparra.lector.R;

public class SeleccionTema extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean homeButton = false, themeChanged;

    public void theme(Context context) {
        try {

            sharedPreferences = context.getSharedPreferences("VALUES", Context.MODE_PRIVATE);
            int theme = sharedPreferences.getInt("THEME", 0);
            settingTheme(theme,context);
            themeChanged();
        } catch (Exception e) {
            Log.e("Error thema",e.getMessage());

        }

    }
    private void themeChanged() {
        themeChanged = sharedPreferences.getBoolean("THEMECHANGED", false);
        homeButton = true;
    }

    public void settingTheme(int theme,Context context) {
        try {
            switch (theme) {
                case 1:
                    context.setTheme(R.style.AppTheme);
                    break;
                case 2:
                    context.setTheme(R.style.AppTheme2);
                    break;
                case 3:
                    context.setTheme(R.style.AppTheme3);
                    break;
                case 4:
                    context.setTheme(R.style.AppTheme4);
                    break;
                case 5:
                    context.setTheme(R.style.AppTheme5);
                    break;
                case 6:
                    context.setTheme(R.style.AppTheme6);
                    break;
                case 7:
                    context.setTheme(R.style.AppTheme7);
                    break;
                case 8:
                    context.setTheme(R.style.AppTheme8);
                    break;
                case 9:
                    context.setTheme(R.style.AppTheme9);
                    break;
                case 10:
                    context.setTheme(R.style.AppTheme10);
                    break;
                default:
                    context.setTheme(R.style.AppTheme);
                    break;
            }




        }catch (Exception e){
            Log.e("Error settingTheme: ",e.getMessage());
        }


    }


    public void setThemeFragment(int theme,SharedPreferences shared) {
        switch (theme) {
            case 1:
                editor = shared.edit();
                editor.putInt("THEME", 1).apply();
                break;
            case 2:
                editor = shared.edit();
                editor.putInt("THEME", 2).apply();
                break;
            case 3:
                editor = shared.edit();
                editor.putInt("THEME", 3).apply();
                break;
            case 4:
                editor = shared.edit();
                editor.putInt("THEME", 4).apply();
                break;
            case 5:
                editor = shared.edit();
                editor.putInt("THEME", 5).apply();
                break;
            case 6:
                editor = shared.edit();
                editor.putInt("THEME", 6).apply();
                break;
            case 7:
                editor = shared.edit();
                editor.putInt("THEME", 7).apply();
                break;
            case 8:
                editor = shared.edit();
                editor.putInt("THEME", 8).apply();
                break;
            case 9:
                editor = shared.edit();
                editor.putInt("THEME", 9).apply();
                break;
            case 10:
                editor = shared.edit();
                editor.putInt("THEME", 10).apply();
                break;
        }
    }






}
