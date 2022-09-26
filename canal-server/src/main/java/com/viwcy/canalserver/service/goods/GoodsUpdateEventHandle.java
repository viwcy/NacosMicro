package com.viwcy.canalserver.service.goods;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.viwcy.canalserver.dto.GoodsDTO;
import com.viwcy.canalstater.constant.ColumnEnum;
import com.viwcy.canalstater.event.AbstractEventHandler;
import com.viwcy.canalstater.event.TableEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@TableEvent(database = "nacos_micro_mall", table = "goods", eventType = CanalEntry.EventType.UPDATE)
@Slf4j
@Component
public class GoodsUpdateEventHandle extends AbstractEventHandler<GoodsDTO> {

    @Override
    protected void doHandle(GoodsDTO dto) throws Exception {

        log.info("data = " + JSON.toJSONString(dto));
    }

    @Override
    protected ColumnEnum column() {
        return ColumnEnum.AFTER_COLUMN;
    }
}
