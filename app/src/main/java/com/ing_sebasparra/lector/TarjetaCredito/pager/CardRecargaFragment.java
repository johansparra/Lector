package com.ing_sebasparra.lector.TarjetaCredito.pager;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ing_sebasparra.lector.R;


public class CardRecargaFragment extends CreditCardFragment {

    EditText mCardNumberView;

    public CardRecargaFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle state) {
        View v = inflater.inflate(R.layout.t_lyt_card_recarga, group, false);
        mCardNumberView = (EditText) v.findViewById(R.id.card_cvv);

        String number = "";

      /*  if (getArguments() != null && getArguments().containsKey(EXTRA_CARD_NUMBER)) {
            number = getArguments().getString(EXTRA_CARD_NUMBER);
        }*/

        if (number == null) {
            number = "";
        }

        mCardNumberView.setText(number);
        mCardNumberView.addTextChangedListener(this);

        return v;
    }


    @Override
    public void afterTextChanged(Editable s) {
/*        int cursorPosition = mCardNumberView.getSelectionEnd();
        int previousLength = mCardNumberView.getText().length();

        String cardNumber = CreditCardUtils.handleCardNumber(s.toString());
        int modifiedLength = cardNumber.length();

        mCardNumberView.removeTextChangedListener(this);
        mCardNumberView.setText(cardNumber);
        String rawCardNumber = cardNumber.replace(CreditCardUtils.SPACE_SEPERATOR, "");
        CreditCardUtils.CardType cardType = CreditCardUtils.selectCardType(rawCardNumber);
        int maxLengthWithSpaces = ((cardType == CreditCardUtils.CardType.AMEX_CARD) ? CARD_NUMBER_FORMAT_AMEX : CARD_NUMBER_FORMAT).length();
        mCardNumberView.setSelection(cardNumber.length() > maxLengthWithSpaces ? maxLengthWithSpaces : cardNumber.length());
        mCardNumberView.addTextChangedListener(this);

        if (modifiedLength <= previousLength && cursorPosition < modifiedLength) {
            mCardNumberView.setSelection(cursorPosition);
        }

        onEdit(cardNumber);

        if (rawCardNumber.length() == CreditCardUtils.selectCardLength(cardType)) {
           // onComplete();
        }*/
    }

    @Override
    public void focus() {
        if (isAdded()) {
            mCardNumberView.selectAll();
        }
    }
}
