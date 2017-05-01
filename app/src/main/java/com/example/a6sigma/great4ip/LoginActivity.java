package com.example.a6sigma.great4ip;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a6sigma.great4ip.Model.LoginModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin;
    private Button mSignUp;
    private TextView mResetPassword;
    private Intent mIntent;

    private ProgressDialog mProgressDialog;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (EditText) findViewById(R.id.editTextEmailLogin);
        mPassword = (EditText) findViewById(R.id.editTextPasswordLogin);
        mLogin = (Button) findViewById(R.id.buttonLogin);
        mResetPassword = (TextView) findViewById(R.id.forgotPassword);
        mSignUp = (Button) findViewById(R.id.Sign_Up);

        mProgressDialog = new ProgressDialog(this);
        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    String uid = user.getUid();
                    mIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    mIntent.putExtra("uid", uid);
                    startActivity(mIntent);
                }
            }
        };

        mLogin.setOnClickListener(this);
        mResetPassword.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener!=null){
            finish();
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        LoginModel loginModel = new LoginModel(email, password);
        if (v == mSignUp){
            mIntent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(mIntent);
        } else if (v == mLogin){
            if(validateUser(loginModel)){
                userLogin(loginModel);
            } else {
                reset();
            }
        } else if (v == mResetPassword){
            mIntent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
            startActivity(mIntent);
        }
    }

    private boolean validateUser(LoginModel loginModel){
        boolean validate = false;
        CharSequence targetEmail = loginModel.getEmail();

        if(TextUtils.isEmpty(loginModel.getEmail()) && TextUtils.isEmpty(loginModel.getPassword())){
            Toast.makeText(this, "Please input your email and password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(loginModel.getEmail())){
            Toast.makeText(this, "Please input your email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(loginModel.getPassword())){
            Toast.makeText(this, "Please input your password", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(targetEmail).matches()){
            Toast.makeText(this, "Please type your email correctly", Toast.LENGTH_SHORT).show();
        }else {
            validate = true;
        }
        return validate;
    }

    private void userLogin(LoginModel loginModel){
        mProgressDialog.setMessage("Signing in...");
        mProgressDialog.show();

        mFirebaseAuth.signInWithEmailAndPassword(loginModel.getEmail(), loginModel.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Your Email or password is incorrect", Toast.LENGTH_SHORT).show();
                            reset();
                        }
                    }
                });
    }

    public void reset(){
        mEmail.setText("");
        mPassword.setText("");
    }
}
