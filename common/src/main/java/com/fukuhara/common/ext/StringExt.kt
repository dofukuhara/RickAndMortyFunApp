package com.fukuhara.common.ext

/*
    Given a String with the following pattern : .*<API_NAME>/?page=(\d+), as in example below:
        https://rickandmortyapi.com/api/character/?page=2
    this Extension Function will parse this String and try to return the Page Index as an String.
    Otherwise, NULL will be returned.
 */
fun String.getPageIndex(api: String): String? {
    val regex = "^.*$api/?\\?page=(\\d+)"

    val findWithRegex = Regex(regex).find(this)
    return findWithRegex?.run {
        val (index) = destructured
        index
    }
}

fun String.getSeasonAndEpisode(): Pair<String, String>? {
    val regex = "[sS](\\d+)[eE](\\d+)"

    val findWithRegex = Regex(regex).find(this)
    return findWithRegex?.run {
        val (season, episode) = destructured
        Pair(season, episode)
    }
}
