package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liulei03 on 2020/8/11.
 */
@Service("testService")
public class TestService
{
    @Autowired
    @Qualifier("ds1JdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getSersordata(){
        List<Map<String,Object>>list = null;
        list = jdbcTemplate.queryForList("select * from sensor");
        return list;
    }


}
