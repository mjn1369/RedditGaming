package apps.mjn.data.factory

import java.util.concurrent.ThreadLocalRandom

internal class DataFactory {

    companion object Factory {

        fun randomString(length: Int): String {
            val chars = "0123456789abcdefghijklnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            var result = ""
            repeat(length){
                result += chars[Math.floor(Math.random() * chars.length).toInt()]
            }
            return result
        }

        fun randomLong(): Long {
            return ThreadLocalRandom.current().nextInt(0, 1000 + 1).toLong()
        }
    }
}