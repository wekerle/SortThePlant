package com.example.sorttheplants.businessLayer

import com.example.sorttheplants.businessLayer.Models.LevelModel

class LevelBuilder private constructor() {
    companion object {
        @Volatile private var instance: LevelBuilder? = null
        fun getInstance(): LevelBuilder {
            if (instance == null) {
                instance = LevelBuilder()
            }
            return instance!!
        }
    }

    private var myDic:MutableMap<Int,List<List<String>>> = mutableMapOf()

    fun buildLevel()
    {
        myDic.put(1, listOf(
            listOf("buz","arp","tor","nap"),
            listOf("arp", "arp","buz"),
            listOf("buz", "arp"),
            listOf("buz"),
            listOf("luc", "arp","buz"),
            listOf("buz", "arp","buz"),
            listOf("buz", "arp","buz"),
            listOf("buz", "arp","buz"),
            listOf("buz", "arp","buz"),
            listOf("buz", "arp","buz"),
            listOf("buz", "arp","buz"),
            listOf("buz", "arp","buz")
        ))
    }

    fun getLevelNextLevel(currentLevel: Int): List<List<String>>?
    {
        return myDic[currentLevel]
    }
}