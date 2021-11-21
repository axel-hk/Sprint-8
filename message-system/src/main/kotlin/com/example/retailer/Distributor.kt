package com.example.retailer

import com.example.retailer.api.distributor.Order
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.Logger
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import kotlin.concurrent.thread

class Distributor {

    @Autowired
    lateinit var template: RabbitTemplate

    @Autowired
    lateinit var topic: TopicExchange

    @Autowired
    lateinit var logger: Logger

    val mapper = ObjectMapper().registerModule(KotlinModule())

    @RabbitListener(queues = ["#{retailerQueue.name}"])
    fun receive(message: Message): String {
        val order: Order = mapper.readValue(message.body)

        val customer: String = message.messageProperties.getHeader("Notify-Exchange")
        val retailerId = order.orderId + customer

        logger.info(" [x] Received from Retailer. Exchange: $customer")

        thread(start = true) {
            send(order.orderId, customer)
        }

        return retailerId
    }

    fun send(id: String, customer: String) {
        Thread.sleep(600)
        val message = MessageBuilder
            .withBody(mapper.writeValueAsBytes(id))
            .setHeader("Notify-Exchange", customer)
            .build()

        val key = "retailer.alexsysoy.$id"

        template.convertAndSend(topic.name, key, message)
        logger.info(" [x] Sent from Distributor to Retailer. OrderId: $id")
    }

}