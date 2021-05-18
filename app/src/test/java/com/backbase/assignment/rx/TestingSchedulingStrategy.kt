package com.vinay.assignment.rx

import com.vinay.assignment.ui.rx.SchedulingStrategyFactory
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

open class TestSchedulingStrategyFactory(subscribingScheduler: Scheduler, observingScheduler: Scheduler) :
    SchedulingStrategyFactory(subscribingScheduler, observingScheduler) {
    companion object {
        fun immediate(): TestSchedulingStrategyFactory {
            return TestSchedulingStrategyFactory(Schedulers.trampoline(), Schedulers.trampoline())
        }
    }
}