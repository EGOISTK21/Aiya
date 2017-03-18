package com.egoistk.aiya.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egoistk.aiya.R;
import com.egoistk.aiya.LazyFragment;

/**
 * Created by EGOISTK on 2017/3/16.
 */

public class MessageFragment extends LazyFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }
}
