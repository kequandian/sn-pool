package com.jfeat.am.module.NumberGenerator.services.crud.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.common.exception.BizExceptionEnum;
import com.jfeat.am.common.exception.BusinessException;
import com.jfeat.am.module.NumberGenerator.config.PageForPool;
import com.jfeat.am.module.NumberGenerator.services.persistence.model.Pool;
import com.jfeat.am.module.NumberGenerator.services.persistence.dao.PoolMapper;
import com.jfeat.am.module.NumberGenerator.services.crud.service.PoolService;
import com.jfeat.am.common.crud.impl.CRUDServiceOnlyImpl;
import com.jfeat.am.module.NumberGenerator.config.PoolConfig;
import com.jfeat.am.module.NumberGenerator.services.persistence.model.PoolModel;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
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
public class PoolServiceImpl extends CRUDServiceOnlyImpl<Pool> implements PoolService {

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @Resource
    private PoolMapper poolMapper;

    @Override
    protected BaseMapper<Pool> getMasterMapper() {
        return poolMapper;
    }

    public static List<Pool> pools;

    private static Integer num = 0;

    @Resource
    private PoolConfig poolConfig;

    private static Map<String, List<Pool>> prefixesMap = new HashMap<>();//存放所有的前缀及号码

    private static Map<String, List<Pool>> suffixesMap = new HashMap<>();//存放所有的后缀及号码

    private static Map<String, Integer> prefixesPageNumbers = new HashMap<>();//存放所有前缀使用到的页码

    private static Map<String, Integer> suffixesPageNumbers = new HashMap<>();//存放所有后缀使用到的页码

    public static String dateForMark = "0";

    public static Integer pageNumber = 1;

    public static Long poolsCount;

    private static List<String> keySetlist;

    private static List<String> rebackNumber = new ArrayList<>();

    private void initTable() throws SQLException {
        List<String> tables = poolMapper.showTables();
        if ((!tables.contains("orderPool")) && (!tables.contains("orderpool"))) {
            poolMapper.initTable();
        }

        Connection conn = sqlSessionFactory.openSession().getConnection();
        DatabaseMetaData data = conn.getMetaData();
        ResultSet rs = data.getColumns(null, null, "orderPool", "%");
        List<String> columns = new ArrayList<>();
        while (rs.next()) {
            columns.add(rs.getString("COLUMN_NAME"));
        }

        List<String> prefixes = poolConfig.getPrefixes();
        if (prefixes != null && prefixes.size() > 0 && (!prefixes.get(0).equals("${prefixes}"))) {
            for (String prefix : prefixes) {
                String prefixStr = prefix + "IsUsed";
                if (!columns.contains(prefixStr)) {
                    poolMapper.addPrefix(prefixStr);
                }
            }
        }
        List<String> suffixes = poolConfig.getSuffixes();
        if (suffixes != null && suffixes.size() > 0 && (!suffixes.get(0).equals("${suffixes}"))) {
            for (String suffix : suffixes) {
                String suffixStr = "IsUsed" + suffix;
                if (!columns.contains(suffixStr)) {
                    poolMapper.addPrefix(suffixStr);
                }
            }
        }
    }

