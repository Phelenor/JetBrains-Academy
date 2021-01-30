package machine

import java.util.*

class CoffeeMachine(var water: Int = 400, var milk: Int = 540, var beans: Int = 120, var cups:Int = 9) {
    var coffee = Coffee()
    var money: Int = 550
    val reader = Scanner(System.`in`)

    fun read(): String {
        return reader.next()
    }

    fun readInt(): Int {
        return reader.nextInt()
    }

    fun run() {
        loop@ while(true) {
            println("Write action (buy, fill, take, remaining, exit): ")
            var command = read()
            when (command) {
                "buy" -> {
                    println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ")
                    when (read()) {
                        "1" -> coffee = Espresso()
                        "2" -> coffee = Latte()
                        "3" -> coffee = Cappuccino()
                        "back" -> continue@loop
                        else -> continue@loop
                    }
                    buyCoffee(coffee)
                }
                "fill" -> {
                    println("Write how many ml of water do you want to add: ")
                    addWater(readInt())
                    println("Write how many ml of milk do you want to add: ")
                    addMilk(readInt())
                    println("Write how many grams of coffee beans do you want to add: ")
                    addBeans(readInt())
                    println("Write how many disposable cups of coffee do you want to add: ")
                    addCups(readInt())
                }
                "take" -> takeMoney()
                "exit" -> break@loop
                "remaining" -> status()
            }
        }
    }

    fun status() {
        println("The coffee machine has:\n" +
                "$water of water\n" +
                "$milk of milk\n" +
                "$beans of coffee beans\n" +
                "$cups of disposable cups")
        if(money == 0)
            println("$money of money\n")
        else
            println("$$money of money\n")
    }

    fun buyCoffee(coffee: Coffee) {
        if(water - coffee.water >= 0 && milk - coffee.milk >= 0 && beans - coffee.coffeeBeans >= 0 && cups - 1 >= 0) {
            water -= coffee.water; milk -= coffee.milk; beans-= coffee.coffeeBeans; cups -= 1; money += coffee.cost
            println("I have enough resources, making you a coffee!\n")
            return
        } else {
            if (water - coffee.water < 0)
                println("Sorry, not enough water!\n")
            if (milk - coffee.milk < 0)
                println("Sorry, not enough milk!\n")
            if (beans - coffee.coffeeBeans < 0)
                println("Sorry, not enough beans!\n")
            if (cups - 1  < 0)
                println("Sorry, not enough cups!\n")
        }
    }

    fun addWater(water: Int) {
        if(water > 0)
         this.water += water
    }

    fun addMilk(milk:Int) {
        if(milk > 0)
            this.milk += milk
    }

    fun addBeans(beans: Int) {
        if(beans > 0)
            this.beans += beans
    }

    fun addCups(cups: Int) {
        if(cups > 0)
            this.cups += cups
    }

    fun takeMoney() {
        println("I gave you $$money\n")
        money = 0
    }
}