package xyz.egoistk.aiya.love.matched.view.impl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.egoistk.aiya.R;
import xyz.egoistk.aiya.love.matched.view.i.ILoveMatchedJournalView;
import xyz.egoistk.aiya.base.LazyFragment;

/**
 * Created by EGOISTK on 2017/3/21.
 */

public class LoveMatchedJournalFragment extends LazyFragment implements ILoveMatchedJournalView {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_love_matched_journal, container, false);
        return rootView;
    }
}
