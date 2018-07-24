package com.fishtiaque.amq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver {

	
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";
	private static final String BROKERURL = "tcp://localhost:61616";
	
	
	public static void main(String[] args) {

		ConnectionFactory connectionFactory;
		
     	Connection connection = null;
     	
        Session session;
        
        Destination destination;
        
        MessageConsumer consumer;
		
        connectionFactory= new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);

        try {
			connection = connectionFactory.createConnection();
			
			connection.start();
			
			session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);

			destination = session.createQueue("TestQ");

			consumer = session.createConsumer(destination);
			while (true) {

				TextMessage message = (TextMessage) consumer.receive(100000);
			    if (null != message) {
			        System.out.println("Received message using Receiver : " + message.getText());
			    } else {
			        break;
			    }
			}
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
            try {
                if (null != connection){
                	connection.close();
                }
            } catch (Throwable ignore) {
            	
            }
        }
        
	}

}
