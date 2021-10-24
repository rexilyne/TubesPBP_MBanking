package com.example.tubespbp_mbanking.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import com.example.tubespbp_mbanking.databinding.PinDialogBinding;
import com.example.tubespbp_mbanking.model.Pin;

public class PinDialog extends Dialog{

    private PinDialogBinding binding;
    private Pin pin;
    private String userPin;
    private String confirmedPin;
    private PinDialogListener pinDialogListener;
    private EditText digit1, digit2, digit3, digit4, digit5, digit6;

    public PinDialog(@NonNull Context context, String userPin) {
        super(context);
        this.setUserPin(userPin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = PinDialogBinding.inflate(LayoutInflater.from(getContext()));
        setContentView(binding.getRoot());

        pin = new Pin();
        binding.setPin(pin);
        binding.setDialog(this);

        digit1 = binding.digit1.getEditText();
        digit2 = binding.digit2.getEditText();
        digit3 = binding.digit3.getEditText();
        digit4 = binding.digit4.getEditText();
        digit5 = binding.digit5.getEditText();
        digit6 = binding.digit6.getEditText();

        digit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(digit1.getText().toString().length() == 1) {
                    digit2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        digit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(digit2.getText().toString().isEmpty()) {
                    digit1.requestFocus();
                } else {
                    digit3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        digit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(digit3.getText().toString().isEmpty()) {
                    digit2.requestFocus();
                } else {
                    digit4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        digit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(digit4.getText().toString().isEmpty()) {
                    digit3.requestFocus();
                } else {
                    digit5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        digit5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(digit5.getText().toString().isEmpty()) {
                    digit4.requestFocus();
                } else {
                    digit6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        digit6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(digit6.getText().toString().isEmpty()) {
                    digit5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        digit2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_DEL) {
                    if(digit2.getText().toString().isEmpty()) {
                        digit1.requestFocus();
                    }
                }
                return false;
            }
        });

        digit3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_DEL) {
                    if(digit3.getText().toString().isEmpty()) {
                        digit2.requestFocus();
                    }
                }
                return false;
            }
        });

        digit4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_DEL) {
                    if(digit4.getText().toString().isEmpty()) {
                        digit3.requestFocus();
                    }
                }
                return false;
            }
        });

        digit5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_DEL) {
                    if(digit5.getText().toString().isEmpty()) {
                        digit4.requestFocus();
                    }
                }
                return false;
            }
        });

        digit6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_DEL) {
                    if(digit6.getText().toString().isEmpty()) {
                        digit5.requestFocus();
                    }
                }
                return false;
            }
        });
    }

    public String getConfirmedPin() {
        return confirmedPin;
    }

    public void setConfirmedPin(String confirmedPin) {
        this.confirmedPin = confirmedPin;
    }

    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }

    public View.OnClickListener btnBatal = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    };

    public View.OnClickListener btnOk = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(binding.digit1.getEditText().getText().toString().isEmpty()) {
                Toast.makeText(PinDialog.super.getContext(), "Pin harus lengkap", Toast.LENGTH_SHORT).show();
            } else if(binding.digit2.getEditText().getText().toString().isEmpty()) {
                Toast.makeText(PinDialog.super.getContext(), "Pin harus lengkap", Toast.LENGTH_SHORT).show();
            } else if(binding.digit3.getEditText().getText().toString().isEmpty()) {
                Toast.makeText(PinDialog.super.getContext(), "Pin harus lengkap", Toast.LENGTH_SHORT).show();
            } else if(binding.digit4.getEditText().getText().toString().isEmpty()) {
                Toast.makeText(PinDialog.super.getContext(), "Pin harus lengkap", Toast.LENGTH_SHORT).show();
            } else if(binding.digit5.getEditText().getText().toString().isEmpty()) {
                Toast.makeText(PinDialog.super.getContext(), "Pin harus lengkap", Toast.LENGTH_SHORT).show();
            } else if(binding.digit6.getEditText().getText().toString().isEmpty()) {
                Toast.makeText(PinDialog.super.getContext(), "Pin harus lengkap", Toast.LENGTH_SHORT).show();
            } else {
                setConfirmedPin(binding.getPin().getAssembledPin());
                if(!isNumeric(confirmedPin)) {
                    Toast.makeText(PinDialog.super.getContext(), "Pin harus berupa angka", Toast.LENGTH_SHORT).show();
                } else if(!confirmedPin.equals(userPin)) {
                    Toast.makeText(PinDialog.super.getContext(), "Pin salah", Toast.LENGTH_SHORT).show();
                } else {
                    pinDialogListener.pinConfirmed(confirmedPin);
                    dismiss();
                }
            }
        }
    };

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public PinDialogListener getPinDialogListener() {
        return pinDialogListener;
    }

    public void setPinDialogListener(PinDialogListener pinDialogListener) {
        this.pinDialogListener = pinDialogListener;
    }
}
