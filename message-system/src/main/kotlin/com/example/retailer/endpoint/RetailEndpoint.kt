package com.example.retailer.endpoint

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*

@RestController
class RetailEndpoint {

    @Value("\${app.client.name:alex}")
    val clientName = "alex"

    @Autowired
    lateinit var orderService: OrderService

    @PostMapping("/placeOrder")
    fun placeOrder(@RequestBody orderDraft: Order): OrderInfo {
        val orderToSave = Order(
            clientName,
            orderDraft.address,
            orderDraft.recipient,
            orderDraft.items
        )
        val result = orderService.placeOrder(orderToSave)
        return result
    }

    @GetMapping("/view/{id}")
    fun viewOrder(@PathVariable("id") id: String): OrderInfo? = orderService.getOrderInfo(id)

}

