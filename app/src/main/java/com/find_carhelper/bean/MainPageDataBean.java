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
    public List<String> timeList;
    public List<String> newsList;
    public List<String> findList;
    public List<String> retrieveList;

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

    public List<String> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<String> timeList) {
        this.timeList = timeList;
    }

    public List<String> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<String> newsList) {
        this.newsList = newsList;
    }

    public List<String> getFindList() {
        return findList;
    }

    public void setFindList(List<String> findList) {
        this.findList = findList;
    }

    public List<String> getRetrieveList() {
        return retrieveList;
    }

    public void setRetrieveList(List<String> retrieveList) {
        this.retrieveList = retrieveList;
    }
}