    private void initData() {

        Integer integer = poolMapper.selectCount(new EntityWrapper<Pool>());

        List<Pool> pools = new CopyOnWriteArrayList<Pool>();
        String l = poolConfig.getLength() == null ? 4 + "" : poolConfig.getLength();
        Integer length = Integer.parseInt(l);
        String number = "";
        for (int i = 0; i < length; i++) {
            number = number + "" + 9;
        }
//
        long parseLong = Long.parseLong(number);
        poolsCount = parseLong + 1;
        if (integer >= (parseLong + 1)) {
            Page p = new Page();
            p.setSize(3000);
            Pool pp = new Pool();
            pp.setIsUsed(0);
            this.pools = poolMapper.selectPage(p, new EntityWrapper<>(pp));
            Pool te = new Pool();
            te.setIsUsed(3);
            if (poolMapper.selectOne(te) != null) {
                dateForMark = poolMapper.selectOne(te).getNumber();
            }


            List<String> prefixes = poolConfig.getPrefixes();
            if (prefixes != null && prefixes.size() > 0 && (!prefixes.get(0).equals("${prefixes}"))) {
                for (String prefix : prefixes) {
                    long hasRead = poolMapper.preOrSufCount(prefix + "IsUsed");
                    PageForPool pageForPool = new PageForPool();
                    pageForPool.setIndex(0);
                    long noRead = poolsCount - hasRead;
                    if (noRead >= 3000) {
                        pageForPool.setPageSize(3000);
                    } else if (noRead == 0) {
                        List<String> strs = new ArrayList<>();
                        strs.add(prefix + "IsUsed");
                        poolMapper.clearAll(strs);
                        pageForPool.setPageSize(3000);
                    } else {
                        pageForPool.setPageSize(noRead);
                    }
                    pageForPool.setPreOrSuf(prefix + "IsUsed");
                    List<Pool> list = poolMapper.preOrSuf(pageForPool);
                    prefixesMap.put(prefix, list);
                    prefixesPageNumbers.put(prefix, pageNumber);
                }
            }
            List<String> suffixes = poolConfig.getSuffixes();
            if (suffixes != null && suffixes.size() > 0 && (!suffixes.get(0).equals("${suffixes}"))) {
                for (String suffix : suffixes) {
                    long hasRead = poolMapper.preOrSufCount("IsUsed" + suffix);
                    PageForPool pageForPool = new PageForPool();
                    pageForPool.setIndex(0);
                    long noRead = poolsCount - hasRead;
                    if (noRead >= 3000) {
                        pageForPool.setPageSize(3000);
                    } else if (noRead == 0) {
                        List<String> strs = new ArrayList<>();
                        strs.add("IsUsed" + suffix);
                        poolMapper.clearAll(strs);
                        pageForPool.setPageSize(3000);
                    } else {
                        pageForPool.setPageSize(noRead);
                    }
                    pageForPool.setPreOrSuf("IsUsed" + suffix);
                    List<Pool> list = poolMapper.preOrSuf(pageForPool);
                    suffixesMap.put(suffix, list);
                    suffixesPageNumbers.put(suffix, pageNumber);
                }
            }


            return;
        }
        for (long k = 0; k <= parseLong; k++) {
            int i = length - String.valueOf(k).length();
            Pool pool = new Pool();
            pool.setIsUsed(0);
            String n = k + "";
            for (int j = 0; j < i; j++) {
                n = 0 + n;
            }

            pool.setNumber(n);
            pools.add(pool);
            if (pools.size() > 20000) {
                poolMapper.batchInsert(pools);
                pools.clear();
            }
        }
        poolMapper.batchInsert(pools);


        addNumber(true, null, true);

    }
    @Override
    public void initPool() {
        this.pools = new CopyOnWriteArrayList<>();

        try {
            initTable();
            initData();
        }
        catch (SQLException ex) {
            throw new BusinessException(BizExceptionEnum.DATABASE_CONNECT_ERROR);
        }
    }


    private void addNumber(boolean isFlag, String prefixOrSuffix, Boolean isPre) {
        if (isFlag) {
            Page page = new Page();
            page.setSize(3000);
            page.setCurrent(1);
            pageNumber = 1;
            Pool pp = new Pool();
            pp.setIsUsed(0);
            pools = poolMapper.selectPage(page, new EntityWrapper<>(pp));


            Integer isUsed = poolMapper.maxUsed(num++);

            if (isUsed != 3) {
                Date date = new Date();
                String time = new SimpleDateFormat("yyyyMMdd").format(date);
                dateForMark = time;
                poolMapper.addConfig(time);
            }

        } else {

            if (prefixOrSuffix == null) {
                pageNumber++;
                Page page = new Page();
                page.setSize(2400);
                page.setCurrent(pageNumber);
                Pool pp = new Pool();
                pp.setIsUsed(0);
                List<Pool> poolList = poolMapper.selectPage(page, new EntityWrapper<>(pp));
                pools.addAll(poolList);
            } else {
                Integer integer = null;
                if (isPre) {
                    integer = prefixesPageNumbers.get(prefixOrSuffix);
                    Integer a = integer + 1;
                    prefixesPageNumbers.replace(prefixOrSuffix, a);//不知道行不行
                } else {
                    integer = suffixesPageNumbers.get(prefixOrSuffix);
                    Integer a = integer + 1;
                    suffixesPageNumbers.replace(prefixOrSuffix, a);//不知道行不行
                }

                long hasRead = integer * 2400;
                PageForPool pageForPool = new PageForPool();
                pageForPool.setIndex(0);

                long noRead = poolsCount - hasRead;
                if (noRead >= 2400) {
                    pageForPool.setPageSize(2400);
                } else {
                    pageForPool.setPageSize(noRead);
                }

                pageForPool.setPreOrSuf(prefixOrSuffix);
                List<Pool> list = poolMapper.preOrSuf(pageForPool);

                if (isPre) {
                    prefixesMap.get(prefixOrSuffix).addAll(list);
                } else {
                    suffixesMap.get(prefixOrSuffix).addAll(list);
                }

            }

        }
    }

    public String getSerialNumber() {
        return getSerialNumber(false, new PoolConfig(null, null));
    }
    public String getSerialNumber(String prefix) {
        return getSerialNumber(false, new PoolConfig(prefix, null));
    }



