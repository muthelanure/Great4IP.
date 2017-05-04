package com.example.a6sigma.great4ip;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.a6sigma.great4ip.Model.ScheduleModel;
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
    private List<String> mList;

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

        mList = new ArrayList<>();
        mReference.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot idSnapshot : dataSnapshot.getChildren()){
                    ScheduleModel scheduleModel = idSnapshot.getValue(ScheduleModel.class);
                    mList.add(scheduleModel.getClassRoom()+" "+scheduleModel.getSchedule_id());

                    System.out.println("Kelas: "+scheduleModel.getClassRoom());
                }

                mSpinnerShare = (Spinner) findViewById(R.id.spinnerShare);
                ArrayAdapter<String> adapterShare = new ArrayAdapter<String>(AddReminderActivity.this, R.layout.spinner_reminder, mList);
                adapterShare.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerShare.setAdapter(adapterShare);
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

        mButtonSave = (TextView) findViewById(R.id.button_save);
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
