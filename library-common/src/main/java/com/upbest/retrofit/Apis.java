package com.upbest.retrofit;



/**
 * Created by kGod on 2017/2/28.
 * Email 18252032703@163.com
 * Thank you for watching my code
 * 存放所有请求Api Retrofit
 *
 * @author kang gui yang
 */
public interface Apis {


    /**
     * 接口根地址 本地
     */
    //String ROOT_URL = "http://192.168.1.115:8080/";
    String ROOT_URL = "http://order.upbest-china.com/";

    /**
     * 图片根地址
     */
    //String IMG_ROOT_URL = "http://192.168.1.115:8081/manager/showPhoto?img_url=";
    String IMG_ROOT_URL = ROOT_URL + "img/";

    /**
     * 文件下载地址
     */
    String DOWNLOAD_URL = ROOT_URL + "manager/downloadAPK?url=";



}