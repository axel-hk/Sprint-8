package com.example.retailer.api.distributor

import javax.persistence.Id

/**
 * Уведомление об изменении заказа
 */
data class OrderInfo(

    /**
     * Уникальный идентификатор заказа
     *
     * @see com.example.retailer.api.distributor.Item#id
     */
    @Id
    val orderId: String,

    /**
     * Статус заказа:
     *  Created
     *
     */
    var status: OrderStatus,

    /**
     * Контрольная сумма
     */
    val signature: String,

)