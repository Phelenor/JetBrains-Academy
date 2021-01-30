package flashcards

import java.io.File
import java.util.*
import kotlin.collections.HashMap

val cards = HashMap<Int, Flashcard>()
var LOG = ArrayList<String>()
var saveAtExit = false
var saveFile = ""

fun main(args: Array<String>) {
    checkArgs(args)
    run()
}

fun run() {
    while(true) {
        println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats): ")
        LOG.add("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats): ")
        var command = readLine()!!.trim()
        LOG.add(command)
        when(command) {
            "add" -> addCard()
            "remove" -> removeCard()
            "exit" -> {
                println("Bye bye!")
                LOG.add("Bye bye!")
                if(saveAtExit) export(saveFile)
                return
            }
            "ask" -> ask()
            "import" -> import()
            "export" -> export()
            "log" -> log()
            "hardest card" -> hardest()
            "reset stats" -> resetStats()
        }
    }
}

fun checkArgs(args: Array<String>) {
    for(i in args.indices) {
        if (args[i] == "-import")
            import(args[i + 1])
        if (args[i] == "-export") {
            saveAtExit = true
            saveFile = args[i + 1]
        }
    }
}

fun resetStats() {
    for(card in cards.values)
        card.errors = 0
    println("Card statistics has been reset.\n")
    LOG.add("Card statistics has been reset.\n")
}

fun hardest() {
    var hardestCards = ArrayList<Flashcard>()
    hardestCards.add(Flashcard())
    for(card in cards.values) {
        if(card.errors > hardestCards[0].errors) {
            hardestCards.clear()
            hardestCards.add(card)
        }
        else if(card.errors == hardestCards[0].errors && card.errors > 0)
            hardestCards.add(card)
    }
    if(hardestCards[0].errors == 0) {
        println("There are no cards with errors.\n")
        LOG.add("There are no cards with errors.\n")
        return
    }
    if(hardestCards.size == 1) {
        println("The hardest card is \"${hardestCards[0].term}\". You have ${hardestCards[0].errors} errors answering it.\n")
        LOG.add("The hardest card is \"${hardestCards[0].term}\". You have ${hardestCards[0].errors} errors answering it.\n")
        return
    }
    if(hardestCards.size > 1) {
        var errors = 0
        var terms = arrayOf<String>()
        for(card in hardestCards) {
            errors += card.errors
            terms += "\"${card.term}\""
        }
        var str = terms.joinToString(separator = ", ")
        println("The hardest cards are $str. You have ${hardestCards[0].errors} errors answering them.\n")
        LOG.add("The hardest cards are $str. You have ${hardestCards[0].errors} errors answering them.\n")
    }
}

fun log() {
    println("File name: ")
    LOG.add("File name: ")
    var fileName = readLine()!!
    LOG.add(fileName)
    val file = File(fileName)
    var log = ""
    println("The log has been saved.\n")
    LOG.add(("The log has been saved.\n"))
    for(line in LOG) {
        log += line + "\n"
    }
    file.writeText(log)
}

fun import(file: String = "") {
    var updated = 0
    var size = cards.size
    var fileName = ""
    if(file.isBlank()) {
        println("File name:")
        LOG.add("File name:")
        fileName = readLine()!!
        LOG.add(fileName)
    } else {
        fileName = file
    }
    var file = File(fileName)
    if (!file.exists()) {
        println("File not found.\n")
        LOG.add("File not found.\n")
        return
    }
    val lines = file.readLines()
    var mark = false
    for (line in lines) {
        var parts = line.split(" : ")
        for (key in cards.keys) {
            mark = false
            if (cards[key]!!.term == parts[0]) {
                cards[key]!!.definition = parts[1]
                cards[key]!!.errors = parts[2].toInt()
                mark = true
                updated++
                break
            }
        }
        if(!mark) {
            val random = Random()
            var i = random.nextInt(100)
            cards.keys.forEach { if(i == it) i = random.nextInt(150)}
            cards[i] = Flashcard(parts[0], parts[1])
            cards[i]!!.errors = parts[2].toInt()
        }

    }
    println("${cards.size - size + updated} cards have been loaded.\n")
    LOG.add("${cards.size - size + updated} cards have been loaded.\n")
}

fun export(file: String = "") {
    var count = 0
    var fileName = ""
    if(file.isBlank()) {
        println("File name:")
        LOG.add("File name:")
        fileName = readLine()!!
        LOG.add(fileName)
    } else {
        fileName = file
    }
    var file = File(fileName)
    var toWrite = ""
    for(card in cards.values)
        toWrite += ("${card.term} : ${card.definition} : ${card.errors}\n")
    file.writeText(toWrite)
    val lines = file.readLines()
    for(line in lines)
        if(line.isNotBlank()) count++
    println("${count} cards have been saved.\n")
    LOG.add("${count} cards have been saved.\n")
}

fun addCard() {
    var mark = false
    var card = ""
    while(true) {
        println("The card: ")
        LOG.add("The card: ")
        card = readLine()!!.trim()
        LOG.add(card)
        cards.values.forEach { if (it.term == card) mark = true }
        if (mark) {
            println("The card \"$card\" already exists.\n ")
            LOG.add("The card \"$card\" already exists.\n ")
            break
        }

        println("The definition of the card:")
        LOG.add("The definition of the card:")
        var markDef = false
        var def = readLine()!!.trim()
        LOG.add(def)

        cards.values.forEach { if (it.definition == def) markDef = true }

        if(markDef) {
            println("The definition \"$def\" already exists. Try again: ")
            LOG.add("The definition \"$def\" already exists. Try again: ")
            break
        }
        val random = Random()
        var i = random.nextInt(100)
        // problematično
        cards.keys.forEach { if(i == it) i = random.nextInt(150)}
        cards[i] = Flashcard(card, def)
        println("The pair (\"$card\" : \"$def\") has been added.\n")
        LOG.add("The pair (\"$card\" : \"$def\") has been added.\n")
        break
    }
}

fun removeCard() {
    println("The card:")
    LOG.add("The card:")
    var name = readLine()!!
    LOG.add(name)
    for(i in cards.keys) {
        if(cards[i]!!.term == name) {
            cards.remove(i)
            println("The card has been removed\n")
            LOG.add("The card has been removed\n")
            return
        }
    }
    println("Can't remove \"$name\": there is no such card.\n")
    LOG.add("Can't remove \"$name\": there is no such card.\n")
}

fun ask() {
    println("How many times to ask?")
    LOG.add("How many times to ask?")
    var times = readLine()!!.toInt()
    LOG.add(times.toString())
    while(times > 0) {
loop@ for(card in cards.keys) {
        if(times == 0) break
        println("Print the definiton of \"${cards[card]!!.term}\":")
        LOG.add("Print the definiton of \"${cards[card]!!.term}\":")
        val guess = readLine()!!
        LOG.add(guess)
        if (cards[card]!!.guess(guess)) {
            println("Correct answer.")
            LOG.add("Correct answer.")
            times--
        } else {
            for (cardx in cards.values) {
                if (guess == cardx.definition) {
                    println("Wrong answer. The correct one is \"${cards[card]!!.definition}\", you've just written the definition of \"${cardx.term}\".\n")
                    LOG.add("Wrong answer. The correct one is \"${cards[card]!!.definition}\", you've just written the definition of \"${cardx.term}\".\n")
                    times--
                    continue@loop
                }
            }
            println("Wrong answer. The correct one is \"${cards[card]!!.definition}\".")
            LOG.add("Wrong answer. The correct one is \"${cards[card]!!.definition}\".")
            times--
        }
        }
    }
}
