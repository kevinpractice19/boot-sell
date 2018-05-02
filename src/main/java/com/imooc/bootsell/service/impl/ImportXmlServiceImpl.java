package com.imooc.bootsell.service.impl;

import com.imooc.bootsell.entity.SellerInfo;
import com.imooc.bootsell.service.ImportXmlService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("importXmlService")
public class ImportXmlServiceImpl implements ImportXmlService {
    @Override
    public List<SellerInfo> selectSellerInfoList() {
        return null;
    }
}
