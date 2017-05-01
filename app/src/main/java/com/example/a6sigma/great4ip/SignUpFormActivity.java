package com.example.a6sigma.great4ip;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a6sigma.great4ip.Model.SignUpFormModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by lenovo on 13/04/2017.
 */

public class SignUpFormActivity extends AppCompatActivity {
    private EditText mName, mNim;
    private Spinner mFaculty, mMajor, mClass;
    private Button mSignUp;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fromsignup);

        mName = (EditText) findViewById(R.id.textName);
        mNim = (EditText) findViewById(R.id.textNim);

        mFaculty = (Spinner) findViewById(R.id.spinnerFaculty);
        mMajor = (Spinner) findViewById(R.id.spinnerMajor);
        mClass = (Spinner) findViewById(R.id.spinnerClass);

        mSignUp = (Button) findViewById(R.id.buttonSignUpForm);
        mProgressDialog = new ProgressDialog(this);

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("tb_user");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setMajor(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setClass(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_user = mUser.getUid();
                String name = mName.getText().toString();
                String nim = mNim.getText().toString();
                String classRoom = mClass.getSelectedItem().toString();
                String major = mMajor.getSelectedItem().toString();
                String faculty = mFaculty.getSelectedItem().toString();
                String role = "student";

                SignUpFormModel signUpFormModel = new SignUpFormModel(classRoom,faculty,id_user,major,name,nim,role);
                addUserDetail(signUpFormModel);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    public void addUserDetail(SignUpFormModel signUpFormModel){
        mProgressDialog.setMessage("Saving...");
        mProgressDialog.show();

        mReference.child(signUpFormModel.getId_user()).setValue(signUpFormModel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                mProgressDialog.dismiss();
                if(databaseError != null){
                    Toast.makeText(SignUpFormActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                    Intent intent = new Intent(SignUpFormActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void setMajor(int position){
        int arrayMajor = showMajor(position);

        ArrayAdapter<CharSequence> mMajorAdapter = ArrayAdapter.createFromResource(SignUpFormActivity.this, arrayMajor, android.R.layout.simple_spinner_item);
        mMajorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMajor.setAdapter(mMajorAdapter);
    }

    public int showMajor(int faculty){
        int array = 0;
        switch (faculty){
            case 0:
                array = R.array.majorIE;
                break;

            case 1:
                array = R.array.majorEE;
                break;

            case 2:
                array = R.array.majorC;
                break;
            default:
        }
        return array;
    }

    public void setClass(int position){
        int arrayClass = showClass(position);

        ArrayAdapter<CharSequence> mClassAdapter = ArrayAdapter.createFromResource(SignUpFormActivity.this, arrayClass, android.R.layout.simple_spinner_item);
        mClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mClass.setAdapter(mClassAdapter);
    }

    public int showClass(int major){
        int array = 0;
        switch (major){
            case 0:
                array = R.array.classIE;
                break;
            case 1:
                array = R.array.classIS;
                break;
            case 2:
                array = R.array.classEE;
                break;
            case 3:
                array = R.array.classEECS;
                break;
            case 4:
                array = R.array.classTE;
                break;
            case 5:
                array = R.array.classPE;
                break;
            case 6:
                array = R.array.classCCS;
                break;
            case 7:
                array = R.array.classIF;
                break;
            default:
        }
        return array;
    }
}