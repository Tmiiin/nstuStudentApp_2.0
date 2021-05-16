package com.example.nstustudentapp.schedule.data.model

import com.google.gson.annotations.SerializedName

class Teacher (
    @SerializedName("ID")
    var id: Int = 0,
    @SerializedName("NAME")
    var name: String? = null,
    @SerializedName("SURNAME")
    var surname: String? = null,

    @SerializedName("PATRONYMIC")
    var patronymic: String? = null,

    @SerializedName("FIO")
    var fio: String? = null,

    @SerializedName("DEGREE")
    var degree: String? = null,

    @SerializedName("POST")
    var post: String? = null,

    @SerializedName("MAIN_SUBDIVISION")
    var mainSubdivision: String? = null,

    @SerializedName("CABINET")
    var cabinet: String? = null,

    @SerializedName("EMAIL")
    var email: String? = null,

    @SerializedName("SITE_LINK")
    var siteLink: String? = null,

    @SerializedName("PORTRAIT_URL")
    var portrait_url: String? = null
){
}
