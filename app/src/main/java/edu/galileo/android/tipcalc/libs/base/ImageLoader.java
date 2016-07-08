package edu.galileo.android.tipcalc.libs.base;

import android.widget.ImageView;

/**
 * Created by carlos.gomez on 08/07/2016.
 */

public interface ImageLoader {
    void load(ImageView imageView, String URL);

    void setOnFinishedImageLoadingListener(Object listener); //este listener se ejecuta al terminar la carga
}