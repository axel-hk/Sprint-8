package com.example.retailer.config


import com.example.retailer.Distributor
import com.example.retailer.Retailer
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Value("\${app.client.name:alex}")
    val clientName = "ivan"

    @Bean
    fun logger() = LoggerFactory.getLogger(javaClass)

    @Bean
    fun topic(): TopicExchange {
        logger().info(" [x] Create TopicExchange for client $clientName")
        return TopicExchange("${clientName}_distributor_exchange")
    }

    class RetailerConfig {

        @Bean
        fun retailer(): Retailer = Retailer()

        @Bean
        fun retailerQueue(): Queue = AnonymousQueue()

        @Bean
        fun retailerQueueBinding(
            topic: TopicExchange,
            retailerQueue: Queue
        ): Binding = BindingBuilder.bind(retailerQueue)
            .to(topic)
            .with("distributor.placeOrder.alexsysoy.#")
    }

    class DistributorConfig {

        @Bean
        fun distributorQueue(): Queue = AnonymousQueue()

        @Bean
        fun distributorQueueBinding(
            topic: TopicExchange,
            distributorQueue: Queue
        ): Binding = BindingBuilder.bind(distributorQueue)
            .to(topic)
            .with("retailer.alexsysoy.#")

        @Bean
        fun distributor(): Distributor = Distributor()
    }

}
