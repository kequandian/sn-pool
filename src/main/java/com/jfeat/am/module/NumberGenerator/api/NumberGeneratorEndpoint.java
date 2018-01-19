package com.jfeat.am.module.NumberGenerator.api;

import com.jfeat.am.common.constant.tips.SuccessTip;
import com.jfeat.am.common.constant.tips.Tip;
import com.jfeat.am.common.controller.BaseController;
import com.jfeat.am.module.NumberGenerator.services.crud.service.Identify;
import com.jfeat.am.module.NumberGenerator.services.crud.service.PoolService;
import com.jfeat.am.module.NumberGenerator.config.PoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Code Generator on 2017-12-05
 */
@RestController
@RequestMapping("/api/pub/sn")
public class NumberGeneratorEndpoint extends BaseController {

    @Autowired
    private PoolService poolService;

    @Autowired
    private Identify identify;

    @GetMapping("/random")
    public Tip getRandom(@RequestParam(name ="prefix",required = false)String prefix,@RequestParam(name = "suffix",required = false)String suffix) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        return SuccessTip.create(identify.addIdentify(poolService.getSerialNumber(true, new PoolConfig(prefix, suffix))));
    }

    @GetMapping("/serial")
    public Tip getSerial(@RequestParam(name ="prefix",required = false)String prefix,@RequestParam(name = "suffix",required = false)String suffix) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        return SuccessTip.create(identify.addIdentify(poolService.getSerialNumber(false,new PoolConfig(prefix,suffix))));
    }

    @GetMapping("/rollback")
    public Tip rollbackNumber(@RequestParam(name ="number",required = true)String number) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        String num = number.substring(0,number.length()-3);
        if(identify.checkIdentify(number)){
            poolService.rollback(num);
        }else{
            throw new IllegalArgumentException("rollback number is illegal");
        }
        return SuccessTip.create();
    }

}