package com.example.a6sigma.great4ip;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.lang.UCharacterEnums;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.a6sigma.great4ip.FragmentMenu.ReminderActivity;
import com.example.a6sigma.great4ip.Model.CourseModel;
import com.example.a6sigma.great4ip.Model.ReminderModel;
import com.example.a6sigma.great4ip.Model.ScheduleModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.wdullaer.materialdatetimepicker.date.MonthAdapter;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Acer on 20/04/2017.
 */

public class AddReminderActivity extends AppCompatActivity {
    private Spinner mSpinnerReminder;
    private Spinner mSpinnerShare;
    private EditText mTextTime;

    private EditText mEvent;
    private EditText mLocation;
    private EditText mNotes;

    private RelativeLayout mRelativeLayout;
    private ImageButton mButtonTime;
    private PopupWindow mPopupWindow;

    private Calendar mCalendar;
    private int mHour;
    private int mMinute;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private DatabaseReference mReferenceCourse;

    private List<String> mListID;
    private List<String> mListCourse;
    private String courseName;

    private ProgressDialog mProgressDialog;

    private TextView mButtonCancel;
    private TextView mButtonSave;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("tb_studentSchedule");
        mReferenceCourse = mDatabase.getReference("tb_reminder");

        mRelativeLayout = (RelativeLayout) findViewById(R.id.layout);
        mButtonTime = (ImageButton) findViewById(R.id.buttonTime);
        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                final View timeView = inflater.inflate(R.layout.popup_timepicker, null);
                mPopupWindow = new PopupWindow(timeView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

                TimePicker timePicker = (TimePicker) timeView.findViewById(R.id.timePicker);
                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;
                        mTextTime.setText(mHour+" : "+mMinute);
                    }
                });
                Button buttonOk = (Button) timeView.findViewById(R.id.buttonOk);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopupWindow.dismiss();
                    }
                });
                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
            }
        });

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mTextTime = (EditText) findViewById(R.id.editTime);
        mTextTime.setText(mHour+" : "+mMinute);

        mSpinnerReminder = (Spinner) findViewById(R.id.spinnerTIme);
        ArrayAdapter<CharSequence> adapterReminder = ArrayAdapter.createFromResource(AddReminderActivity.this, R.array.reminder, R.layout.spinner_reminder);
        adapterReminder.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerReminder.setAdapter(adapterReminder);

        mEvent = (EditText) findViewById(R.id.textEvent);
        mLocation = (EditText) findViewById(R.id.textLocation);
        mNotes = (EditText) findViewById(R.id.textNote);

        mButtonSave = (TextView) findViewById(R.id.button_save);

        mListID = new ArrayList<>();
        mListCourse = new ArrayList<>();
        mReference.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot idSnapshot : dataSnapshot.getChildren()){
                    ScheduleModel scheduleModel = idSnapshot.getValue(ScheduleModel.class);

                    mListID.add(scheduleModel.getClassRoom()+" "+scheduleModel.getCourse_id());
                    mListCourse.add(scheduleModel.getSchedule_id());
                }

                mSpinnerShare = (Spinner) findViewById(R.id.spinnerShare);
                ArrayAdapter<String> adapterShare = new ArrayAdapter<String>(AddReminderActivity.this, R.layout.spinner_reminder, mListID);
                adapterShare.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerShare.setAdapter(adapterShare);

                mSpinnerShare.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                        mButtonSave.setOnClickListener(new View.OnClickListener() {
//                            mProgressDialog.setMessage("Saving...");
//                            mProgressDialog.show();

                            String event = mEvent.getText().toString();
                            String location = mLocation.getText().toString();
                            final String reminder_id = mReferenceCourse.push().getKey();
                            String reminder_time = mSpinnerReminder.getSelectedItem().toString();
                            String share = mSpinnerShare.getSelectedItem().toString();
                            String time = mTextTime.getText().toString();

                            final ReminderModel reminderModel = new ReminderModel(event, location, reminder_id, reminder_time, share, time);

                            @Override
                            public void onClick(View v) {
                                mReferenceCourse.child(mListCourse.get(position)).child(reminderModel.getReminder_id()).setValue(reminderModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
//                                        mProgressDialog.dismiss();
                                        Toast.makeText(AddReminderActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(AddReminderActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
//                                        mProgressDialog.dismiss();
                                        Toast.makeText(AddReminderActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mButtonCancel = (TextView) findViewById(R.id.button_cancel);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
