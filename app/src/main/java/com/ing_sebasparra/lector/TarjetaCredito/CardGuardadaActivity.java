package com.ing_sebasparra.lector.TarjetaCredito;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.CerrarSesionTarjeta;
import com.ing_sebasparra.lector.Recursos.Config;
import com.ing_sebasparra.lector.Recursos.IraActividades;
import com.ing_sebasparra.lector.TarjetaCredito.pager.CardFragmentAdapter.ICardEntryCompleteListener;
import com.ing_sebasparra.lector.TarjetaCredito.pager.CardFragmentAdapterRecarga;

import static com.ing_sebasparra.lector.TarjetaCredito.CreditCardUtils.CARD_NAME_PAGE;

public class CardGuardadaActivity extends AppCompatActivity {


    int mLastPageSelected = 0;
    private CreditCardView mCreditCardView;

    private String mCardNumber;
    private String mCVV;
    private String mCardHolderName;
    private String mExpiry;
    private String mCardRecarga;
    private int mStartPage = 0;
    private CardFragmentAdapterRecarga mCardAdapter;
    //
    Config config = new Config();
    private EditText valor_recarga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t_activity_card_recargar);

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valor="";
               valor= valor_recarga.getText().toString().trim();
                Toast.makeText(CardGuardadaActivity.this, "recarga: "+valor, Toast.LENGTH_SHORT).show();

            }
        });
        findViewById(R.id.previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CerrarSesionTarjeta  cerrarSesionTarjeta =new CerrarSesionTarjeta();
                cerrarSesionTarjeta.logout(CardGuardadaActivity.this);

            }
        });

        mCreditCardView = (CreditCardView) findViewById(R.id.credit_card_view);
        Bundle args = savedInstanceState != null ? savedInstanceState : getIntent().getExtras();

        loadPager(args);
        checkParams(args);
        carga();
        iniciarValores();
    }

    private void iniciarValores() {
        valor_recarga=(EditText)findViewById(R.id.edit_recarga);
    }

    private void carga() {
        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_TARJETA, Context.MODE_PRIVATE);
        mCVV = sharedPreferences.getString(config.TARJETA_CVV, "No Disponible");
        mCardHolderName = sharedPreferences.getString(config.TARJETA_NOMBRE, "No Disponible");
        mExpiry = sharedPreferences.getString(config.TARJETA_EXPIRA, "No Disponible");
        mCardNumber = sharedPreferences.getString(config.TARJETA_NUMERO, "No Disponible");
        mStartPage=1;

        mCreditCardView.setCVV(mCVV);
        mCreditCardView.setCardHolderName(mCardHolderName);
        mCreditCardView.setCardExpiry(mExpiry);
        mCreditCardView.setCardNumber(mCardNumber);
    }

    private void checkParams(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        carga();

        mCreditCardView.setCVV(mCVV);
        mCreditCardView.setCardHolderName(mCardHolderName);
        mCreditCardView.setCardExpiry(mExpiry);
        mCreditCardView.setCardNumber(mCardNumber);
        mCreditCardView.setCardNumber(mCardNumber);

        int cardSide = bundle.getInt(CreditCardUtils.EXTRA_CARD_SHOW_CARD_SIDE, CreditCardUtils.CARD_SIDE_FRONT);
        if (cardSide == CreditCardUtils.CARD_SIDE_BACK) {
            mCreditCardView.showBack();
        }
        if (mStartPage > 0 && mStartPage <= CARD_NAME_PAGE) {
            getViewPager().setCurrentItem(mStartPage);
        }
    }

    ViewPager getViewPager() {
        return (ViewPager) findViewById(R.id.card_field_container_pager);
    }

    public void loadPager(Bundle bundle) {
        ViewPager pager = getViewPager();
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                mCardAdapter.focus(position);

                if ((mCreditCardView.getCardType() != CreditCardUtils.CardType.AMEX_CARD) && (position == 2)) {
                    mCreditCardView.showBack();
                } else if (((position == 1)) && (mCreditCardView.getCardType() != CreditCardUtils.CardType.AMEX_CARD)) {
                    mCreditCardView.showFront();
                }

                mLastPageSelected = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        pager.setOffscreenPageLimit(1);

        mCardAdapter = new CardFragmentAdapterRecarga(getSupportFragmentManager(), bundle);
        mCardAdapter.setOnCardEntryCompleteListener(new ICardEntryCompleteListener() {
            @Override
            public void onCardEntryComplete(int currentIndex) {

            }

            @Override
            public void onCardEntryEdit(int currentIndex, String entryValue) {
                switch (currentIndex) {
                    case 0:
                        mCardRecarga = entryValue;
                        mCreditCardView.setCardNumber(mCardRecarga);
                        break;
                }
            }
        });

        pager.setAdapter(mCardAdapter);
    }


    @Override
    public void onBackPressed() {
        IraActividades iraActividades=new IraActividades();
        iraActividades.iraCuenta(this);
    }
}
