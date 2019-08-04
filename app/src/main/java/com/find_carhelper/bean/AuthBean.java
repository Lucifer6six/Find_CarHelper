package com.find_carhelper.bean;

public class AuthBean {

    public String memberCode; //用户编号
    public String realName;//姓名
    public String idCardNo;//身份证号
    public String idCardObverseImgUrl;//身份证正面照
    public String idCardReverseImgUrl;//身份证背面照
    public String idCardHoldImgUrl;//身份证手持照

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getIdCardObverseImgUrl() {
        return idCardObverseImgUrl;
    }

    public void setIdCardObverseImgUrl(String idCardObverseImgUrl) {
        this.idCardObverseImgUrl = idCardObverseImgUrl;
    }

    public String getIdCardReverseImgUrl() {
        return idCardReverseImgUrl;
    }

    public void setIdCardReverseImgUrl(String idCardReverseImgUrl) {
        this.idCardReverseImgUrl = idCardReverseImgUrl;
    }

    public String getIdCardHoldImgUrl() {
        return idCardHoldImgUrl;
    }

    public void setIdCardHoldImgUrl(String idCardHoldImgUrl) {
        this.idCardHoldImgUrl = idCardHoldImgUrl;
    }
}
