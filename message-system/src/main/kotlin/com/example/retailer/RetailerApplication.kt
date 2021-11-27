package com.example.retailer

import com.example.retailer.adapter.DistributorPublisherImpl
import com.example.retailer.adapter.RetailerConsumerImpl
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean


@SpringBootApplication
class RetailerApplication {
	@Bean
	fun topicExchange()
			= TopicExchange("distributor_exchange", true, false)

	@Bean
	fun queue()
			= Queue("retailer", false, false, true)

	@Bean
	fun bindingRetailer(topicExchange: TopicExchange, queue: Queue
	) = BindingBuilder
		.bind(queue)
		.to(topicExchange)
		.with("retailer.axel-hk.#")

	@Bean
	fun consumer() = RetailerConsumerImpl()

	@Bean
	fun publisher() = DistributorPublisherImpl()

	@Bean
	fun restTemplate() = TestRestTemplate()

}

fun main(args: Array<String>) {
	runApplication<RetailerApplication>(*args)
}