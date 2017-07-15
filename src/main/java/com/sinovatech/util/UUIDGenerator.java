package com.sinovatech.util;

import java.util.UUID;

/**
 * Created by Xieguojun on 2017/6/3.
 *
 */
public class UUIDGenerator {
    public static String genUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
