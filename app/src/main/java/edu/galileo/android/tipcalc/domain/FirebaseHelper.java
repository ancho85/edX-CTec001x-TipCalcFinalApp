package edu.galileo.android.tipcalc.domain;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import java.util.Map;

/**
 * Created by carlos.gomez on 08/07/2016.
 */
public class FirebaseHelper {
    private Firebase dataReference;
    private final static String TIPS_PATH = "tips";
    private final static String USERS_PATH = "users";
    private final static String FIREBASE_URL = "https://edx-ctec001x-tipcalcfinalapp.firebaseio.com/";

    private static class SingletonHolder {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    public static FirebaseHelper getInstance() {
        // Una sola instancia en toda la app
        return SingletonHolder.INSTANCE;
    }

    public FirebaseHelper() {
        this.dataReference = new Firebase(FIREBASE_URL);
    }

    public Firebase getDataReference() {
        return dataReference;
    }

    public String getAuthUserId() {
        AuthData authData = dataReference.getAuth();
        String userId = null;
        if (authData != null && authData.getProvider().equals("facebook")) {
            Map<String, Object> providerData = authData.getProviderData();
            userId = providerData.get("uid").toString();
        }
        return userId;
    }

    public Firebase getUserReference(String fbId) {
        Firebase userReference = null;
        if (fbId != null) {
            userReference = dataReference.getRoot().child(USERS_PATH).child(fbId);
        }
        return userReference;
    }

    public Firebase getMyUserReference() {
        return getUserReference(getAuthUserId());
    }
}
