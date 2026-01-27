package com.dna.ecommerceproj.data.model

import com.google.gson.annotations.SerializedName

data class addNote(
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    @SerializedName("user_id")
    val userId: Int,
)

data class requestNote(
    val title: String,
    val description: String?,
)

data class TodoItem(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("isCompleted")
    var isCompleted: Boolean,

    @SerializedName("data_hora")
    val data_hora: String?
)
