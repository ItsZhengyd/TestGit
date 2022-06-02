package com.example.demo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClientConfig;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class EnvTest {

    /**
     * 每隔5秒轮询一次环境
     */
    @Test
    public void envTest() {
        this.poll(5, TimeUnit.SECONDS);
    }

    /**
     * 轮询方法
     *
     * @param timeout 时间
     * @param unit    时间单位
     * @return
     */
    public synchronized Object poll(long timeout, TimeUnit unit) {
        while (true) {
            try {
                unit.timedWait(this, timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mysqlConnAnhui();
            mongoConnAnhui();
            redisConnAnhui();
        }
    }

    @Test
    public void mysqlConnAnhui() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3316/incai?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=round&serverTimezone=UTC", "root", "jiaoyuLIGUO2021");
            Console.log("{} MySQL连接成功", DateUtil.now());
        } catch (ClassNotFoundException e) {
            Console.error("{} MySQL驱动加载失败 {}", DateUtil.now(), e.getMessage());
        } catch (SQLException e) {
            Console.error("{} MySQL连接失败 {}", DateUtil.now(), e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                Console.error("{} MySQL连接关闭失败 {}", DateUtil.now(), e.getMessage());
            }
        }
    }

    @Test
    public void mongoConnAnhui() {
        MongoCredential credential = MongoCredential.createCredential("root", "admin", "jiaoyuLIGUO#2021".toCharArray());
        ServerAddress address = new ServerAddress("localhost", 3727);
        MongoClient client = null;
        try {
            client = new MongoClient(address, credential,MongoClientOptions.builder().build());
//            System.out.println("firstDatabase:" + client.listDatabases().first());
//            System.out.println("firstDocument:" + client.getDatabase("clearn_prod").listCollections().first());
            Console.log("{} MongoDB连接成功", DateUtil.now());
        }catch (Exception e){
            Console.error("{} MongoDB连接失败 {}", DateUtil.now(), e.getMessage());
        } finally {
            client.close();
        }
    }

    @Test
    public void redisConnAnhui() {
        JedisClientConfig config = new JedisClientConfig() {
            @Override
            public String getPassword() {
                return "Keyiyou1314";
            }
        };

        Jedis jedis = null;
        try {
            jedis = new Jedis("localhost", 6389, config);
            jedis.randomKey();
            Console.log("{} Redis连接成功", DateUtil.now());
        } catch (Exception e) {
            Console.error("{} Redis连接失败 {}", DateUtil.now(), e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }


    }

}
