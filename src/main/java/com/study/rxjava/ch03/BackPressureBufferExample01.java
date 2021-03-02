package com.study.rxjava.ch03;

import com.study.rxjava.utils.LogType;
import com.study.rxjava.utils.Logger;
import com.study.rxjava.utils.TimeUtil;
import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class BackPressureBufferExample01 {
    public static void main(String[] args) {
        System.out.println("# start : " + TimeUtil.getCurrentTimeFormatted());
        Flowable.interval(300L, TimeUnit.MILLISECONDS)  // 0.3초 마다 데이터 통제
                .doOnNext(data -> Logger.log("#interval doOnNext()", data))
                .onBackpressureBuffer(
                        2,      // 버퍼 안에 데이터가 들어갈 수 있는 데이터 갯수
                        () -> Logger.log("overflow!"),
                        BackpressureOverflowStrategy.DROP_LATEST
                )
                .doOnNext(data -> Logger.log("#onBackPressureBuffer doOnNext()", data))
                .observeOn(Schedulers.computation(), false, 1)  // bufferSize : 소비자가 생산자에게 요청하는 데이터 개수 (매번 1개씩 데이터를 요청하겠다.)
                .subscribe(
                        data -> {
                            TimeUtil.sleep(1000L);  // 생산자에서 통제되는 속도보다 처리되는 속도가 느리게 설정
                            Logger.log(LogType.ON_NEXT, data);
                        }, error -> Logger.log(LogType.ON_ERROR, error)
                );

        TimeUtil.sleep(2800L);
    }
}
