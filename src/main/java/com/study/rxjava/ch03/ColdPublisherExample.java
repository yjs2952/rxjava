package com.study.rxjava.ch03;

import io.reactivex.Flowable;

public class ColdPublisherExample {
    public static void main(String[] args) {
        Flowable<Integer> flowable = Flowable.just(1, 3, 5, 7);
        // observable 도 cold publisher

        flowable.subscribe(data -> System.out.println("구독자1 : " + data));
        flowable.subscribe(data -> System.out.println("구독자2 : " + data));
    }
}
