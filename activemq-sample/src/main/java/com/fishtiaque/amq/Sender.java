package com.fishtiaque.amq;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {

	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";
	private static final String BROKERURL = "tcp://localhost:61616";
		
	public static void main(String[] args) {
		
     	ConnectionFactory connectionFactory;
		
     	Connection connection = null;
     	
        Session session;
        
        Destination destination;
        
        MessageProducer producer;
		
        connectionFactory= new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);
        
		try {

			connection = connectionFactory.createConnection();
			connection.start();
			
            session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            
            destination = session.createQueue("MyQueue");
			
            producer = session.createProducer(destination);
            
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            
            try {
				sendMessage(session, producer);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception during sending of message :"+e.getLocalizedMessage());
			}
            
            session.commit();
            
		} catch (JMSException e) {
			e.printStackTrace();
		}finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}

	private static void sendMessage(Session session, MessageProducer producer) throws Exception {
		for (int i = 1; i <= 5; i++) {
			TextMessage  message = session.createTextMessage("Active MQ messages "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
			producer.send(message);//发送消息
			System.out.println("****** Done Sending message ************");
		}
	}

}
