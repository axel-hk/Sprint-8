package com.example.retailer.storage


import com.example.retailer.api.distributor.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository: JpaRepository<Order, String>