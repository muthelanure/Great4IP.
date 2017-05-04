package com.example.a6sigma.great4ip.FragmentMenu;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a6sigma.great4ip.AddReminderActivity;
import com.example.a6sigma.great4ip.R;
import com.google.firebase.auth.FirebaseAuth;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.CalendarWeekDayFormatter;

import java.util.Calendar;

/**
 * Created by Acer on 20/04/2017.
 */

public class ReminderActivity extends Fragment{
    private MaterialCalendarView mCalendarView;
    private FloatingActionButton mFab;
    private TextView mDateText;

    private String mDayName, mDay, mMonth, mYear;
    private CalendarWeekDayFormatter mDayFormatter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_reminder, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Reminder");

        mCalendarView = (MaterialCalendarView) view.findViewById(R.id.datePicker);
        mCalendarView.setSelectedDate(CalendarDay.today());

        mDateText = (TextView) view.findViewById(R.id.dateText);
        mDayFormatter = new CalendarWeekDayFormatter();

        mDayName = mDayFormatter.format(mCalendarView.getSelectedDate().getDay()).toString();
        mDay = String.valueOf(mCalendarView.getSelectedDate().getDay());
        mMonth = String.valueOf(mCalendarView.getSelectedDate().getMonth());
        mYear = String.valueOf(mCalendarView.getSelectedDate().getYear());

        defineMonth(mMonth);
        mDateText.setText(mDayName+" "+mMonth+" "+mDay+", "+mYear);

        mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                mDayName = mDayFormatter.format(date.getDay()).toString();
                mDay = String.valueOf(date.getDay());
                mMonth = String.valueOf(date.getMonth());
                mYear = String.valueOf(date.getYear());

                defineMonth(mMonth);
                mDateText.setText(mDayName+" "+mMonth+" "+mDay+", "+mYear);
            }
        });

        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddReminderActivity.class);
                startActivity(intent);
            }
        });
    }

    public void defineMonth(String monthName){
        int month = Integer.parseInt(monthName);
        switch (month){
            case 1:
                mMonth = "January";
                break;
            case 2:
                mMonth = "February";
                break;
            case 3:
                mMonth = "March";
                break;
            case 4:
                mMonth = "April";
                break;
            case 5:
                mMonth = "May";
                break;
            case 6:
                mMonth = "June";
                break;
            case 7:
                mMonth = "July";
                break;
            case 8:
                mMonth = "August";
                break;
            case 9:
                mMonth = "September";
                break;
            case 10:
                mMonth = "October";
                break;
            case 11:
                mMonth = "November";
                break;
            case 12:
                mMonth = "Desember";
                break;
            default:
                break;
        }
    }
}
