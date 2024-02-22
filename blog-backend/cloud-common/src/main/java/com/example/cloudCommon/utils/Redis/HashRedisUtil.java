package com.example.cloudCommon.utils.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class HashRedisUtil extends RedisUtil{
    @Autowired
    private HashOperations<String,String,Object> hash;

    public <T> boolean set(String key,String hashKey,T obj){
        try {
            hash.put(key,hashKey,obj);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public <T> int setMore(String key, Map<String,T> map){
        try {
            hash.putAll(key,map);
            return map.size();
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public<T> T get(String key,String hashKey){
        try {
            return (T) hash.get(key,hashKey);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> List<T> getMore(String key,String... hashKeys){
        try {
            return hash.multiGet(key, Arrays.stream(hashKeys).collect(Collectors.toList()))
                    .stream().map(o -> (T)o).collect(Collectors.toList());
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>(0);
        }
    }

    public <T> Map<String,T> getAll(String key){
        try {
            return hash.entries(key)
                    .entrySet()
                    .stream()
                    .map(e -> Map.entry(e.getKey(),(T)e.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public<T> List<T> getAllValues(String key){
        try {
            return hash.values(key).stream().map(o -> (T)o).collect(Collectors.toList());
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>(0);
        }
    }

    public <T> boolean setNoExist(String key,String hashKey,T obj){
        try {
            hash.putIfAbsent(key,hashKey,obj);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteOne(String key,String hashKeys){
        try {
            hash.delete(key, hashKeys);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean hashKeyExist(String key,String hashKey){
        try {
            return hash.hasKey(key,hashKey);
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Double incr(String key,String hashKey,Double number){
        try {
            return hash.increment(key,hashKey,number);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Double incr(String key,String hashKey){
        try {
            return hash.increment(key,hashKey,1D);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
