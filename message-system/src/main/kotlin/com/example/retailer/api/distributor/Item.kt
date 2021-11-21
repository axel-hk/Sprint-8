package com.example.retailer.api.distributor

import javax.persistence.*

/**
 * Описание товара
 */
@Entity
@Table(name = "ITEM")
class Item(
    /**
     * Произвольный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    /**
     * Произвольное название
     */
    val name: String
)