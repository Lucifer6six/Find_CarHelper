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
    private MainPageFragment mainPageFragment;
    private FindCarFragment mFindCarFragment;
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

    public MainPageFragment getMainPageFragment() {
        if (mainPageFragment == null) {
            synchronized (FragmentFactory.class) {
                if (mainPageFragment == null) {
                    mainPageFragment = new MainPageFragment();
                }
            }
        }
        return mainPageFragment;
    }
    public FindCarFragment getFindCarFragment() {
        if (mFindCarFragment == null) {
            synchronized (FragmentFactory.class) {
                if (mFindCarFragment == null) {
                    mFindCarFragment = new FindCarFragment();
                }
            }
        }
        return mFindCarFragment;
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

    public UserCenterFragment getUserCenterFragment() {
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
