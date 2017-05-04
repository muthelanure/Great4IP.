package com.example.a6sigma.great4ip.FragmentMessage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a6sigma.great4ip.R;

/**
 * Created by JustL on 5/1/2017.
 */

public class InboxFragment extends Fragment {

    public InboxFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layoutRs;

        layoutRs = inflater.inflate(R.layout.inbox_data, container, false);

        return layoutRs;
    }
}
