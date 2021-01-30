package machine

open class Coffee {
    open val water: Int = 0
    open val milk: Int = 0
    open val coffeeBeans: Int = 0
    open val cost: Int = 0
}

class Espresso : Coffee() {
    override val water: Int = 250
    override val milk: Int = 0
    override val coffeeBeans: Int = 16
    override val cost: Int = 4
    // mililiters and grams
}

class Latte : Coffee() {
    override val water: Int = 350
    override val milk: Int = 75
    override val coffeeBeans: Int = 20
    override val cost: Int = 7
}

class Cappuccino : Coffee() {
    override val water: Int = 200
    override val milk: Int = 100
    override val coffeeBeans: Int = 12
    override val cost: Int = 6
}

