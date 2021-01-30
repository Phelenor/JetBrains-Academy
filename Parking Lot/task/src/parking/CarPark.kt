package parking

class CarPark {
    private val carPark = ArrayList<ParkingSpot>()
    public var counter = 0
    public var spots = 0

    fun addSpot(number: Int) {
        if(!carPark.contains(ParkingSpot(number))) {
            carPark.add(ParkingSpot(number))
            spots++
        }
    }

    fun park(color: String, reg: String) {
        for(spot in carPark) {

            if(!spot.isOccupied()) {
                spot.occupy(color, reg)
                counter++
                println("${color.capitalize()} car parked on the spot ${spot.number}.")
                return
            }
        }
        println("Sorry, parking lot is full.")
    }

    fun clear() {
        carPark.clear()
    }

    fun leave(num: Int) {
        if(carPark.size >= num - 1 && carPark.size != 0 && carPark.get(num - 1).isOccupied()) {
            carPark.get(num - 1).free()
            counter--
            println("Spot $num is free.")
        } else {
            println("There is no car in the spot $num.")
        }
    }

    fun status() {
        var i = 0
        for(spot in carPark){
            i++
            if(spot.isOccupied())
                println("$i $spot")
        }
    }

    fun findByColor(color: String) {
        val cars = ArrayList<String>()
        for(car in carPark) {
            if(car.getColor() == color.toUpperCase())
                cars.add(car.registration!!)
        }
        if(cars.isEmpty()) {
            println("No cars with color $color were found.")
            return
        }
        println(cars.joinToString(separator = ", "))
    }

    fun spotByReg(reg: String) {
        val spots = ArrayList<Int>()
        for(i in 0 until carPark.size ) {
            if(carPark.get(i).registration == reg)
                spots.add(i + 1)
        }
        if(spots.isEmpty()) {
            println("No cars with registration number $reg were found.")
            return
        }
        println(spots.joinToString(separator = ", "))
    }

    fun spotByColor(color: String) {
        val spots = ArrayList<Int>()
        for(i in 0 until carPark.size ) {
            if(carPark.get(i).getColor() == color.toUpperCase())
                spots.add(i + 1)
        }
        if(spots.isEmpty()) {
            println("No cars with color $color were found.")
            return
        }
        println(spots.joinToString(separator = ", "))
    }
}