package com.find_carhelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author Mzy
 * date 2019/7/26
 * 写bug啊
 */
public class FindCarInfo implements Parcelable {

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
    public String code;
    public String positioningPin;
    public String reason;
    public String vehiclePhoto;
    public String vinPhoto;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPositioningPin() {
        return positioningPin;
    }

    public void setPositioningPin(String positioningPin) {
        this.positioningPin = positioningPin;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getVehiclePhoto() {
        return vehiclePhoto;
    }

    public void setVehiclePhoto(String vehiclePhoto) {
        this.vehiclePhoto = vehiclePhoto;
    }

    public String getVinPhoto() {
        return vinPhoto;
    }

    public void setVinPhoto(String vinPhoto) {
        this.vinPhoto = vinPhoto;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(orderCode);
        out.writeString(vin);
        out.writeString(vehicleModel);
        out.writeString(rewardAmount);
        out.writeString(lpn);
        out.writeString(region);
        out.writeString(partya);
        out.writeString(orderTime);
    }


    public static final Parcelable.Creator<FindCarInfo> CREATOR = new Creator<FindCarInfo>() {
        @Override
        public FindCarInfo createFromParcel(Parcel parcel) {
            FindCarInfo findCarInfo = new FindCarInfo();
            findCarInfo.setOrderCode(parcel.readString());
            findCarInfo.setVin(parcel.readString());
            findCarInfo.setVehicleModel(parcel.readString());
            findCarInfo.setRewardAmount(parcel.readString());
            findCarInfo.setLpn(parcel.readString());
            findCarInfo.setRegion(parcel.readString());
            findCarInfo.setPartya(parcel.readString());
            findCarInfo.setOrderTime(parcel.readString());
            return findCarInfo;
        }

        @Override
        public FindCarInfo[] newArray(int i) {
            return new FindCarInfo[i];
        }
    };

}
