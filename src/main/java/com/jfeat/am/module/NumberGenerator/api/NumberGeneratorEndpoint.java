package com.jfeat.am.module.NumberGenerator.api;

import com.jfeat.am.common.constant.tips.SuccessTip;
import com.jfeat.am.common.constant.tips.Tip;
import com.jfeat.am.common.controller.BaseController;
import com.jfeat.am.module.NumberGenerator.services.crud.service.PoolService;
import com.jfeat.am.module.NumberGenerator.services.persistence.model.PoolConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * Created by Code Generator on 2017-12-05
 */
@RestController
@RequestMapping("/api/NumberGenerator")
public class NumberGeneratorEndpoint extends BaseController {


    @Autowired
    private PoolService poolService;

    @GetMapping("/random")
    public Tip getRandom(@RequestParam(name ="prefix",required = false)String prefix,@RequestParam(name = "suffix",required = false)String suffix){
        return SuccessTip.create(poolService.getSerialNumber(true,new PoolConfig(prefix,suffix)));
    }

    @GetMapping("/serial")
    public Tip getSerial(@RequestParam(name ="prefix",required = false)String prefix,@RequestParam(name = "suffix",required = false)String suffix){
        return SuccessTip.create(poolService.getSerialNumber(false,new PoolConfig(prefix,suffix)));
    }

    @GetMapping("/reback")
    public Tip rebackNumber(@RequestParam(name ="number",required = true)String num){
        poolService.reback(num);
        return SuccessTip.create();
    }

}