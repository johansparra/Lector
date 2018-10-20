package com.ing_sebasparra.lector.TarjetaCredito.pager;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.TarjetaCredito.CreditCardUtils;

import java.util.Calendar;

import static com.ing_sebasparra.lector.TarjetaCredito.CreditCardUtils.EXTRA_CARD_EXPIRY;
import static com.ing_sebasparra.lector.TarjetaCredito.CreditCardUtils.EXTRA_VALIDATE_EXPIRY_DATE;

/**
 * Created by sharish on 9/1/15.
 */
public class CardExpiryFragment extends CreditCardFragment {

    EditText cardExpiryView;

    private boolean mValidateCard = true;

    public CardExpiryFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle state) {

        View v = inflater.inflate(R.layout.t_lyt_card_expiry, group, false);
        cardExpiryView = (EditText) v.findViewById(R.id.card_expiry);

        String expiry = "";

        Bundle args = getArguments();



        if(args != null) {

            if(args.containsKey(EXTRA_CARD_EXPIRY)) {
                expiry = getArguments().getString(EXTRA_CARD_EXPIRY);
            }

            mValidateCard = args.getBoolean(EXTRA_VALIDATE_EXPIRY_DATE, true);

            /*SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_EXTRA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();*/
        }

        if(expiry == null) {
            expiry = "";
        }

        cardExpiryView.setText(expiry);

        cardExpiryView.addTextChangedListener(this);

        return v;
    }

    @Override
    public void afterTextChanged(Editable s) {

        String text = s.toString().replace(CreditCardUtils.SLASH_SEPERATOR, "");

        boolean verificar=false;

        String month, year="";
        if(text.length() >= 2) {
            month = text.substring(0, 2);

            if(text.length() > 2) {
                year = text.substring(2);
            }

            if(mValidateCard) {
                int mm = Integer.parseInt(month);

                if (mm <= 0 || mm >= 13) {
                    cardExpiryView.setError(getString(R.string.error_invalid_month));
                    Toast.makeText(getContext(), "no tiene que pasar", Toast.LENGTH_SHORT).show();
                    verificar= true;


                    return ;
                }

                if (text.length() >= 4) {

                    int yy = Integer.parseInt(year);

                    final Calendar calendar = Calendar.getInstance();
                    int currentYear = calendar.get(Calendar.YEAR);
                    int currentMonth = calendar.get(Calendar.MONTH) + 1;

                    int millenium = (currentYear / 1000) * 1000;


                    if (yy + millenium < currentYear) {
                        cardExpiryView.setError(getString(R.string.error_card_expired));
                        return;
                    } else if (yy + millenium == currentYear && mm < currentMonth) {
                        cardExpiryView.setError(getString(R.string.error_card_expired));
                        return;
                    }
                }
            }

        }
        else {
            month = text;
        }

        int previousLength = cardExpiryView.getText().length();
        int cursorPosition = cardExpiryView.getSelectionEnd();

        text = CreditCardUtils.handleExpiration(month,year);

        cardExpiryView.removeTextChangedListener(this);
        cardExpiryView.setText(text);
        cardExpiryView.setSelection(text.length());
        cardExpiryView.addTextChangedListener(this);

        int modifiedLength = text.length();

        if(modifiedLength <= previousLength && cursorPosition < modifiedLength) {
            cardExpiryView.setSelection(cursorPosition);
        }

        onEdit(text);

        if(text.length() == 5) {
          //  onComplete();
      /*      text.replace("/","");
            Toast.makeText(getContext(), "la fecha: "+text, Toast.LENGTH_SHORT).show();*/
        }

    }


    @Override
    public void focus() {

        if(isAdded()) {
            cardExpiryView.selectAll();
        }
    }


    public void onSaveInstanceState(Bundle outState) {

        outState.putBoolean(EXTRA_VALIDATE_EXPIRY_DATE, mValidateCard);
        super.onSaveInstanceState(outState);
    }

    public void onActivityCreated(Bundle instate) {

        if(instate != null) {
            mValidateCard = instate.getBoolean(EXTRA_VALIDATE_EXPIRY_DATE, mValidateCard);
        }

        super.onActivityCreated(instate);
    }
}
