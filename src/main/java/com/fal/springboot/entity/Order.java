package com.fal.springboot.entity;

import java.io.Serializable;

public class Order implements Serializable {
	private static final long serialVersionUID = -7079088490628082110L;
	private String id;
	private String name;
	private String messageId;// 存储消息发送的唯一标识

	public Order() {
	}

	public Order(String id, String name, String messageId) {
		this.id = id;
		this.name = name;
		this.messageId = messageId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId ==null ? null : messageId.trim();
	}

}
