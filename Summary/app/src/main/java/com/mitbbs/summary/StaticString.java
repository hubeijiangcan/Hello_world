package com.mitbbs.summary;

/**
 * Created by jc on 2017/4/26.
 * 存放一些常用的静态变量
 */
public class StaticString {

    //线上
    public static String BASE_DOMAIN = "www.rencai8.com";

    //阿里云
//    public static String BASE_DOMAIN = "123.57.5.76:8080";

    public static String BASE_URL ="http://"+BASE_DOMAIN+"/App_service/service_menu.php";


    //是否有新版本
    public static boolean hasNewVersion = false;







    /**
     * requestType  接口类型
     */
    //检查版本
    public static final String CHECK_VERSION = "";
}
