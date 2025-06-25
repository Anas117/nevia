package com.example.myapplication.environment

class Environment() {
    companion object {
//        private const val ipAddress = "172.31.240.3:9004"
        private const val ipAddress = "172.31.252.174:8082"

        const val baseUrlDispatcher = "http://$ipAddress/dispatcher/"
        const val baseUrlNotification = "http://$ipAddress/notification/"


    }
}
