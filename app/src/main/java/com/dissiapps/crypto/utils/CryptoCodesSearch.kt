package com.dissiapps.crypto.utils

import android.content.Context
import com.dissiapps.crypto.R

/*
* not used
* */
class CryptoCodesSearch(
    context: Context
) {

    private val codes: Array<String> = context.resources.getStringArray(R.array.codes)
    private val trie = CustomTrie()

    init {
        for (code in codes){
            trie.insert(code)
        }
    }

    fun search(string: String): List<String> {
        return trie.search(string)
    }

}