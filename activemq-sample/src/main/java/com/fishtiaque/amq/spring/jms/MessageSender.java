package com.fishtiaque.amq.spring.jms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class MessageSender {

	private JmsTemplate jmsTemplate;

	public MessageSender() {
	}

	public MessageSender(JmsTemplate jmsTemplate) {
		super();
		this.jmsTemplate = jmsTemplate;
	}

	public void send(Book book) {
		jmsTemplate.convertAndSend(book);
	}

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("jms-producer.xml");
		MessageSender messageSender = (MessageSender) applicationContext.getBean("messageSender");
		Book book = new Book(1, "Active MQ in Action", "Robert Bruce", 98.0f);
		messageSender.send(book);
		System.out.println("************** Done sending a book message ***************");
		System.exit(0);
	}

}
