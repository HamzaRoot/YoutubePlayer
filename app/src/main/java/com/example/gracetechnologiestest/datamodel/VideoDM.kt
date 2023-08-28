package com.example.gracetechnologiestest.datamodel

import java.io.Serializable

data class VideoDM(var ex_id: String,
var ex_title: String,
var ex_identity: String,
var ex_description: String,
var ex_duration: Double,
var ex_url: String,
var ex_cat_id: String,
var ex_cat_name: String,
var ex_level_id: String,
var ex_level_name: String): Serializable {
    constructor() : this(
        "", "", "",
        "", 0.0, "",
        "", "", "",
        ""
    )
}
