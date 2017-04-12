package com.aiyaschool.aiya.message.cache;

import android.util.LruCache;

/**
 * Created by ShootHzj on 2016/11/2.
 */

public class Cache {
    public static LruCache<String,byte[]> memoryImageCache;

    public static LruCache<String, byte[]> getMemoryImageCache() {
        if(memoryImageCache==null){
            int maxMemory = (int) Runtime.getRuntime().maxMemory();
            int size = maxMemory/20;
            memoryImageCache  = new LruCache<String,byte[]>(size){
                @Override
                protected int sizeOf(String key, byte[] value) {
                    return value.length;
                }
            };
        }
        return memoryImageCache;
    }
}
