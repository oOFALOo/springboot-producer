package com.fal.springboot.producer;

import java.util.Date;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fal.springboot.constant.Constants;
import com.fal.springboot.entity.Order;
import com.fal.springboot.mapper.BrokerMessageLogMapper;
import com.sun.tools.javac.code.Attribute.Constant;

@Component
public class RabbitOrderSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

	/**
     * 回调方法：confirm确认
     */
    final ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("correlationData：" + correlationData);
            String messageId = correlationData.getId();
            if (ack) {
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageId,Constants.ORDER_SENDING_SUCCESS, new Date());
            } else {
                // 失败则进行具体的后续操作：重试或者补偿等
                System.out.println("异常处理...");
            }
        }
    };
	
    /**
     * 发送订单
     * @param order 订单
     */
	public void sendOrder(Order order){
		// 设置回调方法
        rabbitTemplate.setConfirmCallback(confirmCallback);
        // 消息ID
        CorrelationData correlationData = new CorrelationData(order.getMessageId());
        // 发送消息
        rabbitTemplate.convertAndSend("order-exchange", "order.abc", order, correlationData);
	}
}
