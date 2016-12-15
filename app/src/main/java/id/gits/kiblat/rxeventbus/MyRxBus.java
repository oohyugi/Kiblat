package id.gits.kiblat.rxeventbus;


import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by yogi on 02/12/16.
 */
public class MyRxBus {


    public final Subject<Object, Object> subject = new SerializedSubject<>(PublishSubject.create());

    public <T> Subscription onEvent(Class<T> tClass, Action1<T> handler) {
        return subject
                .ofType(tClass)
                .subscribe(handler);
    }

    public <T> Subscription onEventMainThread(Class<T> tClass, Action1<T> handler) {
        return subject
                .ofType(tClass)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handler);
    }

    public void post(Object object) {
        subject.onNext(object);
    }


}