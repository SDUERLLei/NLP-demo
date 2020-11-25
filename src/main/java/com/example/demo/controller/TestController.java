package com.example.demo.controller;


import ch.qos.logback.classic.pattern.ThrowableHandlingConverter;
import com.alibaba.fastjson.JSONArray;
import com.example.demo.Service.TestService;
import com.example.demo.common.ClassifyThread;
import com.example.demo.common.Constant;
import com.example.demo.common.GetApiParam;
import com.example.demo.common.MyThread;
import com.inspur.cloud.apig.sdk.util.AppSign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import static java.lang.System.in;

/**
 * Created by liulei03 on 2020/8/10.
 */
@Controller
@Api(value="机构名识别接口调用",tags = "机构名识别接口调用类",description = "Ray")
@RequestMapping("/demo")
public class TestController {

    @Autowired
    private Constant constant;

    @Autowired
    @Qualifier("ds1JdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("ds2JdbcTemplate")
    private JdbcTemplate localJdbcTemplate;

    @Autowired
    @Qualifier("ds3JdbcTemplate")
    private JdbcTemplate clickhouseJdbcTemplate;

    @Autowired
    TestService testService;

    ClassifyThread th = new ClassifyThread(localJdbcTemplate,"线程1",0,10);

    @ApiOperation(value="调用方法",notes = "测试注释")
    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index() throws Exception {
        this.startAnalysis();
        return "/page/npl/index.html";
    }

    /**
     * 分析启动类
     */
    public void startAnalysis() throws Exception {
        Map<String,Integer> map = this.threadInfo();
//        //启动分析工作
//        this.contentEnt(map,jdbcTemplate);
//        //数据清理
//        String sql = "delete from yuqing_ent_temp where id not in (select a.id from (select id from yuqing_ent_temp where ENTNAME like '%公司%' or  ENTNAME like '%集团%' or  ENTNAME like '%馆%' or  ENTNAME like '%餐厅%' or  ENTNAME like '%店%' or  ENTNAME like '%厂%')a ) ";
//        this.dataClean(sql,jdbcTemplate);
//        String sql2 = "DELETE FROM yuqing_ent_temp WHERE ENTNAME IN (select ENTNAME from ( SELECT ENTNAME FROM yuqing_ent_temp GROUP BY ENTNAME  HAVING  count(ENTNAME) > 1) a ) AND ID NOT IN (select * from (" +
//                "SELECT  min(ID) FROM yuqing_ent_temp GROUP BY ENTNAME HAVING count(ENTNAME) > 1) b)";
//        this.dataClean(sql2,jdbcTemplate);
//        //数据迁移mysql到clickhouse
//        String sql ="insert into table yuqing_ent select * from mysql('10.110.10.42:3306','jgbigdata','yuqing_ent','root','HlwjgSjk%420')";
//        //企业名称和ID匹配
//        String matchSql = "insert into yuqing_temp select a.ID,a.ITEMID,b.EntName,b.UniSCID,a.INFLUENCE,a.DOMAIN,a.INSERT_TIME from yuqing_ent a inner join jg_jgdx_qyjbxx b on a.ENTNAME = b.EntName;";
//        List<Map<String,Object>> list = this.entIdMatch(matchSql);
//        //迁移结果数据clickhouse到mysql
//        this.insertToMysql();
//        System.out.println("###########数据迁移完成###########");
        //this.contentClassify(map,localJdbcTemplate);
        //th.getEnt(localJdbcTemplate,"线程1",0,10);
        this.contentClassify(map,localJdbcTemplate);
    }

    /**
     * 多线程调用接口分析企业名称
     * @param map
     */
    public void contentEnt(Map<String,Integer> map,JdbcTemplate jdbcTemplate){
        int dataStart = map.get("dataStart");
        int size = map.get("size");//线程数量单个线程处理量
        int threadCount= map.get("threadCount");//单个线程处理量
        int num = map.get("num");
        int last = map.get("last");
        //企业名称识别
        for(int i=1;i<size+1;i++){
            if(i==size){
                MyThread th = new MyThread(jdbcTemplate,"线程"+i,threadCount,last);
                new Thread(th).start();
            }else if(i==1){
                MyThread th = new MyThread(jdbcTemplate,"线程"+i,dataStart,num);
                new Thread(th).start();
                threadCount=dataStart+num;
            }else{
                MyThread th = new MyThread(jdbcTemplate,"线程"+i,threadCount,last);
                threadCount+=threadCount;
                new Thread(th).start();
            }
        }
    }

    /**
     * 舆情文本分类
     * @param map
     */
    public void contentClassify(Map<String,Integer> map,JdbcTemplate jdbcTemplate){
        int dataStart = map.get("dataStart");
        int size = map.get("size");//线程数量单个线程处理量
        int threadCount= map.get("threadCount");//单个线程处理量
        int num = map.get("num");
        int last = map.get("last");
        //舆情文本分类
        for(int i=1;i<size+1;i++){
            if(i==size){
                ClassifyThread th = new ClassifyThread(jdbcTemplate,"线程"+i,threadCount,last);
                new Thread(th).start();
            }else if(i==1){
                ClassifyThread th = new ClassifyThread(jdbcTemplate,"线程"+i,dataStart,num);
                new Thread(th).start();
                threadCount=dataStart+num;
            }else{
                ClassifyThread th = new ClassifyThread(jdbcTemplate,"线程"+i,threadCount,num);
                threadCount+=threadCount;
                new Thread(th).start();
            }
        }
    }

    /**
     * 线程信息
     * @return
     */
    public Map<String,Integer> threadInfo(){
        Map<String,Integer> map = new HashMap<String,Integer>();
        int dataStart = constant.getDataStart();
        int dataCount = constant.getDataEnd();
        int size = constant.getThreadSize();//线程数量单个线程处理量
        int  threadCount= dataCount/size;//单个线程处理量
        int num = threadCount;
        int last = dataCount-threadCount*(size-1);
        map.put("dataStart",dataStart);
        map.put("size",size);
        map.put("threadCount",threadCount);
        map.put("num",num);
        map.put("last",last);
        return map;
    }

    /**
     * 清理数据
     * @param sql
     */
    public void dataClean(String sql,JdbcTemplate jdbcTemplate){
        jdbcTemplate.execute(sql);
    }

    /**
     * 企业名称和统一社会信用代码匹配
     * @param sql
     * @return
     */
    public List<Map<String,Object>> entIdMatch(String sql,JdbcTemplate jdbcTemplate){
        List<Map<String,Object>> list  = jdbcTemplate.queryForList(sql);
        return list;
    }

    public void insertToMysql(JdbcTemplate jdbcTemplate){
        String sql = "select * from yuqing_temp;";
        List<Map<String,Object>> list = clickhouseJdbcTemplate.queryForList(sql);
        System.out.println("#########"+list.size()+"##########");
        String ID = "";
        String ITEMID = "";
        String ENTNAME = "";
        String ENTID = "";
        String INFLUENCE = "";
        String DOMAIN = "";
        String insertSql = " insert into yuqing_ent_temp(ID,ITEMID,ENTNAME,ENTID,INFLUENCE) values ";
        ID="UUID()";
        for(int i=0;i<list.size();i++){
            Map<String,Object> map = list.get(i);
            //ID = map.get("ID").toString();
            ITEMID = map.get("ITEMID").toString();
            ENTNAME = map.get("ENTNAME").toString();
            ENTID = map.get("ENTID").toString();
            INFLUENCE = map.get("INFLUENCE")==null?"0":map.get("INFLUENCE").toString();
            //DOMAIN = map.get("DOMAIN")==null?" ":map.get("DOMAIN").toString();
            insertSql += " ("+ID+",'"+ITEMID+"','"+ENTNAME+"','"+ENTID+"','"+INFLUENCE+"' ),";
        }
        insertSql =insertSql.substring(0,insertSql.length()-1);
        int n = jdbcTemplate.update(insertSql);
    }

}
