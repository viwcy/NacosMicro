package com.viwcy.canalstater.event;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;
import com.google.protobuf.InvalidProtocolBufferException;
import com.viwcy.canalstater.config.CanalConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Slf4j
public class CanalDataListener {

    private final CanalConnector canalConnector;
    private final CanalConfigProperties canalConfigProperties;
    private final EventHandlerFactory factory;

    public CanalDataListener(CanalConnector canalConnector, CanalConfigProperties canalConfigProperties, EventHandlerFactory factory) {
        this.canalConnector = canalConnector;
        this.canalConfigProperties = canalConfigProperties;
        this.factory = factory;
    }

    /**
     * 最好不要无限循环，太消耗资源，建议任务调度，2-5s拉取一次
     */
    @Scheduled(initialDelayString = "#{@initialDelay}", fixedDelayString = "#{@fixedDelay}")
    public void pull() {

        log.debug("canal scheduled task pull data");

        long batchId = -1;
        try {
            Message message = canalConnector.getWithoutAck(canalConfigProperties.getBatchSize());
            batchId = message.getId();
            List<CanalEntry.Entry> entries = message.getEntries();
            log.debug("batchId = " + batchId);
            if (batchId == -1 || CollectionUtils.isEmpty(entries)) {
                return;
            }
            //过滤不需要处理的数据类型
            List<CanalEntry.Entry> collect = entries.stream().filter(entry -> (entry.getEntryType() != CanalEntry.EntryType.TRANSACTIONBEGIN && entry.getEntryType() != CanalEntry.EntryType.TRANSACTIONEND)).collect(Collectors.toList());
            handle(collect);
            log.info("batchId = {}, has been processed", batchId);
        } catch (CanalClientException e) {
            e.printStackTrace();
//            canalConnector.rollback(batchId);
        }finally {
            if (batchId != -1) {
                canalConnector.ack(batchId);
                log.info("ack success , batchId = " + batchId);
            }
        }
    }

    /**
     * 这里的entries，已经经过了过滤，比如MySQL批处理更新了两条数据，则entries就是两条
     */
    private void handle(List<CanalEntry.Entry> entries) {

        if (CollectionUtils.isEmpty(entries)) {
            return;
        }

        for (CanalEntry.Entry entry : entries) {
            CanalEntry.RowChange rowChange = null;
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }

            if (Objects.isNull(rowChange)) {
                continue;
            }
            CanalEntry.EventType eventType = rowChange.getEventType();
            String schemaName = entry.getHeader().getSchemaName();
            String tableName = entry.getHeader().getTableName();

            List<CanalEntry.RowData> rowDataList = rowChange.getRowDatasList();
            if (CollectionUtils.isEmpty(rowDataList)) {
                continue;
            }
            IEventHandle handler = factory.getHandler(EventHandlerFactory.createUnionKey(schemaName, tableName, eventType));
            if (Objects.isNull(handler)) {
                continue;
            }
            log.info("query event handle = " + handler.getClass().getSimpleName());
            handler.handle(rowDataList.get(0));
        }
    }

}
