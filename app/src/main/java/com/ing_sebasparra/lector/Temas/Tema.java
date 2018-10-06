package com.ing_sebasparra.lector.Temas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.ing_sebasparra.lector.View.OpcionesActivity;
import com.ing_sebasparra.lector.R;


public class Tema extends DialogFragment implements View.OnClickListener {

    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6, cardView7, cardView8, cardView9, cardView10;
    Button buttonDisagree, buttonAgree;
    View view;
    int currentTheme;
    SharedPreferences sharedPreferences;
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Guarde el tema actual para usar cuando el usuario presione cerrar en el cuadro de diálogo
        sharedPreferences = getActivity().getSharedPreferences("VALUES", Context.MODE_PRIVATE);
        currentTheme = sharedPreferences.getInt("THEME", 0);

        //mostrar el tema .xml
        view = inflater.inflate(R.layout.activity_tema, container);
        // remueve el titulo (si ya esta definido el etam .xml)
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogButtons();
        return view;
    }

    private void dialogButtons() {
        cardView1 = (CardView) view.findViewById(R.id.card_view1);
        cardView2 = (CardView) view.findViewById(R.id.card_view2);
        cardView3 = (CardView) view.findViewById(R.id.card_view3);
        cardView4 = (CardView) view.findViewById(R.id.card_view4);
        cardView5 = (CardView) view.findViewById(R.id.card_view5);
        cardView6 = (CardView) view.findViewById(R.id.card_view6);
        cardView7 = (CardView) view.findViewById(R.id.card_view7);
        cardView8 = (CardView) view.findViewById(R.id.card_view8);
        cardView9 = (CardView) view.findViewById(R.id.card_view9);
        cardView10 = (CardView) view.findViewById(R.id.card_view10);
        buttonDisagree = (Button) view.findViewById(R.id.buttonDisagree);
        buttonAgree = (Button) view.findViewById(R.id.buttonAgree);

        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        cardView4.setOnClickListener(this);
        cardView5.setOnClickListener(this);
        cardView6.setOnClickListener(this);
        cardView7.setOnClickListener(this);
        cardView8.setOnClickListener(this);
        cardView9.setOnClickListener(this);
        cardView10.setOnClickListener(this);
        buttonDisagree.setOnClickListener(this);
        buttonAgree.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_view1:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((OpcionesActivity) getActivity()).setThemeFragment(1);
                break;
            case R.id.card_view2:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((OpcionesActivity) getActivity()).setThemeFragment(2);
                break;
            case R.id.card_view3:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((OpcionesActivity) getActivity()).setThemeFragment(3);
                break;
            case R.id.card_view4:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((OpcionesActivity) getActivity()).setThemeFragment(4);
                break;
            case R.id.card_view5:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((OpcionesActivity) getActivity()).setThemeFragment(5);
                break;
            case R.id.card_view6:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((OpcionesActivity) getActivity()).setThemeFragment(6);
                break;
            case R.id.card_view7:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((OpcionesActivity) getActivity()).setThemeFragment(7);
                break;
            case R.id.card_view8:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((OpcionesActivity) getActivity()).setThemeFragment(8);
                break;
            case R.id.card_view9:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((OpcionesActivity) getActivity()).setThemeFragment(9);
                break;
            case R.id.card_view10:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((OpcionesActivity) getActivity()).setThemeFragment(10);
                break;
            case R.id.buttonDisagree:
                sharedPreferences.edit().putBoolean("THEMECHANGED", false).apply();
                ((OpcionesActivity) getActivity()).setThemeFragment(currentTheme);
                getDialog().dismiss();
                break;
            case R.id.buttonAgree:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                intent = new Intent(getActivity(), OpcionesActivity.class);
                startActivity(intent);
                break;
        }
    }
}