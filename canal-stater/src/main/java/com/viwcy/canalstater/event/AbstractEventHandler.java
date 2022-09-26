package com.viwcy.canalstater.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.viwcy.canalstater.constant.ColumnEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * 事件处理模板
 */
public abstract class AbstractEventHandler<T> implements IEventHandle {

   protected static final Logger log = LoggerFactory.getLogger(AbstractEventHandler.class);

    @Autowired
    private DataParser dataParser;
    @Autowired(required = false)
    private EventHandlerFactory eventHandlerFactory;

    //流程框架
    @Override
    public void handle(CanalEntry.RowData rowData) {

        log.info("event handle start");
        check(rowData);
        T t = analysis(rowData);
        if (Objects.isNull(t)) {
            return;
        }

        try {
            this.doHandle(t);
        } catch (Exception e) {
            log.error("canal handle has error , e = " + e);
            //TODO  业务消费失败之后，补偿入库，定时任务消费
            if (!Objects.isNull(t)) {
                //事件操作类型
                eventHandlerFactory.getEventType(this.getClass().getSimpleName());
                log.info("compensation has been completed , t = " + JSON.toJSONString(t));
            }
        }

        log.info("event handle end");
    }

    private void check(CanalEntry.RowData rowData) {

        if (Objects.isNull(rowData)) {
            log.debug("AbstractEventHandler#check rowData is null");
            return;
        }
    }

    protected abstract void doHandle(T t) throws Exception;

    protected abstract ColumnEnum column();

    private final T analysis(final CanalEntry.RowData rowData) {

        if (Objects.isNull(rowData)) {
            return null;
        }
        Class<T> clazz = this.handleClass();
        return this.dataParser.parse(clazz, rowData, this.column());
    }

    private final Class<T> handleClass() {
        Class clazz = this.getClass();
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        return (Class<T>) types[0];
    }
}
