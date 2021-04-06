package com.example.nstustudentapp


class Constants {

    companion object {
       const val error1 = "Unacceptable password or username"
        const val error2 = "error in authorization"
        const val error3 = "error in username or password"
        const val URLForAuthentification = "https://login.nstu.ru/ssoservice/json/"
        const val URLforGetSchedule = "http://217.71.129.139:4493/"  //"http://mobile-backend.cloud.nstu.ru/api/v0.1/semester_schedule/"
        const val URLforGetStudent = "http://mobile-backend.cloud.nstu.ru:80/api/v0.1/"
        const val APP_PREFERENCES = "userData"
        const val TOKEN = "tokenID"
    }
}