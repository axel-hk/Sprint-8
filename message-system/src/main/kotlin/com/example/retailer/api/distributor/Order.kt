package com.example.retailer.api.distributor

import javax.persistence.*
/**
 * Описание заказа
 */
@Entity
@Table(name = "ORDERS")
data class Order(
    /**
     * Уникальный идентификатор заказа на стороне ретейлера
     */
    @Id
    val id: String?,

    /**
     * Произвольный адрес доставки
     */
    val address: String,

    /**
     * Произвольный получатель доставки
     */
    val recipient: String,

    /**
     * Список заказанных товаров
     */
    @OneToMany(cascade = [CascadeType.ALL])
    val items: List<Item>
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var orderId: String = ""
}
