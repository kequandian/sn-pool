package com.jfeat.am.module.NumberGenerator.services.crud.service.impl;
            
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.NumberGenerator.services.persistence.model.Pool;
import com.jfeat.am.module.NumberGenerator.services.persistence.dao.PoolMapper;
import com.jfeat.am.module.NumberGenerator.services.crud.service.PoolService;
import com.jfeat.am.common.crud.impl.CRUDServiceOnlyImpl;
import com.jfeat.am.module.NumberGenerator.services.persistence.model.PoolConfig;
import com.sun.javafx.binding.StringFormatter;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 * 订单池 implementation
 * </p>
 *
 * @author Code Generator
 * @since 2017-12-05
 */
@Service
public class PoolServiceImpl  extends CRUDServiceOnlyImpl<Pool> implements PoolService {


    @Resource
    private PoolMapper poolMapper;

    @Override
    protected BaseMapper<Pool> getMasterMapper() {
        return poolMapper;
    }

    public static List<Pool> pools;


    @Resource
    private PoolConfig poolConfig;



    public static String dateForMark = "0";

    public static Integer pageNumber;


    @Override
    public void initPool() {
        this.pools = new CopyOnWriteArrayList<>();
        List<String> tables = poolMapper.showTables();
        if((!tables.contains("orderPool"))&&(!tables.contains("orderpool"))){
            poolMapper.initTable();
        }

        Integer integer = poolMapper.selectCount(new EntityWrapper<Pool>());

        List<Pool> pools = new CopyOnWriteArrayList<Pool>();
        String l = poolConfig.getLength()==null?4+"":poolConfig.getLength();
        Integer length = Integer.parseInt(l);
        String number = "";
        for(int i = 0;i<length;i++){
            number = number + "" +9;
        }

        long parseLong = Long.parseLong(number);
        if(integer>=(parseLong+1)){
            Page p = new Page();
            p.setSize(3000);
            Pool pp = new Pool();
            pp.setIsUsed(0);
            this.pools=poolMapper.selectPage(p,new EntityWrapper<>(pp));
            return;
        }
        for(long k = 0;k<=parseLong;k++){
            int i = length - String.valueOf(k).length();
            Pool pool = new Pool();
            pool.setIsUsed(0);
            String n = k+"";
            for(int j=0;j<i;j++){
                n=0+n;
            }

            pool.setNumber(n);
            pools.add(pool);
            if(pools.size()>20000){
                poolMapper.batchInsert(pools);
                pools.clear();
            }
        }
        poolMapper.batchInsert(pools);


        addNumber(true);
    }

    private void addNumber(boolean isFlag){
        if(isFlag){
            Page page = new Page();
            page.setSize(3000);
            page.setCurrent(1);
            pageNumber = 1;
            Pool pp = new Pool();
            pp.setIsUsed(0);
            pools = poolMapper.selectPage(page, new EntityWrapper<>(pp));
        }else{
            pageNumber++;
            Page page = new Page();
            page.setSize(2400);
            page.setCurrent(pageNumber);
            Pool pp = new Pool();
            pp.setIsUsed(0);
            List<Pool> poolList = poolMapper.selectPage(page, new EntityWrapper<>(pp));
            pools.addAll(poolList);
        }
    }


    @Override
    @Transactional
    public String getSerialNumber(boolean isFlag,PoolConfig poConfig) {
        Date date = new Date();
        String dateMark = new SimpleDateFormat("yyyyMMdd").format(date);

        String prefix = poConfig.getPrefix()==null?"":poConfig.getPrefix();//前缀
        String suffix = poConfig.getSuffix()==null?"":poConfig.getSuffix();//后缀

        if(dateMark.compareTo(dateForMark)>0){//第二天
            poolMapper.clearAll();
            addNumber(true);
        }

        dateForMark = dateMark;
        if(pools.size()<600){
            addNumber(false);
        }
        if(isFlag){
            int i = pools.size() - 1;
            Random random = new Random();
            int nextInt = random.nextInt(i);
            Pool remove = pools.get(nextInt);
            poolMapper.setUsed(remove);
            return prefix+formatDateSpecial(date)+pools.remove(nextInt).getNumber()+suffix;
        }

        poolMapper.setUsed(pools.get(0));
        return prefix+formatDateSpecial(date)+pools.remove(0).getNumber()+suffix;

    }

    @Override
    public void backupData() {

    }

    @Override
    public String formatDateSpecial(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMyyyydd");
        String format = simpleDateFormat.format(date);
        String left = format.substring(0,2);
        String left2 =format.substring(2,4);
        String right =format.substring(4,6);
        String right2 =format.substring(6,8);
        return left+left2+right2+right;
    }

    @Override
    public void reback(long num) {
        Pool pool = new Pool();
        String n = String.valueOf(num);
        n=n.substring(8);

        pool.setNumber(n);
        pool.setIsUsed(0);
        pools.add(pool);

        poolMapper.reback(pool);
    }


}


