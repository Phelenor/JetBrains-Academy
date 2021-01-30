package parking

import java.util.*

fun main() {
    val reader = Scanner(System.`in`)
    val parking = CarPark()

    while (true) {
        var input = readLine()
        if (input == null || input.isEmpty())
            continue
        if (input == "exit")
            return
        var ins = input!!.split(" ")
        if (ins[0] == "create") {
            for (i in 1..ins[1].toInt()) {
                parking.addSpot(i)
            }
            println("Created a parking lot with ${ins[1]} spots.")
            break
        } else {
            println("Sorry, parking lot is not created.")
            continue;
        }
    }


    while (true) {
        var input = readLine()
        if (input == null || input.isEmpty())
            continue
        if (input == "exit")
            break
        if (input == "status") {
            when (parking.counter) {
                0  -> println("Parking lot is empty.")
                else -> parking.status()
            }
        }

        var ins = input!!.split(" ")

        if (ins[0] == "create") {
            for (i in 1..ins[1].toInt()) {
                parking.clear()
                parking.counter = 0
                parking.addSpot(i)
                println("Created a parking lot with ${ins[1]} spots.")
            }
        }
        if (ins[0] == "park") {
            parking.park(ins[2], ins[1])
        }
        if (ins[0] == "leave")
            parking.leave(ins[1].toInt())
        if(ins[0] == "reg_by_color") {
            parking.findByColor(ins[1])
        }
        if(ins[0] == "spot_by_color") {
            parking.spotByColor(ins[1])
        }
        if(ins[0] == "spot_by_reg") {
            parking.spotByReg(ins[1])
        }

    }
}
