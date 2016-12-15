package id.gits.kiblat.location;

import android.location.Location;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;


/**
 * Created by yogi on 02/12/16.
 */
public class LocationRepo {

    Location location;

    public Location getLocation() {
        return location;
    }

    public LocationRepo(Location location) {
        this.location = location;
    }
}