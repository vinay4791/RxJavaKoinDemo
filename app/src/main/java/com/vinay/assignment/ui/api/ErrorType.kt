package com.vinay.assignment.ui.api

import androidx.annotation.StringRes
import com.vinay.assignment.R

enum class ErrorType(@StringRes val errorCauseString: Int) {
    NO_INTERNET_CONNECTION(R.string.no_internet_connection_message),
    SERVER(R.string.server_error),
    UNKNOWN(R.string.unknown_error);
    fun value(): Int {
        return errorCauseString
    }
}