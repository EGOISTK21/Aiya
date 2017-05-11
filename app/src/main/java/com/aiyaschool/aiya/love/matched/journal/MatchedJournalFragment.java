package com.aiyaschool.aiya.love.matched.journal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.LazyFragment;
import com.aiyaschool.aiya.widget.StringScrollPicker;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by EGOISTK21 on 2017/3/21.
 */

public class MatchedJournalFragment extends LazyFragment implements ILoveMatchedJournalView {

    private View rootView;
    private StringScrollPicker sspDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_love_matched_journal, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        sspDate = (StringScrollPicker) rootView.findViewById(R.id.ssp_date);
        sspDate.setData(new ArrayList<>(Arrays.asList("Day 6", "Day 5", "Day 4", "Day 3", "Day 2", "Day 1")));
    }
}
