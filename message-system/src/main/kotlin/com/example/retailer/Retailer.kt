package com.example.retailer


import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.service.OrderService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.Logger

class Retailer {

    @Autowired
    lateinit var template: RabbitTemplate

    @Autowired
    lateinit var topic: TopicExchange

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var logger: Logger

    val mapper = ObjectMapper().registerModule(KotlinModule())

    fun send(order: Order): Boolean {
        val key = "distributor.placeOrder.alexsysoy.${order.orderId}"

        val message = MessageBuilder
            .withBody(mapper.writeValueAsBytes(order))
            .setHeader("Notify-Exchange", "${order.id}_distributor_exchange")
            .setHeader("Notify-RoutingKey", "retailer.alexsysoy")
            .build()

        val response = template
            .convertSendAndReceive(topic.name, key, message) as String

        logger.info(" [x] Sent from Retailer to Distributor. OrderId: ${order.orderId}")

        return if (response.isNotBlank()) {
            logger.info(" [.] Got response from Distributor CREATED");
            orderService.updateOrderInfo(
                OrderInfo(
                    response + OrderStatus.CREATED,
                    OrderStatus.CREATED,
                    order.orderId
                )
            )
        } else {
            false
        }
    }

    @RabbitListener(queues = ["#{distributorQueue.name}"])
    fun receive(message: Message) {
        val id: String = mapper.readValue(message.body)
        val customer: String = message.messageProperties.getHeader("Notify-Exchange")
        logger.info(" [.] Got from Distributor DELIVERED")
        orderService.updateOrderInfo(
            OrderInfo(
                id + customer + OrderStatus.DELIVERED,
                OrderStatus.DELIVERED,
                id
            )
        )
    }
}