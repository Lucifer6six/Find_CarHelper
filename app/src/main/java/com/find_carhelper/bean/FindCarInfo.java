package com.find_carhelper.bean;

/**
 * author Mzy
 * date 2019/7/26
 * 写bug啊
 */
public class FindCarInfo {

    public String lpn; //车牌号码
    public String orderCode;
    public String orderStatus;
    public String orderStatusName;
    public String orderTime;
    public String orderMemberCount; //接单人数
    public String partya; //甲方
    public String region; //区域
    public String rewardAmount; //寻车赏金
    public String vehicleModel; //车型
    public String vin; //车架号

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn;
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

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderMemberCount() {
        return orderMemberCount;
    }

    public void setOrderMemberCount(String orderMemberCount) {
        this.orderMemberCount = orderMemberCount;
    }

    public String getPartya() {
        return partya;
    }

    public void setPartya(String partya) {
        this.partya = partya;
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
}
