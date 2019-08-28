package com.simple.controller;

import com.simple.entiy.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.nio.cs.UTF_32;

import java.util.*;

/**
 * @author songabao
 * @date 2019/8/26 15:04
 */
@RestController
@RequestMapping(value = "redis")
public class SimpleRedisController {
    @Autowired
    private RedisTemplate redisCacheTemplate;

    /**
     * String的使用
     * 方法：get,set,delete
     */
    @GetMapping(value = "string")
    public void simpleString(){
        redisCacheTemplate.opsForValue().set("count",20);
        Object count = redisCacheTemplate.opsForValue().get("count");
        System.out.println(count);
        redisCacheTemplate.delete("count");
    }

    /**
     * linked-list
     */
    @GetMapping(value = "linkedList")
    public String simpleLinkedList(){
        String key = "linked-list";
        //删除这个key
        redisCacheTemplate.delete(key);
//        //在右边添加
//      redisCacheTemplate.opsForList().rightPush(key,1);
//       // 在右边添加
//      redisCacheTemplate.opsForList().rightPush(key,2);
//      //  在左边添加
//      redisCacheTemplate.opsForList().leftPush(key,3);
        //添加一个list集合数据
        Collection con = new ArrayList();
        con.add(111);
        con.add(222);
        con.add(333);
        con.add(444);
        con.add(555);
        con.add(666);
        redisCacheTemplate.opsForList().rightPushAll(key,con);
//        //返回左边第一个（猜想111）并且会删除返回的值
//        Integer lIndex =(Integer) redisCacheTemplate.opsForList().leftPop(key);
//        //返回右边第一个(猜想666)并且会删除返回的值
//        Integer rIndex= (Integer)redisCacheTemplate.opsForList().rightPop(key);
        //返回给定索引的值(猜想222)
        Object index = redisCacheTemplate.opsForList().index(key, 1);
        //返回范围的值（猜想全部）
        List range = redisCacheTemplate.opsForList().range(key, 0, -1);
//        return "返回左边第一个: "+lIndex+",返回右边第一个: "+rIndex+",返回给定索引的值是："+index
//                +"返回范围的值是："+ range;
        return "返回给定索引的值是："+index +"\n\t"+" 返回范围的值是："+ range;
    }

    /**
     * set-key
     */
    @GetMapping(value = "setKey")
    public String simpleSetKey(){
        String key = "set-key";
        //删除key
        redisCacheTemplate.delete(key);
        //添加元素
//        redisCacheTemplate.opsForSet().add(key,"1111");
//        redisCacheTemplate.opsForSet().add(key,"2222");
        String[] str = new String[7];
        str[0]="3333";
        str[1]="4444";
        str[2]="5555";
        str[3]="6666";
        str[4]="7777";
        str[5]="8888";
        str[6]="9999";
        redisCacheTemplate.opsForSet().add(key,str);
        //获取全部
        Set members = redisCacheTemplate.opsForSet().members(key);
        //随机返回
        String randomMember =(String) redisCacheTemplate.opsForSet().randomMember(key);
        //随机返回多个
        List list = redisCacheTemplate.opsForSet().randomMembers(key, 3);
        //判断是否存在某个元素
        Boolean member = redisCacheTemplate.opsForSet().isMember(key, "2222");
        //删除集合中元素(成功返回删除数量)
        Long remove = redisCacheTemplate.opsForSet().remove(key, "2222");
        return "获取全部："+members+", 是否存在某个元素："+member+",删除集合中的元素："+remove+"\n"
                +"随机返回的数为："+randomMember+"\n"+"随机返回多个数："+list;
    }

    /**
     * hash-key
     */
    @GetMapping(value = "hashKey")
    public String hashKey(){
        String key="user";
//        User user = new User();
//        user.setId(1);
//        user.setName("songabao");
//        user.setAge(23);
//        user.setSex(1);
//        user.setCreateDate(new Date());
//        //存入用户id
//        redisCacheTemplate.opsForHash().put(key,user.getId(),user);
        //获取单个
        User user1= (User)redisCacheTemplate.opsForHash().get(key, 1);
        //获取全部
        Set keys = redisCacheTemplate.opsForHash().keys(key);
        return "获取单个是："+user1+"\n"+"获取全部是："+keys;
    }

    /**
     * zset-key
     */
    @GetMapping(value = "zsetKey")
    public  Set zsetKey(){
       String key ="zset-key";
        long l = System.currentTimeMillis();
        //添加
        redisCacheTemplate.opsForZSet().add(key,9,(double) (l+1000));
        redisCacheTemplate.opsForZSet().add(key,8,(double)(l+100));
        redisCacheTemplate.opsForZSet().add(key,7,(double)l);
        Set range = redisCacheTemplate.opsForZSet().range(key, 0, 3);
        return  range;
    }

}
