package com.jeesite.test;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.jeesite.modules.check.bo.CheckBillExcelModel;
import com.jeesite.modules.utils.Idutils;
import sun.misc.IOUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Test {

    public static void main(String[] args) throws Exception {

        List<CheckBillExcelModel> checkBillExcelModels = new ArrayList<>();
        CheckBillExcelModel checkBillExcelModel = new CheckBillExcelModel();
        checkBillExcelModel.setBillId("WX_201906001");
        checkBillExcelModel.setCarType("大型车");
        checkBillExcelModel.setCheckTime("");
        checkBillExcelModel.setCustomerName("张三");
        checkBillExcelModel.setPlateNumber("京A12334");
        checkBillExcelModel.setCustomerPhoneNumber(13011230320L);
        checkBillExcelModel.setNum(1L);
        checkBillExcelModel.setPrice(100.36);
        checkBillExcelModels.add(checkBillExcelModel);
        checkBillExcelModels.add(checkBillExcelModel);

        OutputStream out = new FileOutputStream("/Users/edz/Downloads/test.xlsx");
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0, CheckBillExcelModel.class);
            writer.write(checkBillExcelModels, sheet1);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @org.junit.Test
    public void testIdUtil() {

        ExecutorService executor = Executors.newFixedThreadPool(1000);

        for (int i = 0; i < 1000; i++) {

            //executor.execute(() -> System.out.println(Idutils.getNextId()));
        }

    }

}
