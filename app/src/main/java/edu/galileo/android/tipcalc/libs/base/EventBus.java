package edu.galileo.android.tipcalc.libs.base;

/**
 * Created by carlos.gomez on 08/07/2016.
 */
public interface EventBus {
    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Object event);

}
