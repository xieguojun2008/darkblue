package com.sinovatech.entity.mapper.order;

import com.sinovatech.entity.ActivityProduct;
import com.sinovatech.entity.mapper.activity.Activity;

import java.sql.Date;

/**
 * Created by Xieguojun on 2017/4/26.
 *
 */
public class FlashOrder {
    private String id;
    private String userId;
    private Activity activity;
    private ActivityProduct activityProduct;
    private String periodId;
    private Date orderTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ActivityProduct getActivityProduct() {
        return activityProduct;
    }

    public void setActivityProduct(ActivityProduct activityProduct) {
        this.activityProduct = activityProduct;
    }

    public String getPeriodId() {
        return periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}
