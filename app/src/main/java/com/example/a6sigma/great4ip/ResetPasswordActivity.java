package com.example.a6sigma.great4ip;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a6sigma.great4ip.Model.ResetPasswordModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by lenovo on 09/03/2017.
 */

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail;
    private Button mReset;

    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);

        mEmail = (EditText) findViewById(R.id.editTextEmailResetPassword);
        mReset = (Button) findViewById(R.id.buttonReset);

        mReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String email = mEmail.getText().toString().trim();

        ResetPasswordModel resetPasswordModel = new ResetPasswordModel(email);
        if(v == mReset){
            resetUser(resetPasswordModel);
        }
    }

    private void resetUser(ResetPasswordModel resetPasswordModel){
        mProgressDialog.setMessage("Sending message...");
        mProgressDialog.show();

        mFirebaseAuth.sendPasswordResetEmail(resetPasswordModel.getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mProgressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            Toast.makeText(ResetPasswordActivity.this, "Sending successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, "Sending failed... Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
