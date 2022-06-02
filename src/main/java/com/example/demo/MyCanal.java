package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyCanal {

    public static void main(String[] args) {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1",
                11111), "example", "canal", "canal");
        int batchSize = 1000;
        int emptyCount = 0;
        try {
            while (true) {
                connector.connect();
                connector.subscribe("yipeide.*");
                Message message = connector.get(100);
                List<CanalEntry.Entry> entries = message.getEntries();
                if (entries.size() == 0) {
                    System.out.println("没有数据" + emptyCount++);
                    TimeUnit.SECONDS.sleep(1);
                } else {
                    System.out.println("读取到数据=============================");
                    for (CanalEntry.Entry entry : entries) {
                        String tableName = entry.getHeader().getTableName();
                        CanalEntry.EntryType entryType = entry.getEntryType();
                        ByteString storeValue = entry.getStoreValue();
                        if(CanalEntry.EntryType.ROWDATA.equals(entryType)){
                            System.out.println("tableName = " + tableName);
                            // 反序列化数据
                            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(storeValue);
                            // 获取当前时间操作类型
                            CanalEntry.EventType eventType = rowChange.getEventType();
                            // 获取数据集
                            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
                            for (CanalEntry.RowData rowData : rowDatasList) {
                                JSONObject beforeData = new JSONObject();
                                List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
                                for (CanalEntry.Column column : beforeColumnsList) {
                                    beforeData.put(column.getName(),column.getValue());
                                }

                                JSONObject afterData = new JSONObject();
                                List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
                                for (CanalEntry.Column column : afterColumnsList) {
                                    afterData.put(column.getName(), column.getValue());
                                }
                                System.out.println("beforeData = " + beforeData);
                                System.out.println("afterData = " + afterData);
                            }

                        } else {
                            System.out.println("entryType = " + entryType);
                        }
                    }
                }
            }

        } catch (InterruptedException | InvalidProtocolBufferException e) {
            e.printStackTrace();
        } finally {
            System.out.println("异常中止");
            connector.disconnect();
        }
    }
}
