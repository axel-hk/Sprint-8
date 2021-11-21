package com.example.retailer.adapter

import com.example.retailer.Retailer
import com.example.retailer.api.distributor.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DistributorPublisherImpl : DistributorPublisher {

    @Autowired
    lateinit var retailer: Retailer

    override fun placeOrder(order: Order): Boolean {
        return retailer.send(order)
    }
}