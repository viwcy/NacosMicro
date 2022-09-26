package com.viwcy.canalstater.event;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.viwcy.canalstater.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 事件处理 工厂
 */
public class EventHandlerFactory {

    private static final Logger log = LoggerFactory.getLogger(EventHandlerFactory.class);

    private Map<String, IEventHandle> _handle = new ConcurrentHashMap<>();

    private Map<String, CanalEntry.EventType> _eventType = new ConcurrentHashMap<>();

    public void setEventType(String key, CanalEntry.EventType eventType) {
        _eventType.put(key, eventType);
    }

    public CanalEntry.EventType getEventType(String key) {
        return _eventType.get(key);
    }

    public IEventHandle getHandler(String key) {
        return _handle.get(key);
    }

    public void setHandler(String key, IEventHandle handler) {
        _handle.put(key, handler);
    }

    public static String createUnionKey(String schemaName, String tableName, CanalEntry.EventType eventType) {
        return schemaName + "-" + tableName + "-" + eventType.getValueDescriptor().getName();
    }

    public void register() {

        //手动获取所有子类执行器
        Map<String, AbstractEventHandler> eventHandlerMap = SpringContextUtil.getBeansByType(AbstractEventHandler.class);

        //注册handle
        for (Map.Entry<String, AbstractEventHandler> eventHandlerEntry : eventHandlerMap.entrySet()) {
            AbstractEventHandler eventHandler = eventHandlerEntry.getValue();
            Class<? extends AbstractEventHandler> eventHandlerClass = eventHandler.getClass();
            TableEvent annotation = eventHandlerClass.getAnnotation(TableEvent.class);
            if (annotation == null) {
                continue;
            }
            CanalEntry.EventType eventType = annotation.eventType();
            String unionKey = createUnionKey(annotation.database(), annotation.table(), eventType);
            String simpleName = eventHandler.getClass().getSimpleName();
            log.info("Auto loading handler，unionKey = " + unionKey + "，handle = " + simpleName);
            setHandler(unionKey, eventHandler);
            setEventType(simpleName, eventType);
        }
    }
}
