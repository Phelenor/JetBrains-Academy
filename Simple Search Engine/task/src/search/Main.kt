package search

import java.io.File
import java.util.*

var file = File("")
val database = Database()
var mark = false

fun main(args: Array<String>) {
     // run()
    if(args[0] == "--data") {
        file = File(args[1])
        addFiles()
    }
    database.makeMap()
    run()
}

fun addFiles() {
    mark = true
    var lines = file.readLines()
    for(line in lines) {
        var input = line.split(" ")
        if (input.size == 2)
            database.addPerson(Person(input[0], input[1]))
        else if(input.size == 1)
            database.addPerson(Person(input[0]))
        else
            database.addPerson(Person(input[0], input[1], input[2]))
    }
}


fun run() {
    val reader = Scanner(System.`in`)
    var input: List<String>
    var num = 0

    if(!mark) {
        println("Enter the number of people: ")
        num = reader.nextLine().toInt()
        println("Enter all people: ")
        repeat(num) {
            input = reader.nextLine().split(" ")
            if (input.size == 2)
                database.addPerson(Person(input[0], input[1]))
            else if (input.size == 1)
                database.addPerson(Person(input[0]))
            else
                database.addPerson(Person(input[0], input[1], input[2]))
        }
    }

loop@ while(true) {
        println()
        printMenu()
        when(reader.nextLine().toInt()) {
            1 -> {
                println("\nSelect a matching strategy: ALL, ANY, NONE")
                var strat: String = reader.nextLine()
                println("\nEnter a name or email to search all suitable people.")
                  database.search(reader.nextLine(), strat) }
            2 -> database.printPeople()
            0 -> { println("\nBye!")
                   return  }
            else -> { println("\nIncorrect option! Try again."); continue@loop}
        }
    }
}


fun printMenu() {
    println("=== Menu ===\n" +
            "1. Find a person\n" +
            "2. Print all people\n" +
            "0. Exit")
}
