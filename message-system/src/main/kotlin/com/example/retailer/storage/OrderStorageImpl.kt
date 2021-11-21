package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class OrderStorageImpl : OrderStorage {

    @Autowired
    lateinit var repository: OrderRepository

    override fun createOrder(draftOrder: Order): PlaceOrderData {

        val result = repository.save(draftOrder)

        val orderInfo = OrderInfo(
            result.orderId,
            OrderStatus.SENT,
            result.orderId
        )

        return PlaceOrderData(result, orderInfo)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        val orderToUpdateOptional = repository.findById(order.signature)

        return if (orderToUpdateOptional.isPresent) {
            val orderToUpdate = orderToUpdateOptional.get()

            val updatedOrder = Order(
                order.orderId,
                orderToUpdate.address,
                orderToUpdate.recipient,
                orderToUpdate.items
            )

            updatedOrder.orderId = orderToUpdate.orderId
            repository.save(updatedOrder)
            true
        } else {
            false
        }

    }

    override fun getOrderInfo(id: String): OrderInfo? {
        val order = repository.findByIdOrNull(id)

        lateinit var status: OrderStatus
        if (order != null) {
            val key = order.id!!
            status = when {
                key == "" -> OrderStatus.SENT
                key.contains("CREATED") -> OrderStatus.CREATED
                key.contains("DELIVERED") -> OrderStatus.DELIVERED
                else -> OrderStatus.ERROR
            }
        } else {
            return null
        }
        return OrderInfo(id, status, order.orderId)
    }
}