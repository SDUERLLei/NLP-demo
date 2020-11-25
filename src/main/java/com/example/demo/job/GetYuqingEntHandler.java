package com.example.demo.job;

import com.example.demo.controller.TestController;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by liulei03 on 2020/10/19.
 */
@JobHandler(value="getYuqingEntHandler")
@Component
public class GetYuqingEntHandler extends IJobHandler {

    @Autowired
    TestController testController;
    @Override
    public ReturnT<String> execute(String s) throws Exception {
        testController.startAnalysis();
        return ReturnT.SUCCESS;
    }
}
