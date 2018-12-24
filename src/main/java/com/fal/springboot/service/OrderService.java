package com.fal.springboot.service;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fal.springboot.constant.Constants;
import com.fal.springboot.entity.BrokerMessageLog;
import com.fal.springboot.entity.Order;
import com.fal.springboot.mapper.BrokerMessageLogMapper;
import com.fal.springboot.mapper.OrderMapper;
import com.fal.springboot.producer.RabbitOrderSender;
import com.fal.springboot.utils.FastJsonConvertUtil;

@Service
public class OrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private BrokerMessageLogMapper brokerMessageLogMapper;
	
	@Autowired
	private RabbitOrderSender rabbitOrderSender;
	
	public void createOrder(Order order) throws Exception{
		Date orderTime = new Date();
		//order insert 业务数据入库
		orderMapper.insert(order);
		BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
		brokerMessageLog.setMessageId(order.getMessageId());
		brokerMessageLog.setMessage(FastJsonConvertUtil.convertObjectToJSON(order));
		brokerMessageLog.setStatus("0");
		brokerMessageLog.setNextRetry(DateUtils.addMinutes(orderTime, Constants.ORDER_TIMEOUT));
		brokerMessageLog.setCreateTime(new Date());
		brokerMessageLog.setUpdateTime(new Date());
		brokerMessageLogMapper.insert(brokerMessageLog);
		rabbitOrderSender.sendOrder(order);
		
	}
}
