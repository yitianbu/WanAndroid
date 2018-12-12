package com.weifeng.wanandroid.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.andview.refreshview.XRefreshViewFooter;
import com.weifeng.wanandroid.R;
import com.weifeng.wanandroid.adapter.ProjectViewPagerAdapter;
import com.weifeng.wanandroid.repositiry.APIService;
import com.weifeng.wanandroid.repositiry.RetrofitClient;
import com.weifeng.wanandroid.repositiry.callback.ThorCallback;
import com.weifeng.wanandroid.repositiry.response.ProjectCategoryResponse;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import retrofit2.Response;


public class ProjectMainFragment extends Fragment {
    private View rootView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    List<ProjectCategoryResponse.DataBean> dates;
    ProjectViewPagerAdapter viewPagerAdapter;
    private int currentPos = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return this.rootView != null ? this.rootView : inflater.inflate(R.layout.fragment_project, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(this.rootView == null){
            initView(view);
        }
        loadProjectCategoryData();
    }




    private void loadProjectCategoryData() {
        RetrofitClient.getInstance().getService(APIService.class).getProjectCategory().enqueue(new ThorCallback<ProjectCategoryResponse>() {
            @Override
            public void onSuccess(Response<ProjectCategoryResponse> response) {
                if(response!=null && response.body()!=null && response.body().getData()!=null){
                    dates = response.body().getData();
                    viewPagerAdapter.clearData();
                    for (int i = 0; i <dates.size(); i++) {
                        Fragment projectCategoryFragment = new ProjectCategoryFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("project_category",dates.get(i));
                        projectCategoryFragment.setArguments(bundle);
                        viewPagerAdapter.addFragment(projectCategoryFragment,dates.get(i).getName());
                    }
                    viewPagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(ErrorMessage errorMessage) {

            }
        });
    }

    private void initView(View view) {
        this.rootView = view;
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager = view.findViewById(R.id.view_pager);
        viewPagerAdapter = new ProjectViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                int selectedTabPos = tabLayout.getSelectedTabPosition();
                if(tabLayout.getTabAt(selectedTabPos)!=null) {
                    try {
                        TabLayout.Tab tab = tabLayout.getTabAt(selectedTabPos);
                        Class<?>  tabClass =  TabLayout.Tab.class;
                        Field field = tabClass.getDeclaredField("mView");
                        field.setAccessible(true);
                        View view = (View)field.get(tab);
                        tabLayout.smoothScrollTo(view.getLeft(),0);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
