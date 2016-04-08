package com.subzero.trafficflow.HttpConfig;

/**
 * Created by hasee on 2016/3/22.
 */
public class UrlConstants {

    public static final String BASE = "http://219.141.223.141:8325/jddsi/rest";
    //license
    public static final String license = "rcr1ctf3sv-XEMDmy1hjpTuSZVU3Mqsf6P5OxKz-BC9MvhjVBNDfv1SbdgYRUeEl";
    //登入
    public static final String LOGIN_IN = "/app/user/login.json";
    //登出
    public static final String LOGIN_OUT = "/app/user/logout.json";
    //读取服务器列表
    public static final String SERVER_NAME_ = "/sys/servers.json";
    //查询地图可视范围观测站基本信息
    public static final String MAP_SCOPE = "/app/gcz/query.json";
    //获取业务联系人方式
    public static final String CONTACT = "/app/users.json";

    //查询行政区划信息
    public static final String XZQH_QUERY = "/mst/xzqh/query.json";
    //2.4.2查询下级行政区划信息
    public static final String XZQH_SUB = "/mst/xzqh/sub.json";
    //2.5.1查询观测站5分钟数据
    public static final String MINUTES = "/dat/dcsj/recent/query.json";
    //观测站小时数据
    public static final String HOUR = "/dat/dcsj/hour/query.json";
    //观测站每日数据
    public static final String DAY = "/dat/dcsj/day/query.json";
    //观测站月度数据
    public static final String MONTH = "/dat/dcsj/month/query.json";
    //观测站年度数据
    public static final String YEAR = "/dat/dcsj/year/query.json";
    //2.10.1查询观测站下设备信息
    public static final String QUALITY_DEVICE = "/eqp/query.json";
    //2.10.2观测站日数据质量明细
    public static final String QUALITY_DAY = "/eqp/quality/day.json";
    //2.10.3观测站月数据质量明细
    public static final String QUALITY_MONTH = "/eqp/quality/month.json";
    //2.11.1查询观测站基本信息
    public static final String BASE_INFOR = "/mst/gcz/get.json";
    //数据核查页,查询时间段内5分钟数据汇总结果
    public static final String  MINUTES_CHECK= "/dat/dcsj/time/amount.json";
    //2.4.1查询行政区划信息
   public static final String DISTRICT_INFO= "/mst/xzqh/query.json";
    //2.4.2查询下级行政区划信息
   public static final String DISTRICT_DOWN_INFO= "/mst/xzqh/sub.json";


























    //关于我们
    public static final String ABOUT_US = "http://219.141.223.141:8325/jddsi/about.html";
    //


}
