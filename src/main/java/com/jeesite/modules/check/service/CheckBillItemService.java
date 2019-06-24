package com.jeesite.modules.check.service;

import com.jeesite.common.service.CrudService;
import com.jeesite.modules.check.dao.CheckBillItemDao;
import com.jeesite.modules.check.entity.CheckBillItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description
 * @author: pengxincheng
 * @date: 2019-06-24 14:46
 */
@Service
@Transactional(readOnly=true)
public class CheckBillItemService extends CrudService<CheckBillItemDao, CheckBillItem> {

    @Override
    public List<CheckBillItem> findList(CheckBillItem entity) {
        return super.findList(entity);
    }
}
