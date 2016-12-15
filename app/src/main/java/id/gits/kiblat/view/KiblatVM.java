package id.gits.kiblat.view;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.util.Log;

import id.gits.kiblat.rxeventbus.MyRxBus;
import id.gits.kiblat.rxeventbus.RxEventBusProvider;
import id.gits.kiblat.location.MyLocation;
import id.gits.mvvmcore.viewmodel.GitsVM;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by yogi on 02/12/16.
 */
public class KiblatVM extends GitsVM {

    MyLocation mMyLocation;
    KiblatView mView;
    Subscription subscription;
    Location kabaaLocation;
    Location myLocation;
    MyRxBus myRxBus;
    Sensor mAccelerometer;
    SensorManager sensorManager;
    boolean accelerometerAvailable = false;
    Context mContext;
    public KiblatVM(Context context, KiblatView view) {
        super(context);
        mContext =context;
        mView =view;
        myRxBus = new MyRxBus();
        mMyLocation = new MyLocation(context);
        mView.initMap();
        //Location Connect
        mMyLocation.connect();


        kabaaLocation =new Location("Kabaa");
        kabaaLocation.setLatitude(21.422487);
        kabaaLocation.setLatitude(39.826206);
        sensorManager = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer =sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
      if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)!=null){
          Log.e( "KiblatVM: ", "Sensor ada");
      }else{

          mView.showError("Maaf Smartphone anda tidak mempunyai sensor Kompas");

      }
        myRxBus = RxEventBusProvider.provide();
        subscription=myRxBus.onEventMainThread(MyLocation.LocationMdl.class, new Action1<MyLocation.LocationMdl>() {
            @Override
            public void call(MyLocation.LocationMdl locationMdl) {
                Log.e( "call: ", String.valueOf(locationMdl.getLocation().getLatitude()));
                showAzimuth(locationMdl.getLocation(),kabaaLocation );
            }
        });

    }

    private void showAzimuth(Location myLocation, Location kabaaLocation) {

        mView.showAzimut(myLocation, kabaaLocation);

    }

    public void Destroy() {
        subscription.unsubscribe();
    }

    public interface KiblatView{
        void showAzimut(Location myLocation, Location kabaaLoc );
        void initMap();
        void showError(String message);
    }
}