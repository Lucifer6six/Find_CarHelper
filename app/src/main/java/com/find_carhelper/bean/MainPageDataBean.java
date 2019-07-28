package com.find_carhelper.bean;

import java.util.List;

/**
 * author Mzy
 * date 2019/7/25
 * 写bug啊
 */
public class MainPageDataBean {

    public String finding;//在寻
    public String found;//已寻
    public String retrieved;//已收
    public String retrieving;//在收
    public String today;//今日
    public String total;//总
    
    public List<String> newsList;
    public List<ListBean> findList;
    public List<ListBean> timeList;
    public List<ListBean> retrieveList;

    public List<ListBean> getFindList() {
        return findList;
    }

    public void setFindList(List<ListBean> findList) {
        this.findList = findList;
    }

    public List<ListBean> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<ListBean> timeList) {
        this.timeList = timeList;
    }

    public List<ListBean> getRetrieveList() {
        return retrieveList;
    }

    public void setRetrieveList(List<ListBean> retrieveList) {
        this.retrieveList = retrieveList;
    }

    public String getFinding() {
        return finding;
    }

    public void setFinding(String finding) {
        this.finding = finding;
    }

    public String getFound() {
        return found;
    }

    public void setFound(String found) {
        this.found = found;
    }

    public String getRetrieved() {
        return retrieved;
    }

    public void setRetrieved(String retrieved) {
        this.retrieved = retrieved;
    }

    public String getRetrieving() {
        return retrieving;
    }

    public void setRetrieving(String retrieving) {
        this.retrieving = retrieving;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }


    public List<String> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<String> newsList) {
        this.newsList = newsList;
    }


    public class ListBean {
        private String left;
        private String right;

        public String getLeft() {
            return left;
        }

        public void setLeft(String left) {
            this.left = left;
        }

        public String getRight() {
            return right;
        }

        public void setRight(String right) {
            this.right = right;
        }
    }


}
