package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class OrderStorageImpl: OrderStorage {
    @Autowired
    lateinit var orderRepository: OrderRepository
    @Autowired
    lateinit var orderInformRepository: OrderInformRepository


    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        val orderInfo = orderInformRepository
            .save(OrderInfo(order.id!!, OrderStatus.SENT, "signature"))
        return PlaceOrderData(order, orderInfo)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        return if (orderInformRepository.findById(order.orderId).isPresent) {
            orderInformRepository.save(order)
            true
        }
        else false
    }

    override fun getOrderInfo(id: String): OrderInfo? = orderInformRepository.findByIdOrNull(id)
}