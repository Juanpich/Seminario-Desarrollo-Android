package ar.edu.unicen.seminario.ddl.models

class FiltersGame {
    var genres: List<Genre>?= emptyList()
    var platforms: List<Platform>?= emptyList()
    var stores: List<Store>?= emptyList()
    constructor(
        listG: List<Genre>?,
        listP: List<Platform>?,
        listD: List<Store>?
    ) {
        this.genres = listG
        this.platforms = listP
        this.stores = listD
    }
    fun containsData(): Boolean{
        return this.genres != null || this.platforms != null || this.stores != null
    }

    override fun toString(): String {
        return "FiltersGame(genres=$genres, platforms=$platforms, developers=$stores)"
    }
}
class Genre {
    var id: Int
    var name: String
    constructor(
        id: Int,
        name: String
    ){
        this.id = id
        this.name = name

}

}
class Platform {
    var id: Int
    var name: String
    constructor(
        id: Int,
        name: String
    ){
        this.id = id
        this.name = name

}
}
class Store{
    var id: Int
    var name: String
    constructor(
        id: Int,
        name: String
    ){
        this.id = id
        this.name = name

}

}