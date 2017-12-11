package com.jfeat.am.module.NumberGenerator.api;

import com.jfeat.am.common.constant.tips.SuccessTip;
import com.jfeat.am.common.constant.tips.Tip;
import com.jfeat.am.common.controller.BaseController;
import com.jfeat.am.module.NumberGenerator.services.crud.service.PoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Code Generator on 2017-12-05
 */
@RestController
@RequestMapping("/api/NumberGenerator")
public class PatchEndpoint extends BaseController {


    @Autowired
    private PoolService poolService;

    @GetMapping("/random")
    public Tip getRandom(){
        return SuccessTip.create(poolService.getSerialNumber(true));
    }

    @GetMapping("/serial")
    public Tip getSerial(){
        return SuccessTip.create(poolService.getSerialNumber(false));
    }

}