package com.ing_sebasparra.lector.TarjetaCredito;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.IraActividades;
import com.ing_sebasparra.lector.TarjetaCredito.pager.CardFragmentAdapter;
import com.ing_sebasparra.lector.TarjetaCredito.pager.CardFragmentAdapter.ICardEntryCompleteListener;

import static com.ing_sebasparra.lector.TarjetaCredito.CreditCardUtils.CARD_NAME_PAGE;
import static com.ing_sebasparra.lector.TarjetaCredito.CreditCardUtils.EXTRA_CARD_CVV;
import static com.ing_sebasparra.lector.TarjetaCredito.CreditCardUtils.EXTRA_CARD_EXPIRY;
import static com.ing_sebasparra.lector.TarjetaCredito.CreditCardUtils.EXTRA_CARD_HOLDER_NAME;
import static com.ing_sebasparra.lector.TarjetaCredito.CreditCardUtils.EXTRA_CARD_NUMBER;
import static com.ing_sebasparra.lector.TarjetaCredito.CreditCardUtils.EXTRA_ENTRY_START_PAGE;

public class CardEditActivity extends AppCompatActivity {


    int mLastPageSelected = 0;
    private CreditCardView mCreditCardView;

    private String mCardNumber;
    private String mCVV;
    private String mCardHolderName;
    private String mExpiry;
    private int mStartPage = 0;
    private CardFragmentAdapter mCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t_activity_card_edit);

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewPager pager = (ViewPager) findViewById(R.id.card_field_container_pager);
                int max = pager.getAdapter().getCount();
                if (pager.getCurrentItem() == max - 1) {
                    // if last card.
                    onDoneTapped();
                } else {
                    showNext();
                }
            }
        });
        findViewById(R.id.previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPrevious();
            }
        });

        setKeyboardVisibility(true);
        mCreditCardView = (CreditCardView) findViewById(R.id.credit_card_view);
        Bundle args = savedInstanceState != null ? savedInstanceState : getIntent().getExtras();

        loadPager(args);
        checkParams(args);
    }

    private void checkParams(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        mCardHolderName = bundle.getString(EXTRA_CARD_HOLDER_NAME);
        mCVV = bundle.getString(EXTRA_CARD_CVV);
        mExpiry = bundle.getString(EXTRA_CARD_EXPIRY);
        mCardNumber = bundle.getString(EXTRA_CARD_NUMBER);
        mStartPage = bundle.getInt(EXTRA_ENTRY_START_PAGE);

        final int maxCvvLength = CardSelector.selectCard(mCardNumber).getCvvLength();
        if (mCVV != null && mCVV.length() > maxCvvLength) {
            mCVV = mCVV.substring(0, maxCvvLength);
        }

        mCreditCardView.setCVV(mCVV);
        mCreditCardView.setCardHolderName(mCardHolderName);
        mCreditCardView.setCardExpiry(mExpiry);
        mCreditCardView.setCardNumber(mCardNumber);

        if (mCardAdapter != null) {
            mCreditCardView.post(new Runnable() {
                @Override
                public void run() {
                    mCardAdapter.setMaxCVV(maxCvvLength);
                    mCardAdapter.notifyDataSetChanged();
                }
            });
        }

        int cardSide = bundle.getInt(CreditCardUtils.EXTRA_CARD_SHOW_CARD_SIDE, CreditCardUtils.CARD_SIDE_FRONT);
        if (cardSide == CreditCardUtils.CARD_SIDE_BACK) {
            mCreditCardView.showBack();
        }
        if (mStartPage > 0 && mStartPage <= CARD_NAME_PAGE) {
            getViewPager().setCurrentItem(mStartPage);
        }
    }

    public void refreshNextButton() {
        ViewPager pager = (ViewPager) findViewById(R.id.card_field_container_pager);

        int max = pager.getAdapter().getCount();

        int text = R.string.next;

        if (pager.getCurrentItem() == max - 1) {
            text = R.string.done;
        }

        ((TextView) findViewById(R.id.next)).setText(text);
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
                } else if (((position == 1) || (position == 3)) && (mLastPageSelected == 2) && (mCreditCardView.getCardType() != CreditCardUtils.CardType.AMEX_CARD)) {
                    mCreditCardView.showFront();
                }

                mLastPageSelected = position;

                refreshNextButton();

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        pager.setOffscreenPageLimit(4);

        mCardAdapter = new CardFragmentAdapter(getSupportFragmentManager(), bundle);
        mCardAdapter.setOnCardEntryCompleteListener(new ICardEntryCompleteListener() {
            @Override
            public void onCardEntryComplete(int currentIndex) {
                showNext();
            }

            @Override
            public void onCardEntryEdit(int currentIndex, String entryValue) {
                switch (currentIndex) {
                    case 0:
                        mCardNumber = entryValue.replace(CreditCardUtils.SPACE_SEPERATOR, "");
                        mCreditCardView.setCardNumber(mCardNumber);
                        if (mCardAdapter != null) {
                            mCardAdapter.setMaxCVV(CardSelector.selectCard(mCardNumber).getCvvLength());
                        }
                        break;
                    case 1:
                        mExpiry = entryValue;
                        mCreditCardView.setCardExpiry(entryValue);
                        break;
                    case 2:
                        mCVV = entryValue;
                        mCreditCardView.setCVV(entryValue);
                        break;
                    case 3:
                        mCardHolderName = entryValue;
                        mCreditCardView.setCardHolderName(entryValue);
                        break;
                }
            }
        });

        pager.setAdapter(mCardAdapter);
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString(EXTRA_CARD_CVV, mCVV);
        outState.putString(EXTRA_CARD_HOLDER_NAME, mCardHolderName);
        outState.putString(EXTRA_CARD_EXPIRY, mExpiry);
        outState.putString(EXTRA_CARD_NUMBER, mCardNumber);

        super.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        checkParams(inState);
    }


    public void showPrevious() {
        final ViewPager pager = (ViewPager) findViewById(R.id.card_field_container_pager);
        int currentIndex = pager.getCurrentItem();

        if (currentIndex == 0) {
           /* setResult(RESULT_CANCELED);
            finish();*/
           // Toast.makeText(this, "No puede ir mas atras", Toast.LENGTH_SHORT).show();
            IraActividades iraActividades=new IraActividades();
            iraActividades.iraCuenta(this);
        }

        if (currentIndex - 1 >= 0) {
            pager.setCurrentItem(currentIndex - 1);
        }

        refreshNextButton();
    }

    public void showNext() {
        final ViewPager pager = (ViewPager) findViewById(R.id.card_field_container_pager);
        CardFragmentAdapter adapter = (CardFragmentAdapter) pager.getAdapter();

        int max = adapter.getCount();
        int currentIndex = pager.getCurrentItem();

        if (currentIndex + 1 < max) {

            pager.setCurrentItem(currentIndex + 1);
        } else {
            // completed the card entry.
            setKeyboardVisibility(false);
        }

        refreshNextButton();
    }

    //CUANDOA ACABA
    private void onDoneTapped() {
   /*     Intent intent = new Intent();

        intent.putExtra(EXTRA_CARD_CVV, mCVV);
        intent.putExtra(EXTRA_CARD_HOLDER_NAME, mCardHolderName);
        intent.putExtra(EXTRA_CARD_EXPIRY, mExpiry);
        intent.putExtra(EXTRA_CARD_NUMBER, mCardNumber);

        setResult(RESULT_OK, intent);
        finish();*/

       /*boolean isUpdate;
        Bundle bundle= getIntent().getExtras();
        if (bundle!= null) {// to avoid the NullPointerException
            isUpdate=bundle.getBoolean("update");
            if(isUpdate)
            {
                Toast.makeText(this, "si pasa: "+mExpiry, Toast.LENGTH_SHORT).show();

            }else{

                Toast.makeText(this, "NOOO pasa: "+mExpiry, Toast.LENGTH_SHORT).show();
            }
        }*/



        if (mCardNumber.length() <= 15) {

            Toast.makeText(this, "Por favor verifique el Número de tarjeta", Toast.LENGTH_SHORT).show();

        } else if (mCardHolderName.length() <= 6) {

            Toast.makeText(this, "Por favor verifique su Nombre", Toast.LENGTH_SHORT).show();
        } else if (mCVV.length() <= 2) {
            Toast.makeText(this, "Por favor verifique el Número cvv", Toast.LENGTH_SHORT).show();

        } else {
            Intent intent = new Intent();

            intent.putExtra(EXTRA_CARD_CVV, mCVV);
            intent.putExtra(EXTRA_CARD_HOLDER_NAME, mCardHolderName);
            intent.putExtra(EXTRA_CARD_EXPIRY, mExpiry);
            intent.putExtra(EXTRA_CARD_NUMBER, mCardNumber);

            Toast.makeText(this, "Finalizo y guarda todo", Toast.LENGTH_SHORT).show();
        }


    }

    // from the link above
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {

            RelativeLayout parent = (RelativeLayout) findViewById(R.id.parent);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) parent.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
            parent.setLayoutParams(layoutParams);

        }
    }

    private void setKeyboardVisibility(boolean visible) {
        final EditText editText = (EditText) findViewById(R.id.card_number_field);

        if (!visible) {

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } else {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
       // this.finish();
        IraActividades iraActividades=new IraActividades();
        iraActividades.iraCuenta(this);
    }
}
