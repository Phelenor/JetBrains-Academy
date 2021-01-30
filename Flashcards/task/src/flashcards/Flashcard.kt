package flashcards

class Flashcard(val term: String = "", var definition: String = "") {
    var errors: Int = 0

    fun guess(guess: String): Boolean {
        if(guess == this.definition)
            return true
        errors++
        return false
    }
}