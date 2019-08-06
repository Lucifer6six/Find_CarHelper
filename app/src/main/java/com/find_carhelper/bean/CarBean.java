package com.find_carhelper.bean;


import java.io.Serializable;

public class CarBean implements Serializable {

    public int id;
    public String countdown;
    public String hasKey;
    public String lpn;
    public String partya;
    public String positioningMethod;
    public String region;
    public String rewardAmount;
    public String status;
    public String vehicleModel;
    public String vin;
    public String assignTask;
    public String orderStatusName;
    public String orderCode;
    public String orderStatus;
    public String canOrder;
    public String remark;
    public String positioningName;
    public String positioningPin;
    public String lesseeInfo;
    public String orderTime;

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPositioningName() {
        return positioningName;
    }

    public void setPositioningName(String positioningName) {
        this.positioningName = positioningName;
    }

    public String getPositioningPin() {
        return positioningPin;
    }

    public void setPositioningPin(String positioningPin) {
        this.positioningPin = positioningPin;
    }

    public String getLesseeInfo() {
        return lesseeInfo;
    }

    public void setLesseeInfo(String lesseeInfo) {
        this.lesseeInfo = lesseeInfo;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getAssignTask() {
        return assignTask;
    }

    public void setAssignTask(String assignTask) {
        this.assignTask = assignTask;
    }

    public String getCountdown() {
        return countdown;
    }

    public void setCountdown(String countdown) {
        this.countdown = countdown;
    }

    public String getHasKey() {
        return hasKey;
    }

    public void setHasKey(String hasKey) {
        this.hasKey = hasKey;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn;
    }

    public String getPartya() {
        return partya;
    }

    public void setPartya(String partya) {
        this.partya = partya;
    }

    public String getPositioningMethod() {
        return positioningMethod;
    }

    public void setPositioningMethod(String positioningMethod) {
        this.positioningMethod = positioningMethod;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(String rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getCanOrder() {
        return canOrder;
    }

    public void setCanOrder(String canOrder) {
        this.canOrder = canOrder;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }

//    public static final Creator<CarBean> CREATOR = new Creator<CarBean>() {
//        @Override
//        public CarBean createFromParcel(Parcel parcel) {
//
//            CarBean carBean = new CarBean();
//            carBean.setCountdown(parcel.readString());
//            carBean.setHasKey(parcel.readString());
//            carBean.setLpn(parcel.readString());
//            carBean.setPartya(parcel.readString());
//            carBean.setPositioningMethod(parcel.readString());
//            carBean.setRegion(parcel.readString());
//            carBean.setRewardAmount(parcel.readString());
//            carBean.setStatus(parcel.readString());
//            carBean.setVehicleModel(parcel.readString());
//            carBean.setVin(parcel.readString());
//            carBean.setAssignTask(parcel.readString());
//            carBean.setOrderStatusName(parcel.readString());
//            carBean.setOrderCode(parcel.readString());
//            carBean.setOrderStatus(parcel.readString());
//            carBean.setCanOrder(parcel.readString());
//            carBean.setRemark(parcel.readString());
//            carBean.setPositioningName(parcel.readString());
//            carBean.setPositioningPin(parcel.readString());
//            carBean.setLesseeInfo(parcel.readString());
//            return carBean;
//        }
//
//        @Override
//        public CarBean[] newArray(int i) {
//            return new CarBean[0];
//        }
//    };

//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(countdown);
//        parcel.writeString(hasKey);
//        parcel.writeString(lpn);
//        parcel.writeString(partya);
//        parcel.writeString(positioningMethod);
//        parcel.writeString(region);
//        parcel.writeString(rewardAmount);
//        parcel.writeString(status);
//        parcel.writeString(vehicleModel);
//        parcel.writeString(vin);
//        parcel.writeString(assignTask);
//        parcel.writeString(orderStatusName);
//        parcel.writeString(orderCode);
//        parcel.writeString(orderStatus);
//        parcel.writeString(canOrder);
//        parcel.writeString(orderCode);
//        parcel.writeString(positioningName);
//        parcel.writeString(positioningPin);
//        parcel.writeString(lesseeInfo);
//        parcel.writeString(remark);
//
//    }
}
