package parking

class ParkingSpot(val number: Int) {

    private var color: String? = null
    public var registration: String? = null
    public var occupied: Boolean = false

    fun occupy(color: String, reg: String) {
        this.color = color
        this.occupied = true
        this.registration = reg
    }


    fun free() {
        this.color = null
        this.occupied = false
    }

    fun isOccupied(): Boolean {
        return occupied
    }

    override fun toString(): String {
        return "$registration ${color!!.capitalize()}"
    }

    fun getColor(): String {
        return if(occupied)
            this.color!!.toUpperCase()
        else
            "BLANK"
    }

}