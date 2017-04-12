package com.aiyaschool.aiya.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by XZY on 2017/3/14.
 */
public class MsgFragment extends Fragment{

    private static final String ARG_CRIME_ID = "crime_id";

    public static MsgFragment newInstance(UUID crimeId) {
        Bundle bundle = new Bundle();

        bundle.putSerializable(ARG_CRIME_ID, crimeId);

        MsgFragment fragment = new MsgFragment();
        fragment.setArguments(bundle);
        return fragment;
    }



}
