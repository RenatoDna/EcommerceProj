package com.dna.ecommerceproj.data.model

import com.google.gson.annotations.SerializedName

data class addNote(
    val title: String,
    val description: String,
    @SerializedName("is_completed")
    val isCompleted: Boolean,
    @SerializedName("user_id")
    val userId: Int,
)

data class requestNote(
    val title: String,
    val description: String?,
    @SerializedName("is_completed")
    val isCompleted: Boolean,
)

data class TodoItem(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("is_completed")
    var isCompleted: Boolean,

    @SerializedName("data_hora")
    val data_hora: String?
)
