package com.jfeat.am.module.NumberGenerator.services.crud.service.impl;

import com.jfeat.am.module.NumberGenerator.services.crud.service.Identify;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * Created by craftperson on 2018/1/19.
 */
@Service
public class IdentifyImpl implements Identify {
    @Override
    public String addIdentify(String num) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        return num+getThreeAp(AESEncoder.AESEncoder(num));
    }

    @Override
    public boolean checkIdentify(String source) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        int length = source.length();
        String num = source.substring(0,length-3);
        String en = source.substring(length-3);
        if(getThreeAp(AESEncoder.AESEncoder(num)).equals(en)){
            return true;
        }else {
            return false;
        }
    }


    private static String getThreeAp(String content){
        StringBuilder stringBuilder = new StringBuilder();
        int length = content.length();
        for(int i=0;i<length-1;i++){
            char c = content.charAt(i);
            if(check(c)){
                stringBuilder.append(c);
            }
            if(stringBuilder.length()==3){
                break;
            }
        }
        return stringBuilder.toString();
    }

    private static boolean check(char c) {
        if(((c>='a'&&c<='z')   ||   (c>='A'&&c<='Z'))){
            return   true;
        }else{
            return   false;
        }
    }

}
