package com.mcevoy.mcevoy_homeadvisor.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ProData(
    @ColumnInfo(name = "entity_id")
    @SerializedName("entityId")
    val entityId: String?,

    @ColumnInfo(name = "company_name")
    @SerializedName("companyName")
    val companyName: String?,

    @ColumnInfo(name = "rating_count")
    @SerializedName("ratingCount")
    val ratingCount: String?,

    @ColumnInfo(name = "composite_rating")
    @SerializedName("compositeRating")
    val compositeRating: String?,

    @ColumnInfo(name = "company_overview")
    @SerializedName("companyOverview")
    val companyOverview: String?,

    @SerializedName("canadianSP")
    val canadianSP: Boolean?,

    @SerializedName("spanishSpeaking")
    val spanishSpeaking: Boolean?,

    @ColumnInfo(name = "phone_number")
    @SerializedName("phoneNumber")
    val phoneNumber: String?,

    @SerializedName("latitude")
    val latitude: Float?,

    @SerializedName("longitude")
    val longitude: Float?,

    @ColumnInfo(name = "services_offered")
    @SerializedName("servicesOffered")
    val servicesOffered: String?,

    @SerializedName("specialty")
    val specialty: String?,

    @ColumnInfo(name = "primary_location")
    @SerializedName("primaryLocation")
    val primaryLocation: String?,

    @ColumnInfo(name = "email")
    @SerializedName("email")
    val email: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
