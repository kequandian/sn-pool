package com.jfeat.am.module.NumberGenerator.api;

import com.jfeat.am.module.NumberGenerator.services.crud.service.Identify;
import com.jfeat.am.module.NumberGenerator.services.crud.service.PoolService;
import com.jfeat.am.module.NumberGenerator.config.PoolConfig;
import com.jfeat.am.module.NumberGenerator.services.crud.service.impl.AESEncoder;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
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
public class NumberGeneratorEndpoint {

    @Autowired
    private PoolService poolService;

    @Autowired
    private Identify identify;
    @Autowired
    private PoolConfig poolConfig;

    @GetMapping("/random")
    public Tip getRandom(@RequestParam(name ="prefix",required = false)String prefix, @RequestParam(name = "suffix",required = false)String suffix) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        return SuccessTip.create(identify.addIdentify(poolService.getSerialNumber(true, new PoolConfig(prefix, suffix))));
    }

    @GetMapping("/serial")
    public Tip getSerial(@RequestParam(name ="prefix",required = false)String prefix,@RequestParam(name = "suffix",required = false)String suffix) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        return SuccessTip.create(identify.addIdentify(poolService.getSerialNumber(false,new PoolConfig(prefix,suffix))));
    }

    @GetMapping("/rollback")
    public Tip rollbackNumber(@RequestParam(name ="number",required = true)String number) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        String num = number.substring(0,number.length()-3);
        if(poolConfig != null && !poolConfig.getHasKey()) {
            poolService.rollback(number);
        } else if(identify.checkIdentify(number)){
            poolService.rollback(num);
        }else{
            throw new IllegalArgumentException("rollback number is illegal");
        }
        return SuccessTip.create();
    }

    @GetMapping("/test")
    public Tip test(@RequestParam(name="number",required = true) String number) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        return SuccessTip.create(AESEncoder.AESEncoder(number));
    }

}