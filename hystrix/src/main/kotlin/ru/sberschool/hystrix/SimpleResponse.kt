package ru.sberschool.hystrix

data class Fighter(val results: Array<Result>?)

data class Power(val results: Array<Result>?)

data class Skills(val results: Array<Result>?)

data class Game(val results: Array<Result>?)

data class Result(val name: String, val url: String)
