package xyz.egoistk21.aiya.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 懒加载的Fragment,在必要的时候才加载，否则不进行View的绘制和数据的加载。
 * 原因是Viewpager一次只会显示一个页卡，那么刚开始的时候，只需加载第一张Fragment页卡，
 * 其他的不加载，当用户向右滑动切换再进行加载。因为其他Fragment对于用户来说是不可见的，
 * 如果一开始就把全部Fragment一起加载，可能造成启动时卡顿的问题，
 * 更重要的是可能白白耗费用户的流量，因为用户可能并不需要其他Fragment的信息。
 * Created by EGOISTK on 2017/3/14.
 */

public class LazyFragment extends Fragment implements View.OnClickListener {
    // 标志位，标志已经初始化完成，因为setUserVisibleHint是在onCreateView之前调用的，
    // 在视图未初始化的时候，在lazyLoad当中就使用的话，就会有空指针的异常
    private boolean isPrepared;
    //标志当前页面是否可见
    private boolean isVisible;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //懒加载
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad() {
        if (!isVisible || !isPrepared) {
            return;
        }
        //getData();//数据请求
    }

    protected void onInvisible() {
    }

    @Override
    public void onClick(View v) {
    }
}
