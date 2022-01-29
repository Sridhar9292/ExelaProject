/*
 * package com.exelatech.ecx.backend.jms;
 * 
 * import javax.jms.Message;
 * 
 * import org.apache.activemq.ActiveMQConnectionFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.jms.annotation.JmsListener; import
 * org.springframework.jms.config.DefaultJmsListenerContainerFactory; import
 * org.springframework.jms.core.JmsTemplate;
 * 
 * import lombok.extern.slf4j.Slf4j;
 * 
 * @Configuration public class JMSConfig {
 * 
 * @Value("${spring.activemq.broker-url}") String ACTIVEMQ_URL;
 * 
 * // @Value("${admin}") String ACTIVEMQ_USERNAME = "admin";
 * 
 * // @Value("${acg.activemq.password}") String ACTIVEMQ_PASSWORD = "admin";
 * 
 * @Autowired JmsTemplate jmsTemplate;
 * 
 * @Autowired private ECXVirtualTopicEventListener listener;
 * 
 * @Bean public DefaultJmsListenerContainerFactory jmsListenerContainerFactory()
 * { DefaultJmsListenerContainerFactory factory = new
 * DefaultJmsListenerContainerFactory();
 * factory.setConnectionFactory(connectionFactory()); return factory; }
 * 
 * @Bean public ActiveMQConnectionFactory connectionFactory() {
 * ActiveMQConnectionFactory connectionFactory = new
 * ActiveMQConnectionFactory(); connectionFactory.setBrokerURL(ACTIVEMQ_URL);
 * connectionFactory.setPassword(ACTIVEMQ_USERNAME);
 * connectionFactory.setUserName(ACTIVEMQ_PASSWORD); return connectionFactory; }
 * 
 * }
 */