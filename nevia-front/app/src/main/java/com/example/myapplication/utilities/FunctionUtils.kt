package com.example.myapplication.utilities

fun convertDateAndTimeToString(date: String, time: String): String {
    return formatDay(date) + " " + formatTime(time)
}

fun convertDateAndTimeToTimestamp(date: String, time: String): String {
    return formatDay(date) + "T" + formatTime(time)
}

fun formatDay(date: String): String {
    var dateString = date.split("-")[2] + "-";

    // month
    if (date.split("-")[1].toString().length == 1) {
        val month = "0" + date.split("-")[1]
        dateString += "$month-"
    } else
        dateString += date.split("-")[1] + "-";

    // day
    if (date.split("-")[0].toString().length == 1) {
        val month = "0" + date.split("-")[0]
        dateString += month
    } else
        dateString += date.split("-")[0];

    return dateString
}

fun formatDayFr(date: String): String {
    var dateString = ""

    // day
    if (date.split("-")[0].toString().length == 1) {
        val month = "0" + date.split("-")[0] + "-"
        dateString += month
    } else
        dateString += date.split("-")[0] + "-"

    // month
    if (date.split("-")[1].toString().length == 1) {
        val month = "0" + date.split("-")[1]
        dateString += month
    } else
        dateString += date.split("-")[1]

    return dateString + "-" + date.split("-")[2]
}



fun formatTime(time: String): String {
    var timeString = ""
    // hour
    if (time.split(":")[0].toString().length == 1) {
        val hour = "0" + time.split(":")[0]
        timeString += "$hour:"
    } else
        timeString += time.split(":")[0] + ":"

    //minutes
    if (time.split(":")[1].toString().length == 1) {
        val minutes = "0" + time.split(":")[1]
        timeString += minutes
    } else
        timeString += time.split(":")[1]

    return timeString + ":00"
}