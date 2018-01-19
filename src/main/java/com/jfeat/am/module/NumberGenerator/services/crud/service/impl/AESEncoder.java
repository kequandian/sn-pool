package com.jfeat.am.module.NumberGenerator.services.crud.service.impl;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by craftperson on 2018/1/19.
 */
public class AESEncoder {
    private static  String AES="AES";
    private static String CHARSET = "utf-8";
    private static String encodeRules = "power";
    /*
     * AES对称加密
     * encodeRules 加密规则
     * content 加密字符串
     */
    public static  String AESEncoder(String content) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = initCipher(Cipher.ENCRYPT_MODE);
        byte[] byte_content = content.getBytes(CHARSET);//为了避免乱码，设置成utf-8，当然其他格式也行
        byte[] byte_AES = cipher.doFinal(byte_content);//对内容进行加密
        String AES_encode = new String(new BASE64Encoder().encode(byte_AES));//把获取的加密字节重新封装为字符串输出
        return  AES_encode;
    }

    public static String AESDecode(String encodeContent) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IOException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = initCipher(Cipher.DECRYPT_MODE);
        byte [] byte_content= new BASE64Decoder().decodeBuffer(encodeContent);
        byte[] content = cipher.doFinal(byte_content);
        return new String(content,CHARSET);
    }

    private static Cipher initCipher(Integer mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);//创建一个秘钥生成器，指定加密类型为AES对称加密
        SecureRandom secureRandom = new SecureRandom(encodeRules.getBytes());
        keyGenerator.init(128,secureRandom);//初始化生成器，使用128位加密，也可以192,256位
        SecretKey secretKey = new SecretKeySpec(keyGenerator.generateKey().getEncoded(),AES);//创建秘钥
        Cipher cipher = Cipher.getInstance(AES);//创建AES类型的密码器
        cipher.init(mode,secretKey);//初始化密码器，输入加密的秘钥
        return cipher;
    }

}
