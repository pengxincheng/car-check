package com.jeesite.modules.check.bo;

import com.alibaba.excel.event.WriteHandler;
import org.apache.poi.ss.usermodel.*;

/**
 * Created by pxc on 2019/7/23.
 */
public class StyleHandler implements WriteHandler {

    @Override
    public void sheet(int i, Sheet sheet) {

    }

    @Override
    public void row(int i, Row row) {

    }

    @Override
    public void cell(int i, Cell cell) {
        if (i == 8 || i == 9) {
           cell.setCellType(CellType.NUMERIC);
        }
    }
}
