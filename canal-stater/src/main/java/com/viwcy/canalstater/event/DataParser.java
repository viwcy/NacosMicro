package com.viwcy.canalstater.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.viwcy.canalstater.constant.ColumnEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * rowData解析成json
 */
@Component
public class DataParser {

    /**
     * rowData解析成json
     */
    public JSONObject parse(CanalEntry.RowData rowData, ColumnEnum column) {
        List<CanalEntry.Column> columnsList = new ArrayList<>();
        if (column == ColumnEnum.AFTER_COLUMN) {
            columnsList = rowData.getAfterColumnsList();
        }
        if (column == ColumnEnum.BEFORE_COLUMN) {
            columnsList = rowData.getBeforeColumnsList();
        }
        JSONObject dataJson = new JSONObject(columnsList.size());
        for (CanalEntry.Column var : columnsList) {
            //解决datetime设置小数点位数之后，解析报错问题，去掉后面小数点精度，业务不影响
            if (var.getMysqlType().contains("datetime")) {
                String substring = StringUtils.isBlank(var.getValue()) ? "" : var.getValue().substring(0, 19);
                dataJson.put(var.getName(), substring);
            } else {
                dataJson.put(var.getName(), var.getValue());
            }
        }
        return dataJson;
    }

    /**
     * 解析对应实体
     */
    public <T> T parse(Class<T> clazz, CanalEntry.RowData rowData, ColumnEnum column) {
        JSONObject parse = parse(rowData, column);
        return JSON.parseObject(parse.toString(), clazz);
    }
}
