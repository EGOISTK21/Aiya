package xyz.egoistk21.aiya.love;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.egoistk21.aiya.R;
import xyz.egoistk21.aiya.base.LazyFragment;
import xyz.egoistk21.aiya.love.unmatched.UnmatchedFragment;

/**
 * Created by EGOISTK on 2017/3/29.
 */

public class LoveContainerFragment extends LazyFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container_love, new UnmatchedFragment()).commit();
        return inflater.inflate(R.layout.fragment_container_love_unmatched, container, false);
    }
}
