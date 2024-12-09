package com.example.se121p11new.core.presentation.utils

import org.mongodb.kbson.ObjectId

fun ObjectId.toIdString() : String {
    return this.toString().substringAfter("(").substringBefore(")")
}