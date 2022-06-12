package com.example.navfragcruddb.model

import java.io.Serializable

data class Event(
    var name: String,
    var category: String,
    var date: String
) : Serializable
