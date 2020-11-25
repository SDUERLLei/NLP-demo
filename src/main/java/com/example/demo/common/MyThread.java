package com.example.demo.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created by liulei03 on 2020/10/20.
 */
public class MyThread implements Runnable{
    private String name;
    private int start;
    private int end;
    private JdbcTemplate jdbcTemplate;

    public MyThread(JdbcTemplate jdbcTemplate,String name,int start,int end){
        this.jdbcTemplate = jdbcTemplate;
        this.name = name;
        this.start = start;
        this.end = end;
    }
    public void run(){
        try {
            this.getEnt(jdbcTemplate,name,start,end);
            System.out.println(name+" 运行结束。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据分析调用实现
     * @param jdbcTemplate
     * @param threadName
     * @param start
     * @param end
     * @throws Exception
     */
    public void getEnt(JdbcTemplate jdbcTemplate,String threadName,int start,int end) throws Exception {
        String url = "http://10.110.10.23:8095/nlp-cs-ner/nlp/annotators/orgner";
        JSONArray conList = this.getContext(jdbcTemplate,start,end);
        System.out.println(threadName+": "+"###################总共处理数据"+conList.size()+"条#####################");
        if(conList.size()>0){
            ArrayList mapList = new ArrayList();
            for(int i=0;i<conList.size();i++){
                Map<String,List> map =new HashMap<>();
                ArrayList list = new ArrayList();
                System.out.println(threadName+": "+"*****************第"+(i+1)+"次请求接口*****************");
                String itemId = conList.getJSONObject(i).getString("id");
                String influence = conList.getJSONObject(i).getString("influence");
                String content = conList.getJSONObject(i).getString("content");
                JSONObject jsonObject =conList.getJSONObject(i).getJSONObject("content");
                String result = this.doHttpPost(threadName,url,jsonObject);
                JSONObject word = JSONObject.parseObject(result);
                JSONArray recongList = JSONArray.parseArray(word.get("words").toString());
                String insertValueSql = "";
                for(int n=0;n<recongList.size();n++){
                    JSONObject obj = JSONObject.parseObject(recongList.get(n).toString());
                    if(!obj.isEmpty()){
                        //企业名称
                        String name = obj.getString("word");
                        String id = UUID.randomUUID().toString();
                        insertValueSql+="('"+id+"','"+itemId+"','"+name+"','"+influence+"','"+content+"'),";
                        list.add(name);
                    }
                }
                if(list.size()>0){
                    //保存数据库
                    int insert = this.insertEntName(jdbcTemplate,threadName,insertValueSql);
                    System.out.println(threadName+": "+"第"+i+"次插入数据库成功");
                    map.put(itemId,list);
                    mapList.add(map);
                    System.out.println(mapList);
                }else{
                    continue;
                }
            }
        }
    }

    /**
     * 接口调用方法
     * @param URL
     * @param jsonInfo
     * @return
     * @throws Exception
     */
    @ApiOperation("请求接口获取返回信息")
    public static String doHttpPost(String threadName,String URL,JSONObject jsonInfo) throws Exception {
        System.out.println(threadName+": "+"登录接口发起的数据:" + jsonInfo);
        InputStream instr = null;
        try {
            byte[] jsonData = jsonInfo.toString().getBytes("utf-8");
            java.net.URL url = new URL(URL);
            URLConnection urlCon = url.openConnection();
            urlCon.setDoOutput(true);//可以发生信息到URLConnection
            urlCon.setDoInput(true);//可以接受来自URLConnection的输入
            urlCon.setUseCaches(false);
            //SDK调用方式
            //Map<String,String> headers = AppSign.appSign("160125995737833","6616a90ca918064830a38f21c8260cc9755bada4");
            //String xAuthTime = headers.get("x-auth-time");
            //String xAuthAppKey = headers.get("x-auth-app-key");
            //String xAuthSign= headers.get("x-auth-sign");
            //urlCon.setRequestProperty("X-Auth-App-Key",xAuthAppKey);
            //urlCon.setRequestProperty("X-Auth-time",xAuthTime);
            //urlCon.setRequestProperty("X-Auth-Sign",xAuthSign);
            urlCon.setRequestProperty("content-Type", "application/json");//设置请求头
            urlCon.setRequestProperty("charset", "utf-8");
            urlCon.setRequestProperty("Content-length",String.valueOf(jsonData.length));
            //System.out.println("登录接口所传参数长度："+String.valueOf(jsonData.length));
            DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
            printout.write(jsonData);
            printout.flush();
            printout.close();
            instr = urlCon.getInputStream();
            byte[] bis = IOUtils.toByteArray(instr);
            String ResponseString = new String(bis, "UTF-8");
            if ((ResponseString == null) || ("".equals(ResponseString.trim()))) {
                System.out.println(threadName+": "+"登陆接口返回空");
            }
            System.out.println(threadName+": "+"登录接口返回数据为:" + ResponseString);
            return ResponseString;

        } catch (Error err) {
            System.out.println(threadName+": "+"登录接口发送的地址=" + URL + "登录接口发送的数据：" + jsonInfo + "登录接口发送数据失败：" + err.getMessage());
            //e.printStackTrace();
            throw new Exception( err.getMessage());
        } finally {
            try {
                instr.close();

            } catch (Exception ex) {
                return "{'words':[{},{}]}";
            }
        }
    }

    /**
     * 获取舆情处理文本,id,content,influence
     * @return
     */
    @ApiOperation("获取舆情处理文本")
    public JSONArray getContext(JdbcTemplate jdbcTemplate,int start,int end){
        JSONArray conList = new JSONArray();
        String sql = "select  id,content,influence from yuqing  limit "+start+","+end;
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        if(list.size()>0){
            for(int i=0;i<list.size();i++){
                Map<String,Object> map = list.get(i);
                JSONObject jsonobject = new JSONObject();
                JSONObject content =new JSONObject();
                String textCon = map.get("content")==null?"空文本":map.get("content").toString();
                content.put("text",textCon);
                content.put("lang","Chinese");
                content.put("access_token","123456");
                content.put("output_format","json");
                jsonobject.put("content",content);
                jsonobject.put("id",map.get("id").toString());
                jsonobject.put("content",textCon);
                jsonobject.put("influence",map.get("influence")==null?"0":map.get("influence").toString());
                conList.add(jsonobject);
            }
        }
        return conList;
    }


    /**
     * 插入数据库
     * @param insertValueSql
     * @return
     */
    @ApiOperation("保存至数据库")
    public int insertEntName(JdbcTemplate jdbcTemplate,String threadName,String insertValueSql){
        insertValueSql = insertValueSql.substring(0,insertValueSql.length()-1);
        System.out.println(threadName+": "+"插入语句为："+insertValueSql);
        int insert = jdbcTemplate.update("insert into yuqing_ent_temp(ID,ITEMID,ENTNAME,INFLUENCE,CONTENT) values "+insertValueSql);
        return insert;
    }
}
