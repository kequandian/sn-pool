package com.jfeat.am;

import com.jfeat.am.module.NumberGenerator.services.crud.service.impl.PoolServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class NumberGeneratorApplicationTests {

	@Test
	public void contextLoads() {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);

		System.out.println(list.remove(1));

		//System.out.println(list.get(0)+"\t"+list.size());
	}

	@Test
	public void test(){
		System.out.println(new PoolServiceImpl().formatDateSpecial(new Date()));
	}

}
