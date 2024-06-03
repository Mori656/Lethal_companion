package com.example.lethal_companion
data class ResponseModel(
    val record: Record
)

data class Record(
    val Monsters: List<ElementMonsters>,
    val Store: List<ElementStore>,
    val Logs: List<ElementLogs>,
    val Tips: List<ElementTips>,
    val Game: MutableList<ElementGame>
)

data class ElementMonsters(
    val id: Int,
    val name: String,
    val dangerLevel: String,
    val sName: String,
    val desc: String,
    val img: String
)

data class ElementStore(
    val id: Int,
    val name: String,
    val price: Int,
    val desc: String,
    val img: String
)

data class ElementLogs(
    val id: Int,
    val name: String,
    val desc: String,
    val img: String
)

data class ElementTips(
    val id: Int,
    val name: String,
    val desc: String
)

data class ElementGame(
    var name: String,
    var hiScore: Int
)