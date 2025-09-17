package com.jeesite.modules.check;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jeesite.modules.check.bo.CheckBillExcelModel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pengxincheng
 * @date 2025/9/10 20:37
 * @description
 */

@Slf4j
public class CheckBillImportListener extends AnalysisEventListener<CheckBillExcelModel> {



    @Override
    public void invoke(CheckBillExcelModel checkBillExcelModel, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("解析完成，一共{}条数据",analysisContext.getTotalCount());

    }

}
