package ru.sberschool.hystrix

class FallbackSlowlyApi : SlowlyApi {
    override fun getFirstFighter(): Pokemon {
        return Fighter(arrayOf(Result("rock", "no url")))
    }

    override fun getFirstSkills(): Ability {
        return Skills(arrayOf(Result("super punch", "no url")))
    }

    override fun getFirstPower(): Location {
        return Location(arrayOf(Result("10000", "no url")))
    }

    override fun getFirstGame(): Game {
        return Game(arrayOf(Result("first game", "no url")))
    }
}