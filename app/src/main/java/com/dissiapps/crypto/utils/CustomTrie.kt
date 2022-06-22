package com.dissiapps.crypto.utils

import java.util.*
import kotlin.collections.HashMap

class TrieNode {
    val characters = HashMap<Char, TrieNode?>()
    var isEndOfWord = false
}

class CustomTrie {

    private val root = TrieNode()

    fun insert(string: String){
        var current = root
        for(char in string){
            if(current.characters[char] == null){
                current.characters[char] = TrieNode()
            }
            current = current.characters[char]!!
        }
        current.isEndOfWord = true
    }

    fun search(string: String): List<String> {
        var current = root //se

        for(char in string){
            current = current.characters[char] ?: return emptyList()
        }

        val result = mutableListOf<String>()
        val stack = Stack<Pair<TrieNode, String>>()
        stack.push(current to string)

        while(stack.isNotEmpty() && result.size < 5){
            val temp = stack.pop()
            val node = temp.first
            val str = temp.second

            if(node.isEndOfWord){
                result.add(str)
            }
            for((char, nd) in node.characters){
                stack.push(nd!! to str + char)
            }

        }

        return result
    }

}