package com.study.rxjava.ch03;

import com.study.rxjava.utils.LogType;
import com.study.rxjava.utils.Logger;
import com.study.rxjava.utils.TimeUtil;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class MissingBackPressureExceptionExample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(1L, TimeUnit.MILLISECONDS)
                .doOnNext(data -> Logger.log(LogType.DO_ON_NEXT, data))     // doOnNext() : interval() 메소드에서 데이터를 통제할 때 호출되는 콜백 함수
                .observeOn(Schedulers.computation())    // observeOn() : 데이터를 처리하는 쓰레드를 별도로 분리할 수 있다.
                .subscribe(
                        data -> {
                            Logger.log(LogType.PRINT, "# 소비자 처리 대기 중..");
                            TimeUtil.sleep(1000L);  // publisher 가 데이터를 생성해서 통제하는 속도보다 subscriber 가 데이터를 처리하는 속도가 훨씬 느리다.
                            Logger.log(LogType.ON_NEXT, data);
                        },
                        error -> Logger.log(LogType.ON_ERROR, error),   // MissingBackpressureException 발생
                        () -> Logger.log(LogType.ON_COMPLETE)
                );

        Thread.sleep(2000L);
    }
}
