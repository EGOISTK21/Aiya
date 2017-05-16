package com.aiyaschool.aiya.love.matched.journal;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.widget.StringScrollPicker;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by EGOISTK21 on 2017/3/21.
 */

public class MatchedJournalFragment extends BaseFragment implements ILoveMatchedJournalView {

    private StringScrollPicker sspDate;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_love_matched_journal;
    }

    @Override
    protected void initView() {
        sspDate = (StringScrollPicker) rootView.findViewById(R.id.ssp_date);
        sspDate.setData(new ArrayList<>(Arrays.asList("Day 6", "Day 5", "Day 4", "Day 3", "Day 2", "Day 1")));
    }
}
