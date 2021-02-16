package com.study.rxjava.ch01;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class Sample {
    public static void main(String[] args) throws InterruptedException {
        Observable.just(100, 200, 300, 400, 500)
                .doOnNext(data -> System.out.println(getThreadName() + " : #doOnNext() : " + data))
                .subscribeOn(Schedulers.io())   // main Thread 가 아닌 다른 Thread 에서 실행 (해당 Thread 가 동작하기 전에 main Thread 가 멈춘다)
                                                // subscribeOn() 메소드는 data 의 발행, 흐름을 결정짓는 메소드
                .observeOn(Schedulers.computation())    // 발행된 데이터를 가공하고 구독해서 처리하는 Thread 를 지정한다.
                .filter(number -> number > 300)
                .subscribe(num -> System.out.println(getThreadName() + " : result : " + num));

        Thread.sleep(500);  // subscribeOn() 메소드로 인해 main Thread 가 멈추는 걸 방지하기 위해 main Thread 를 0.5초 딜레이 시킨다
    }

    private static String getThreadName() {
        return Thread.currentThread().getName();
    }
}
