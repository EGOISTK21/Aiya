package xyz.egoistk21.aiya.community;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.egoistk21.aiya.R;
import xyz.egoistk21.aiya.base.LazyFragment;


/**
 * Created by EGOISTK21 on 2017/3/16.
 */

public class CommunityFragment extends LazyFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_community, container, false);
    }
}
