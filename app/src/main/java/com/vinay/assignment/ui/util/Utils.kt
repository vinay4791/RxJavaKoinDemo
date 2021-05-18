package com.vinay.assignment.ui.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.vinay.assignment.ui.util.AppConstants.IMAGE_URL_PREFIX
import java.text.SimpleDateFormat
import java.util.*

const val WIFI = "WIFI"
const val MOBILE = "MOBILE"

class Utils(private val appContext: Context) {

    fun hasInternet(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                ?: return false
            return when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            var haveConnectedWifi = false
            var haveConnectedMobile = false
            val netInfo = connectivityManager.allNetworkInfo
            for (ni in netInfo) {
                if (ni.typeName.equals(WIFI, ignoreCase = true)) if (ni.isConnected) haveConnectedWifi = true
                if (ni.typeName.equals(MOBILE, ignoreCase = true)) if (ni.isConnected) haveConnectedMobile = true
            }
            return haveConnectedWifi || haveConnectedMobile
        }
    }

     fun getFormattedDate(releaseDate: String): String {
        var dateString: String = releaseDate
        dateString = try {
            val inputFormat = SimpleDateFormat("yyyy-dd-mm", Locale.ENGLISH)
            val value = inputFormat.parse(dateString)
            val outputFormat = SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH)
            outputFormat.format(value)
        } catch (e: Exception) {
            releaseDate
        }
        return dateString
    }

    fun getFormattedImageUrl(imageUtl: String) : String{
        return IMAGE_URL_PREFIX + imageUtl
    }

    fun getProgressDrawable(context: Context): CircularProgressDrawable {
        return CircularProgressDrawable(context).apply {
            strokeWidth = 10f
            centerRadius = 20f
            start()
        }
    }

}

