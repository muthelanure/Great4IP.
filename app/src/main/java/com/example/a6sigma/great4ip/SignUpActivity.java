package com.example.a6sigma.great4ip;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a6sigma.great4ip.Model.SignUpModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by lenovo on 07/03/2017.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mSignUp;

    private ProgressDialog mProgressDialog;
    private FirebaseAuth mFirebaseAuth;

    private SignUpModel mSignUpModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);

        mEmail = (EditText) findViewById(R.id.editTextEmail);
        mPassword = (EditText) findViewById(R.id.editTextPassword);
        mConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        mSignUp = (Button) findViewById(R.id.buttonSignUp);

        mSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String confirmPassword = mConfirmPassword.getText().toString().trim();

        mSignUpModel = new SignUpModel(email, password, confirmPassword);
        if(v == mSignUp){
            System.out.println(mSignUpModel.getEmail());
            if(validateUser(mSignUpModel)){
                registerUser(mSignUpModel);
            } else {
                reset();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private boolean validateUser(SignUpModel signUpModel){
        boolean validate = false;
        CharSequence targetEmail = signUpModel.getEmail();

        if(TextUtils.isEmpty(signUpModel.getEmail()) && TextUtils.isEmpty(signUpModel.getPassword())){
            Toast.makeText(this, "Please input your email and password", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(targetEmail).matches()){
            Toast.makeText(this, "Please type your email correctly", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(signUpModel.getEmail())){
            Toast.makeText(this, "Please input your email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(signUpModel.getPassword())){
            Toast.makeText(this, "Please input your password", Toast.LENGTH_SHORT).show();
        } else if(!signUpModel.getConfirmPassword().equals(signUpModel.getPassword())){
            Toast.makeText(this, "Please input the right confirmation password", Toast.LENGTH_SHORT).show();
        } else if (signUpModel.getPassword().length()<10){
            Toast.makeText(this, "Password should be longer than 9 characters", Toast.LENGTH_SHORT).show();
        } else {
            validate = true;
        }

        return validate;
    }

    private void registerUser(SignUpModel signUpModel){
        mProgressDialog.setMessage("Registering User...");
        mProgressDialog.show();

        mFirebaseAuth.createUserWithEmailAndPassword(signUpModel.getEmail(), signUpModel.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressDialog.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            Intent intent = new Intent(SignUpActivity.this, SignUpFormActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Could not register... please try again", Toast.LENGTH_SHORT).show();
                            reset();
                        }
                    }
                });
    }

    public void reset(){
        mEmail.setText("");
        mPassword.setText("");
        mConfirmPassword.setText("");
    }

}
