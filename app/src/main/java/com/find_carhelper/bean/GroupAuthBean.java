package com.find_carhelper.bean;

public class GroupAuthBean {
    public String memberCode; //用户编号
    public String companyName; //公司全称
    public String companyShortName; //公司简称
    public String companyAddress; //公司地址
    public String legalRepresentative; //法人代表
    public String idCardNo; //法人代表身份证号
    public String uscc;
    public String businessLicenseImgUrl; //营业执照

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getUscc() {
        return uscc;
    }

    public void setUscc(String uscc) {
        this.uscc = uscc;
    }

    public String getBusinessLicenseImgUrl() {
        return businessLicenseImgUrl;
    }

    public void setBusinessLicenseImgUrl(String businessLicenseImgUrl) {
        this.businessLicenseImgUrl = businessLicenseImgUrl;
    }
}
