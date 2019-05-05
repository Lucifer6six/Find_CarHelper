package com.find_carhelper.ui.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.find_carhelper.R;
import com.find_carhelper.bean.CardBean;
import com.find_carhelper.bean.JsonBean;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.base.MVPBaseFragment;
import com.find_carhelper.utils.GetJsonDataUtil;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AcceptOrderFragment extends MVPBaseFragment {

    private RelativeLayout areaSelectedLayout,timeSelectedLayout;
    private TextView areaSelectedTv,timeSelectedTv;

    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    private List<JsonBean> options1Items_ = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items_ = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items_ = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private static boolean isLoaded = false;
    public static Fragment newInstance() {
       AcceptOrderFragment fragment = new AcceptOrderFragment();
        return fragment;
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_accept_order;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void initViews() {
            mHandler.sendEmptyMessage(MSG_LOAD_DATA);
            areaSelectedLayout = mRootView.findViewById(R.id.area_layout);
            timeSelectedLayout = mRootView.findViewById(R.id.time_layout);

            areaSelectedTv = mRootView.findViewById(R.id.areaSelectedTv);
            timeSelectedTv = mRootView.findViewById(R.id.timeSelectedTv);


            areaSelectedLayout.setOnClickListener(view -> {
                if (isLoaded) {
                    showAreaPickView();
                } else {
                    Toast.makeText(getContext(), "Please waiting until the data is parsed", Toast.LENGTH_SHORT).show();
                }


            });
            timeSelectedLayout.setOnClickListener(view -> {

                    showTimePickView();

            });

    }

    private void showAreaPickView(){

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            //返回的分别是三个级别的选中位置
            String opt1tx = options1Items.size() > 0 ?
                    options1Items.get(options1).getPickerViewText() : "";

            String opt2tx = options2Items.size() > 0
                    && options2Items.get(options1).size() > 0 ?
                    options2Items.get(options1).get(options2) : "";

            String opt3tx = options2Items.size() > 0
                    && options3Items.get(options1).size() > 0
                    && options3Items.get(options1).get(options2).size() > 0 ?
                    options3Items.get(options1).get(options2).get(options3) : "";

            String tx = opt1tx+"-"+ opt2tx+"-"+ opt3tx;
            //Toast.makeText(getContext(), tx, Toast.LENGTH_SHORT).show();
            areaSelectedTv.setText(tx);
        }
    })

            .setTitleText("城市选择")
            .setDividerColor(Color.BLACK)
            .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
            .setContentTextSize(20)
            .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    private ArrayList<CardBean> cardItem = new ArrayList<>();
    private OptionsPickerView  pvCustomOptions;
    private void showTimePickView(){



        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items_.size() > 0 ?
                        options1Items_.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items_.size() > 0
                        && options2Items_.get(options1).size() > 0 ?
                        options2Items_.get(options1).get(options2) : "";


                String tx = opt1tx +" ~ "+ opt2tx;
                //Toast.makeText(getContext(), tx, Toast.LENGTH_SHORT).show();
                timeSelectedTv.setText(tx);
            }
        })

                .setTitleText("赏金选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items_, options2Items_);//三级选择器
        pvOptions.show();
    }


    @Override
    protected void initData() {

    }
    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         * */
        String JsonData = new GetJsonDataUtil().getJson(getContext(), "province.json");//获取assets目录下的json文件数据
        String JsonDatas = new GetJsonDataUtil().getJson(getContext(), "money.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        ArrayList<JsonBean> jsonBeans = parseData(JsonDatas);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
//-----------------------------------------------------------
        options1Items_ = jsonBeans;

        for (int i = 0; i < jsonBeans.size(); i++) {//遍历省份
            ArrayList<String> cityList_ = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList_ = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBeans.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBeans.get(i).getCityList().get(c).getName();
                cityList_.add(cityName);//添加城市
  //              ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

//                city_AreaList.addAll(jsonBeans.get(i).getCityList().get(c).getArea());
//                province_AreaList_.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items_.add(cityList_);
//            options3Items_.add(province_AreaList_);

        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        Toast.makeText(getContext(), "Begin Parse Data", Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    Toast.makeText(getContext(), "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(getContext(), "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }
}
