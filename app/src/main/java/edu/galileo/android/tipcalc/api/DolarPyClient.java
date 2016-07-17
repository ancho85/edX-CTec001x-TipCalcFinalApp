package edu.galileo.android.tipcalc.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import edu.galileo.android.tipcalc.R;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by carlos.gomez on 08/07/2016.
 */
public class DolarPyClient {
    private Retrofit retrofit;
    private final static String BASE_URL = "https://dolar.melizeche.com";

    public DolarPyClient(Context context) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getClient(context))
                .build();
    }

    public DolarPyService getDolarPyService(){
        return retrofit.create(DolarPyService.class);
    }

    private OkHttpClient getClient(Context context){
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream instream = context.getResources().openRawResource(R.raw.dolarpy);
            Certificate ca;
            ca = cf.generateCertificate(instream);
            KeyStore kStore = KeyStore.getInstance(KeyStore.getDefaultType());
            kStore.load(null, null);
            kStore.setCertificateEntry("ca", ca);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(kStore);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            okHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory())
                    .build();
        } catch (CertificateException
                | KeyStoreException
                | NoSuchAlgorithmException
                | IOException
                | KeyManagementException e) {
            e.printStackTrace();
        }
        return okHttpClient;
    }
}
