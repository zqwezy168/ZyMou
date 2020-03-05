package zy.library.utils;

import android.app.Activity;

import zy.library.R;
import zy.library.ui.refresh.TwinklingRefreshLayout;
import zy.library.ui.refresh.footer.LoadingView;
import zy.library.ui.refresh.header.SinaRefreshView;


/**
 * Created by zy on 2018/3/27.
 */

public class RefreshUtil {
    public static void init(Activity activity, TwinklingRefreshLayout refreshLayout, boolean isLoadMore){
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOverScrollRefreshShow(true);
        SinaRefreshView headerView = new SinaRefreshView(activity);
        headerView.setArrowResource(R.mipmap.arrow_down);
        //        TextHeaderView headerView = (TextHeaderView) View.inflate(this,R.layout.header_tv,null);
        headerView.setTextColor(activity.getResources().getColor(R.color.gray));
        refreshLayout.setHeaderView(headerView);
        if (isLoadMore){
            refreshLayout.setEnableLoadmore(true);
            refreshLayout.setAutoLoadMore(true);
            LoadingView loadingView  = new LoadingView(activity);
            refreshLayout.setBottomView(loadingView);
        }
    }
}
