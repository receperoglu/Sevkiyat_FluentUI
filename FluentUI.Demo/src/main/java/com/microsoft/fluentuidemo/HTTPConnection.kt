package com.microsoft.fluentuidemo


import android.util.Log
import com.microsoft.fluentuidemo.util.DomainName.getURL
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.ProtocolException
import java.net.URL


class HTTPConnection {
    fun makeServiceCall(param: String): String? {
        val baseurl = getURL()
        var response: String? = null
        try {
            var parametre = ""
            parametre = if (param === "post/CorpList.ashx") {
                "https://recep.space/abi/post/CorpList.ashx"
            } else if (param === "post/SaleType.ashx") {
                "https://recep.space/abi/post/SaleType.ashx"
            } else if (param.contains("post/AddArticel.ashx")) {
                "https://recep.space/abi/$param"
            } else if (param.contains("post/AddOrder.ashx")) {
                "https://recep.space/abi/$param"
            } else if (param.contains("post/ProductType.ashx")) {
                "https://recep.space/abi/$param"
            } else {
                baseurl + param
            }
            val url = URL(parametre)
            Log.e(TAG, "Site Adresi  : $url")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            val `in`: InputStream = BufferedInputStream(conn.inputStream)
            response = convertStreamToString(`in`)
        } catch (e: MalformedURLException) {
            Log.e(TAG, "MalformedURLException: " + e.message)
        } catch (e: ProtocolException) {
            Log.e(TAG, "ProtocolException: " + e.message)
        } catch (e: IOException) {
            Log.e(TAG, "IOException: " + e.message)
        } catch (e: Exception) {
            Log.e(TAG, "Exception: " + e.message)
        }
        return response
    }

    private fun convertStreamToString(`is`: InputStream): String {
        val reader = BufferedReader(InputStreamReader(`is`))
        val sb = StringBuilder()
        var line: String?
        try {
            while (reader.readLine().also { line = it } != null) {
                sb.append(line).append('\n')
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return sb.toString()
    }

    companion object {
        private val TAG = HTTPConnection::class.java.simpleName
    }
}
