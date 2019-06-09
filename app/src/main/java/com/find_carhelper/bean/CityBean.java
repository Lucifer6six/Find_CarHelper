package com.find_carhelper.bean;

import com.contrarywind.interfaces.IPickerViewData;

public class CityBean implements IPickerViewData {
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
