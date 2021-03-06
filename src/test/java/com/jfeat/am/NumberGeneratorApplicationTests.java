package com.jfeat.am;

import com.jfeat.am.module.NumberGenerator.services.persistence.dao.PoolMapper;
import com.jfeat.am.module.NumberGenerator.config.PageForPool;
import com.jfeat.am.module.NumberGenerator.services.persistence.model.Pool;
import com.jfeat.am.NumberGeneratorApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@ActiveProfiles(profiles = "test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NumberGeneratorApplication.class)
@WebAppConfiguration
@Transactional //测试之后数据可回滚
public class NumberGeneratorApplicationTests {

	@Test
	public void contextLoads() {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);

		System.out.println(list.remove(1));

		//System.out.println(list.get(0)+"\t"+list.size());
	}
	@Autowired
	private PoolMapper poolMapper;

	@Test
	public void test(){
		//System.out.println(new PoolServiceImpl().formatDateSpecial(new Date()));
//		List<String> stringList = poolMapper.showField();
//		Set<String> list = new HashSet<>();
//		list.addAll(stringList);
//		list.remove("number");
//		List<String> temps = new ArrayList<>();
//		temps.addAll(list);
//		System.out.println(list);
//		poolMapper.clearAll(temps);
		PageForPool pageForPool = new PageForPool();
		pageForPool.setPreOrSuf("isUsed");
		pageForPool.setIndex(0);
		pageForPool.setPageSize(2400);
		List<Pool> pools = poolMapper.preOrSuf(pageForPool);
		for(Pool pool:pools){
			System.out.println(pool.getNumber());
		}
	}

	@Test
	public void test02(){
		Pool te = new Pool();
		te.setIsUsed(3);
		if(poolMapper.selectOne(te)==null){
			Date date = new Date();
			String time = new SimpleDateFormat("yyyyMMdd").format(date);
			poolMapper.addConfig(time);
		}
	}

	@Test
	public void test03(){
		System.out.println(poolMapper.maxUsed(1));
	}

}
