package com.example.lostandfoundpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    private EditText name,email,phone,password,confirmPassword;
    private Button registerBtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.reg_Name);
        email = findViewById(R.id.reg_Email);
        phone = findViewById(R.id.reg_Phone);
        password = findViewById(R.id.reg_Pass);
        confirmPassword = findViewById(R.id.regcon_pass);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        registerBtn = findViewById(R.id.btn_Reg);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ckeckInputValues();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ckeckInputValues();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ckeckInputValues();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ckeckInputValues();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ckeckInputValues();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().trim().matches(emailPattern)){
                    if (password.getText().toString().equals(confirmPassword.getText().toString())){
                        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            Map<Object,String> userData = new HashMap<>();
                                            userData.put("Name",name.getText().toString());
                                            userData.put("Phone",phone.getText().toString());
                                            firebaseFirestore.collection("Users")
                                                    .add(userData);
                                            Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                                            startActivity(intent);
                                        }else {
                                            String error = task.getException().getMessage();
                                            Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }else {
                        confirmPassword.setError("Password doesn't match");
                    }
                }else {
                    email.setError("Email not Valid");
                }
            }
        });

    }

    private void ckeckInputValues(){
        if (!TextUtils.isEmpty(name.getText())){
            if (!TextUtils.isEmpty(email.getText())){
                if (!TextUtils.isEmpty(phone.getText())){
                    if (!TextUtils.isEmpty(password.getText())){
                        if (!TextUtils.isEmpty(confirmPassword.getText())){
                            registerBtn.setEnabled(true);
                            registerBtn.setTextColor(Color.BLACK);
                        }else {
                            registerBtn.setEnabled(false);
                        }
                    }else {
                        registerBtn.setEnabled(false);
                    }
                }else {
                    registerBtn.setEnabled(false);
                }
            }else {
                registerBtn.setEnabled(false);
            }
        }else {
            registerBtn.setEnabled(false);
        }
    }
}