package ar.edu.unicen.seminario.ddl.models

class ItemGames {
     var id: Int
     var name: String
     var relased: String? =null
     var rating: Double? =null
     var backgroundImage: String? = null

    constructor(id: Int, name: String, relased: String?, rating: Double?, backgroundImage: String?){
        this.id = id
        this.name = name
        if (relased != null) {
            this.relased = relased
        }
        if (rating != null) {
            this.rating = rating
        }
        if (backgroundImage != null) {
            this.backgroundImage = backgroundImage
        }
    }

    override fun toString(): String {
        return "ItemGames(id=$id, name='$name', relased=$relased, rating=$rating, backgroundImage=$backgroundImage)"
    }


}