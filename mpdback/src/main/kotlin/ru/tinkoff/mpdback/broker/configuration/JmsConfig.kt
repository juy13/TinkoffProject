package ru.tinkoff.mpdback.broker.configuration

import org.apache.activemq.ActiveMQConnectionFactory
import org.apache.activemq.broker.BrokerService
import org.apache.activemq.command.ActiveMQQueue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.jms.connection.CachingConnectionFactory
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.listener.DefaultMessageListenerContainer
import ru.tinkoff.mpdback.broker.Consumer
import javax.jms.ConnectionFactory
import javax.jms.Destination

@Configuration
@PropertySource("classpath:application.properties")
class JmsConfig {
    @Value("\${jms.broker.url}")
    private val brokerUrl: String? = null

    @Value("\${jms.queue.name}")
    private val queueName: String? = null

    @Autowired
    private val messageConsumer: Consumer? = null

    @Bean
    fun broker(): BrokerService {
        val broker = BrokerService()
        broker.addConnector(brokerUrl)
        broker.isPersistent = false
        return broker
    }

    @Bean
    fun connectionFactory(): ConnectionFactory {
        return CachingConnectionFactory(ActiveMQConnectionFactory(brokerUrl))
    }

    @Bean
    fun destination(): ActiveMQQueue {
        return ActiveMQQueue(queueName)
    }

    @Bean
    fun jmsTemplate(connectionFactory: ConnectionFactory?, destination: Destination?): JmsTemplate {
        val jmsTemplate = JmsTemplate(connectionFactory!!)
        jmsTemplate.defaultDestination = destination
        return jmsTemplate
    }

    @Bean
    fun defaultMessageListenerContainer(
        connectionFactory: ConnectionFactory?,
        destination: Destination?
    ): DefaultMessageListenerContainer {
        val defaultMessageListenerContainer = DefaultMessageListenerContainer()
        defaultMessageListenerContainer.connectionFactory = connectionFactory
        defaultMessageListenerContainer.destination = destination
        defaultMessageListenerContainer.messageListener = messageConsumer
        return defaultMessageListenerContainer
    }
}