package com.vinay.assignment.ui.rx

import io.reactivex.functions.Function

interface Converter<T, R> : Function<T, R>