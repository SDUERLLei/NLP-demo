package com.example.demo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 常量类
 * Created by liulei03 on 2020/10/21.
 */
@Component
@PropertySource("classpath:/constant.properties")
public class Constant {

    @Value("${thread.size}")
    private int threadSize;

    @Value("${data.start}")
    private int dataStart;

    @Value("${data.end}")
    private int dataEnd;


    public int getDataStart() {
        return dataStart;
    }

    public void setDataStart(int dataStart) {
        this.dataStart = dataStart;
    }

    public int getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(int dataEnd) {
        this.dataEnd = dataEnd;
    }

    public int getThreadSize() {
        return threadSize;
    }

    public void setThreadSize(int threadSize) {
        this.threadSize = threadSize;
    }


}
