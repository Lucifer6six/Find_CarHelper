package com.find_carhelper.bean;

import java.util.List;

public class BillListBean {
    private String year;
    private List<list> list;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<BillListBean.list> getList() {
        return list;
    }

    public void setList(List<BillListBean.list> list) {
        this.list = list;
    }

    class list{
        private String month;
        private String amount;
        private String applyTime;
        private String paymentTime;
        private String status;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public String getPaymentTime() {
            return paymentTime;
        }

        public void setPaymentTime(String paymentTime) {
            this.paymentTime = paymentTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
