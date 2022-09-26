package com.viwcy.canalserver.service.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.viwcy.canalserver.dto.UserDTO;
import com.viwcy.canalstater.constant.ColumnEnum;
import com.viwcy.canalstater.event.AbstractEventHandler;
import com.viwcy.canalstater.event.TableEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@TableEvent(database = "nacos_micro_custom", table = "user", eventType = CanalEntry.EventType.UPDATE)
@Slf4j
@Component
public class UserUpdateEventHandle extends AbstractEventHandler<UserDTO> {

    @Override
    protected void doHandle(UserDTO userDTO) throws Exception {

        log.info("data = " + JSON.toJSONString(userDTO));
    }

    @Override
    protected ColumnEnum column() {
        return ColumnEnum.AFTER_COLUMN;
    }
}
