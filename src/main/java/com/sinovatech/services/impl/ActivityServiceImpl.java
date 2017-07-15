package com.sinovatech.services.impl;

import com.sinovatech.services.IActivityService;
import com.sinovatech.MyBatisSessionUtil;
import com.sinovatech.entity.mapper.activity.Activity;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Xieguojun on 2017/4/17.
 *
 */
public class ActivityServiceImpl implements IActivityService {
    Logger logger = LoggerFactory.getLogger(IActivityService.class);

    public String getActivityInfoById(String actId) {
        logger.info("service getActivityInfoById start");
        SqlSession sqlSession = MyBatisSessionUtil.getSessionFactory().openSession();
        Activity activity = (Activity) sqlSession.selectOne("com.sinovatech.entity.mapper.activity.ActivityMapper.getActivity", actId);
        logger.info("service getActivityInfoById end");
        return com.alibaba.fastjson.JSON.toJSONString(activity);
    }

    public String getFlashOrderByAcId(String acId) {
        logger.info("service getFlashOrderByAcId start");
        SqlSession sqlSession = MyBatisSessionUtil.getSessionFactory().openSession();
        List orderList = sqlSession.selectList("com.sinovatech.entity.mapper.order.FlashOrderMapper.getFlashOrder", acId);
        logger.info("service getFlashOrderByAcId end");
        return com.alibaba.fastjson.JSON.toJSONString(orderList);
    }
}
