package com.example.a6sigma.great4ip.FragmentMenu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.a6sigma.great4ip.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Acer on 20/04/2017.
 */

public class ScheduleActivity extends Fragment {
    protected ListView lv;
    protected ListAdapter adapter;
    SimpleAdapter Adapter;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> mylist;
    String[] Pil;
    String[] Ltn;
    String[] Gbr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Schedule");

        lv = (ListView) view.findViewById(R.id.lv);

        Pil = new String[] {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY","FRIDAY","SATURDAY",  "SUNDAY"};
        Gbr = new String[] {Integer.toString(R.drawable.day),
                Integer.toString(R.drawable.day),
                Integer.toString(R.drawable.day),
                Integer.toString(R.drawable.day),
                Integer.toString(R.drawable.day),
                Integer.toString(R.drawable.day),
                Integer.toString(R.drawable.day) };


        mylist = new ArrayList<HashMap<String,String>>();
        for (int i = 0; i < Pil.length; i++) {
            map = new HashMap<String, String>();
            map.put("list", Pil[i]);

            map.put("gbr", Gbr[i]);
            mylist.add(map);
        }

        Adapter = new SimpleAdapter(getActivity(), mylist, R.layout.layout_isi_lv,
                new String[] {"list", "latin", "gbr"}, new int[] {R.id.tv_nama, R.id.tv_ltn, R.id.imV});
        lv.setAdapter(Adapter);
    }

}
