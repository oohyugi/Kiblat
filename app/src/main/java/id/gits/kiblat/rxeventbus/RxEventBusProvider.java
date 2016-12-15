package id.gits.kiblat.rxeventbus;

/**
 * Created by yogi on 05/12/16.
 */
public class RxEventBusProvider {

    public static final MyRxBus rxBus = new MyRxBus();

    public static MyRxBus provide(){
        return rxBus;
    }

}