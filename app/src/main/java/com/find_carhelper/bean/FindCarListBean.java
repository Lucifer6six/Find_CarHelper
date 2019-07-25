package com.find_carhelper.bean;

import java.util.List;

public class FindCarListBean {

    public String endRow;
    public String hasNextPage; //是否有下一页
    public String hasPreviousPage; //是否有上一页
    public String isFirstPage; //是否展示立即接单按钮及赏金
    public String isLastPage; //是否为最后一页
    public String navigateFirstPage;
    public String navigateLastPage;
    public String navigatePages;
    public List<String> navigatepageNums;
    public String nextPage;
    public String pageNum;
    public String pageSize;
    public String pages;
    public String prePage;
    public String size;
    public String startRow;
    public String total; //合作中的寻车订单数量
    public data data;

    public String getEndRow() {
        return endRow;
    }

    public void setEndRow(String endRow) {
        this.endRow = endRow;
    }

    public String getHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(String hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public String getHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(String hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public String getIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(String isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public String getIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(String isLastPage) {
        this.isLastPage = isLastPage;
    }

    public String getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public void setNavigateFirstPage(String navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public String getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateLastPage(String navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    public String getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(String navigatePages) {
        this.navigatePages = navigatePages;
    }

    public List<String> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<String> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPrePage() {
        return prePage;
    }

    public void setPrePage(String prePage) {
        this.prePage = prePage;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStartRow() {
        return startRow;
    }

    public void setStartRow(String startRow) {
        this.startRow = startRow;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public FindCarListBean.data getData() {
        return data;
    }

    public void setData(FindCarListBean.data data) {
        this.data = data;
    }

    public class data {
        public List<carInfo> list;

        public List<carInfo> getList() {
            return list;
        }

        public void setList(List<carInfo> list) {
            this.list = list;
        }

        public class carInfo {
            public String lpn; //车牌号码
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
    }
}
