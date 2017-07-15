package com.sinovatech.entity;

import org.apache.ibatis.type.Alias;

/**
 * Created by Xieguojun on 2017/4/27.
 *
 */
public class ActivityProduct {
    private String id;
    private String productName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
