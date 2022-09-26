package com.viwcy.canalstater.event;

import com.alibaba.otter.canal.protocol.CanalEntry;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
public interface IEventHandle {

    void handle(CanalEntry.RowData rowData);
}
