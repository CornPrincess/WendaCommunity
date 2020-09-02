package com.minmin.wenda.util;

/**
 * Created by IntelliJ IDEA.
 * User: zhoutianbin
 * Date: 2020-09-02
 * Time: 22:36
 */
public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";

    public static String getLikeKey(int entityType, int entityId) {
        return BIZ_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getDisLikeKey(int entityType, int entityId) {
        return BIZ_DISLIKE + SPLIT + entityType + SPLIT + entityId;
    }
}
