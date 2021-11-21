package com.example.retailer.config


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ConfigurableApplicationContext
import java.util.logging.Logger

class RabbitMQRunner : CommandLineRunner {

    @Autowired
    lateinit var logger: Logger

    @Value("\${app.client.name:ivan}")
    val clientName = "petya"

    @Autowired
    lateinit var ctx: ConfigurableApplicationContext

    override fun run(vararg args: String?) {
        logger.info(" [x] App get started with profile ${ctx.environment.activeProfiles.first()}")
        logger.info(" [x] Client name is: $clientName")
        ctx.close()
    }
}