package com.fal.springboot;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fal.springboot.entity.Order;
import com.fal.springboot.service.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private OrderService orderService;
	
	public void testCreateOrder() throws Exception{
		Order order = new Order();
		order.setId("2018122400000001");
		order.setName("创建测试订单");
		order.setMessageId(System.currentTimeMillis()+"$"+UUID.randomUUID().toString());
		orderService.createOrder(order);
	}
	
}

