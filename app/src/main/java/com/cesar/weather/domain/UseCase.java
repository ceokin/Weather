package com.cesar.weather.domain;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by Cesar on 01/07/2017.
 */

//RxAndroid y RxJava
public abstract class UseCase {

    private Subscription subscription = Subscriptions.empty();

    protected  abstract Observable buildUseCaseObservable();

    public void execute(Subscriber useCaseSubscriber){
        this.subscription = this.buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(useCaseSubscriber);
    }

    public void unsubscribe(){
        if (!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

}
