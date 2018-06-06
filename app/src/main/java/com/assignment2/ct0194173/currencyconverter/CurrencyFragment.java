package com.assignment2.ct0194173.currencyconverter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CurrencyFragment extends Fragment {
    private ImageView img1,img2;
    private TextView tv1,tv2,tv3;
    private int pos1,pos2;
    private EditText edit;
    private RelativeLayout layout;

    public CurrencyFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (inflater.inflate(R.layout.currency_fragment,container,false));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner sp1 = view.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(), R.array.currency_symbols, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter1);
        Spinner sp2 = view.findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.currency_symbols, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter2);
        img1 = view.findViewById(R.id.c_f_flag);
        tv1 = view.findViewById(R.id.c_f_text);
        tv2 = view.findViewById(R.id.c_f_text2);
        tv3 = view.findViewById(R.id.txt_result);
        layout = view.findViewById(R.id.currency_fragment_layout);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] currency_names = getResources().getStringArray(R.array.currency_names);
                String str = currency_names[position];
                img1.setImageResource(MyFlags.getFlags()[position]);
                tv1.setText(str);
                tv3.setText(" ");
                pos1 = position;
                if (edit.getText() != null) {
                    edit.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        img2 = view.findViewById(R.id.c_f_flag2);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] currency_names = getResources().getStringArray(R.array.currency_names);
                String str = currency_names[position];
                img2.setImageResource(MyFlags.getFlags()[position]);
                tv2.setText(str);
                tv3.setText(" ");
                pos2 = position;
                if (edit.getText() != null) {
                    edit.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edit = view.findViewById(R.id.edit);

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tv3.setText(" ");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Button btn = view.findViewById(R.id.convertButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DownloadTask.list.size() == 0) {
                    Snackbar snackbar = Snackbar.make(layout, "Check your internet connection", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    String amountString = edit.getText().toString();
                    if (amountString.equals("")) {
                        Snackbar.make(layout, "No input!", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Double x = DownloadTask.list.get(pos1);
                        Double y = DownloadTask.list.get(pos2);
                        Double amount = Double.valueOf(amountString);
                        Double z = (amount / x) * y;
                        String amountDoubleFormatted = new DecimalFormat("0.00").format(z);
                        Double price = Double.valueOf(amountDoubleFormatted);
                        tv3.setText(String.valueOf("$ " + BigDecimal.valueOf(price)));

                    }
                }

            }
        });
    }

}
