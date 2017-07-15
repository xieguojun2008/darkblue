package com.sinovatech.controller;

import com.sinovatech.entity.mapper.activity.Activity;
import com.sinovatech.entity.mapper.activity.ActivityMapper;
import com.sinovatech.entity.mapper.order.FlashOrderMapper;
import com.sinovatech.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Xieguojun on 2017/6/3.
 *
 */
@Controller
@RequestMapping("/flash")
public class FlashOrderController {
    @Autowired
    FlashOrderMapper orderMapper;
    @Autowired
    ActivityMapper activityMapper;

    @ResponseBody
    @RequestMapping("/addFlashActivity.action")
    public ModelAndView addActivity(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView("jsonView");
        Activity activity = new Activity();
        activity.setId(UUIDGenerator.genUUID());
        activity.setAcId("AC201706032049");
        activity.setAcName("DARKBLUE");
        activity.setAcType("flash");
        activity.setDescription("有心人");
        activity.setBeginTime(new Date());
        activity.setEndTime(new Date());
        int rowCount = activityMapper.addActivity(activity);
        if (rowCount == 1) {
            view.addObject(activity);
        }
        return view;
    }
}