    @Override
    @Transactional
    public String getSerialNumber(boolean isRandom, PoolConfig poConfig) {
        String preOrSuf = null;
        Integer isPre = 2;
        Date date = new Date();
        String dateMark = new SimpleDateFormat("yyyyMMdd").format(date);

        if (dateMark.compareTo(dateForMark) > 0) {//第二天
            List<String> stringList = poolMapper.showField();
            Set<String> list = new HashSet<>();
            list.addAll(stringList);
            list.remove("number");
            List<String> temps = new ArrayList<>();
            temps.addAll(list);
            poolMapper.clearAll(temps);
            addNumber(true, null, true);
            poolMapper.setConfig(dateMark);
        }

        if (poConfig.getPrefix() != null) {
            preOrSuf = poConfig.getPrefix();
            isPre = 1;
        }
        if (poConfig.getSuffix() != null) {
            preOrSuf = poConfig.getSuffix();
            isPre = 0;
        }
        if (poConfig.getPrefix() != null && poConfig.getSuffix() != null) {
            isPre = 3;//未完成
        }


        dateForMark = dateMark;

        if (pools.size() < 600) {
            addNumber(false, preOrSuf, null);
        }
        if (prefixesMap.size() > 0 && isPre == 1) {
            if (!prefixesMap.containsKey(preOrSuf))
                return "There is no corresponding prefix";
            if (prefixesMap.get(preOrSuf).size() < 600)
                addNumber(false, preOrSuf, true);
        }
        if (suffixesMap.size() > 0 && isPre == 0) {
            if (!suffixesMap.containsKey(preOrSuf))
                return "There is no corresponding suffix";
            if (suffixesMap.get(preOrSuf).size() < 600)
                addNumber(false, preOrSuf, false);
        }

        int nextInt = 0;
        if (isRandom) {
            int i = 600;
            Random random = new Random();
            nextInt = random.nextInt(i);
        }
        if (isPre == 2) {
            Pool remove = pools.get(nextInt);
            PoolModel poolModel = new PoolModel();
            poolModel.setPreOrsuf("isUsed");
            poolModel.setNumber(remove.getNumber());
            poolMapper.setUsed(poolModel);
            return formatDateSpecial(date) + pools.remove(nextInt).getNumber();
        } else if (isPre == 1) {//前缀
            Pool remove = prefixesMap.get(preOrSuf).get(nextInt);
            PoolModel poolModel = new PoolModel();
            poolModel.setNumber(remove.getNumber());
            poolModel.setPreOrsuf(preOrSuf + "IsUsed");
            poolMapper.setUsed(poolModel);
            return preOrSuf + formatDateSpecial(date) + prefixesMap.get(preOrSuf).remove(nextInt).getNumber();
        } else if (isPre == 3) {//都有
            Pool remove = pools.get(nextInt);
            PoolModel poolModel = new PoolModel();
            poolModel.setNumber(remove.getNumber());
            poolModel.setPreOrsuf("isUsed");
            poolMapper.setUsed(poolModel);
        } else {//后缀
            Pool remove = suffixesMap.get(preOrSuf).get(nextInt);
            PoolModel poolModel = new PoolModel();
            poolModel.setPreOrsuf("IsUsed" + preOrSuf);
            poolModel.setNumber(remove.getNumber());
            poolMapper.setUsed(poolModel);
            return formatDateSpecial(date) + suffixesMap.get(preOrSuf).remove(nextInt).getNumber() + preOrSuf;
        }

        throw new BusinessException(BizExceptionEnum.SERVER_ERROR);
    }

    @Override
    public void backupData() {

    }

    @Override
    public String formatDateSpecial(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(date);
        String left = format.substring(0, 2);
        String left2 = format.substring(2, 4);
        String right = format.substring(4, 6);
        String right2 = format.substring(6, 8);
        return left + left2 + right2 + right;
    }

    private void setTheKeySetList() {
        Set<String> prefixeskeySet = prefixesMap.keySet();
        Set<String> suffixeskeySet = suffixesMap.keySet();
        keySetlist = new ArrayList<>();
        keySetlist.addAll(prefixeskeySet);
        keySetlist.addAll(suffixeskeySet);
    }

    @Override
    public void reback(String num) {
        String keyOf = "";
        String preOrSuf = "";
        if (keySetlist == null)
            setTheKeySetList();
        for (String key : keySetlist) {

            if (num.contains(key)) {
                preOrSuf = key;
                keyOf = key;
                num = num.replace(preOrSuf, "");
            }
        }
        String n = String.valueOf(num);
        n = n.substring(8);

        PoolModel pool = new PoolModel();

        pool.setNumber(n);
        if (keyOf.equals("")) {
            pool.setPreOrsuf("isUsed");
            pools.add(0, pool);
        } else {

            if (prefixesMap.containsKey(preOrSuf)) {
                if (!rebackNumber.contains(pool.getNumber())) {
                    rebackNumber.add(pool.getNumber());
                    prefixesMap.get(preOrSuf).add(0, pool);
                    pool.setPreOrsuf(keyOf + "IsUsed");
                } else {
                    return;
                }
            }
            if (suffixesMap.containsKey(preOrSuf)) {
                if (!rebackNumber.contains(pool.getNumber())) {
                    rebackNumber.add(pool.getNumber());
                    suffixesMap.get(preOrSuf).add(0, pool);
                    pool.setPreOrsuf("IsUsed" + keyOf);
                } else {
                    return;
                }
            }
        }
        poolMapper.reback(pool);
    }


}


