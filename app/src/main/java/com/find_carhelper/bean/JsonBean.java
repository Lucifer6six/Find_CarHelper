package com.find_carhelper.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * TODO<json数据源>
 *
 */

public class JsonBean implements IPickerViewData {


    /**
     * name : 省份
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
     */

    private String name;
    private List<CityBean> cityList;
    private String code;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityBean> cityList) {
        this.cityList = cityList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return this.name;
    }


    public static class CityBean {
        /**
         * name : 城市
         * area : ["东城区","西城区","崇文区","昌平区"]
         */

        private String name;
        private List<com.find_carhelper.bean.CityBean> cityList;
        private String code;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<com.find_carhelper.bean.CityBean> getArea() {
            return cityList;
        }

        public void setArea(List<String> acityListrea) {
            this.cityList = cityList;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}