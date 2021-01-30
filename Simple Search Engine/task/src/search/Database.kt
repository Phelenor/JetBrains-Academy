package search

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class Person(val firstName: String = "", val lastName: String = "", val email: String = "") {

    fun data(): List<String> {
        return Arrays.asList(firstName, lastName, email)
    }

    override fun toString(): String {
        if(email.isEmpty())
            return "${firstName} ${lastName}"
        else
            return "${firstName} ${lastName} $email"
    }
}

class Database {
    val map = HashMap<String, ArrayList<Person>>()
    val database = ArrayList<Person>()

    fun addPerson(person: Person) {
        database.add(person)
    }

    fun search(string: String, strat: String) {
        val list = ArrayList<Person>()
        val input = string.split(" ")

        for(str in input) {
            when (strat) {
                "ANY" -> {
                    if (map.containsKey(str.toUpperCase()))
                        for (person in map[str.toUpperCase()]!!)
                            list.add(person)
                }
                "ALL" -> {
                    if (map.containsKey(str.toUpperCase()))
                        for (person in map[str.toUpperCase()]!!) {
                            if(list.isEmpty())
                                 list.add(person)
                            else
                                list.intersect(arrayListOf(person))
                        }
                }
                "NONE" -> {
                    list.addAll(database)
                    if (map.containsKey(str.toUpperCase()))
                        for (person in map[str.toUpperCase()]!!) {
                            list.remove(person)
                        }
                }
            }
        }

        if(list.isEmpty()) {
            println("No matching people found.")
            println()
        } else {
            println("${list.size} persons found: ")
            list.forEach {println(it)}
        }
    }

    fun makeMap() {
        for(person in database) {
            for(prop in person.data()) {
                if(map.containsKey(prop.toUpperCase()))
                    map[prop.toUpperCase()]!!.add(person)
                else {
                    map[prop.toUpperCase()] = ArrayList<Person>()
                    map[prop.toUpperCase()]!!.add(person)
                }
            }
        }
    }

    fun printPeople() {
        println("\n=== List of people ===")
        for(person in database)
            println(person)
    }
}