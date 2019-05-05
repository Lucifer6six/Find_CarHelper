package com.find_carhelper.ui.fragment;


/**
 * 主界面4个Fragment工厂
 * @author yangchuncheng
 * @date 2019/3/14
 */
public class FragmentFactory {

    static FragmentFactory mInstance;

    private FragmentFactory() {
    }

    public static FragmentFactory getInstance() {
        if (mInstance == null) {
            synchronized (FragmentFactory.class) {
                if (mInstance == null) {
                    mInstance = new FragmentFactory();
                }
            }
        }
        return mInstance;
    }

    private AcceptOrderFragment mHomeFragment;
    private MyOrdersFragment mFaultRepairMainPageFragment;
    private UserCenterFragment mPlanRepairMainPageFragment;
    private NavigationViewFragment mNavigationViewFragment;

    //侧边栏内容Fragment
    public NavigationViewFragment getNavigationViewFragmentt() {
        if (mNavigationViewFragment == null) {
            synchronized (FragmentFactory.class) {
                if (mNavigationViewFragment == null) {
                    mNavigationViewFragment = new NavigationViewFragment();
                }
            }
        }
        return mNavigationViewFragment;
    }

    public AcceptOrderFragment getHomeFragment() {
        if (mHomeFragment == null) {
            synchronized (FragmentFactory.class) {
                if (mHomeFragment == null) {
                    mHomeFragment = new AcceptOrderFragment();
                }
            }
        }
        return mHomeFragment;
    }

    public MyOrdersFragment getFaultRepairMainPageFragment() {
        if (mFaultRepairMainPageFragment == null) {
            synchronized (FragmentFactory.class) {
                if (mFaultRepairMainPageFragment == null) {
                    mFaultRepairMainPageFragment = new MyOrdersFragment();
                }
            }
        }
        return mFaultRepairMainPageFragment;
    }

    public UserCenterFragment getPlanRepairMainPageFragment() {
        if (mPlanRepairMainPageFragment == null) {
            synchronized (FragmentFactory.class) {
                if (mPlanRepairMainPageFragment == null) {
                    mPlanRepairMainPageFragment = new UserCenterFragment();
                }
            }
        }
        return mPlanRepairMainPageFragment;
    }


}
