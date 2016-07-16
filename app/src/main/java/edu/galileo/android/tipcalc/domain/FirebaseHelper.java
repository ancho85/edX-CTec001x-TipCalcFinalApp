package edu.galileo.android.tipcalc.domain;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

/**
 * Created by carlos.gomez on 08/07/2016.
 */
public class FirebaseHelper {
    private static final String TAG = FirebaseHelper.class.getSimpleName();
    private final static String TIPS_PATH = "tips";
    private final static String USERS_PATH = "users";
    private final static String FIREBASE_URL = "https://edx-ctec001x-tipcalcfinalapp.firebaseio.com/";

    private Firebase dataReference;
    private AccessTokenTracker facebookAccessTokenTracker;
    private AuthData authData;
    private Firebase.AuthStateListener authStateListener;

    private static class SingletonHolder {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    public static FirebaseHelper getInstance() {
        // Una sola instancia en toda la app
        return SingletonHolder.INSTANCE;
    }

    public FirebaseHelper() {
        dataReference = new Firebase(FIREBASE_URL);
        facebookAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d(TAG, "token changed: " + currentAccessToken);
                FirebaseHelper.this.onFacebookAccessTokenChange(currentAccessToken);
            }
        };
        authStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                Log.d(TAG, "authData state changed: " + authData);
                FirebaseHelper.this.setAuthenticatedUser(authData);
            }
        };
        dataReference.addAuthStateListener(authStateListener);
    }

    public Firebase getDataReference() {
        return dataReference;
    }

    public String getAuthUserId() {
        AuthData authData = dataReference.getAuth();
        String userId = null;
        if (authData != null && authData.getProvider().equals("facebook")) {
            Map<String, Object> providerData = authData.getProviderData();
            userId = providerData.get("id").toString();
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
        String userId = getAuthUserId();
        if (userId == null){
            userId = "";
        }
        return getUserReference(userId);
    }

    public void signOff(){
        dataReference.unauth();
    }

    public Firebase getMyTipsReference(){
        return getMyUserReference().child(TIPS_PATH);
    }

    /**
     * Facebook related
     */

    private void onFacebookAccessTokenChange(AccessToken token) {
        Log.d(TAG, "auth access token change: " + token);
        if (token != null) {
            authWithFirebase(token.getToken());
        } else {
            // Logged out of Facebook and currently authenticated with Firebase using Facebook, so do a logout
            if (this.authData != null && this.authData.getProvider().equals("facebook")) {
                destroyTrackerAndListener();
                logout();
            }
        }
    }

    public void destroyTrackerAndListener(){
        Log.d(TAG, "destroying listener");
        if (facebookAccessTokenTracker != null){
            facebookAccessTokenTracker.stopTracking();
        }
        dataReference.removeAuthStateListener(authStateListener);
    }

    public void logout() {
        Log.d(TAG, "login out");
        if (this.authData != null) {
            FirebaseHelper.getInstance().signOff();
            if (this.authData.getProvider().equals("facebook")) {
                /* Logout from Facebook */
                LoginManager.getInstance().logOut();
            }
            /* Update authenticated user and show login buttons */
            setAuthenticatedUser(null);
        }
    }

    /**
     * Once a user is logged in, take the mAuthData provided from Firebase and "use" it.
     */
    private void setAuthenticatedUser(AuthData authData) {
        Log.d(TAG, "Setting auth user: " + authData);
        if (authData != null) {
            String name;
            if (authData.getProvider().equals("facebook")){
                name = (String) authData.getProviderData().get("displayName");
                Log.d(TAG, "Auth user: " + name);
            }
        }
        this.authData = authData;
    }

    /**
     * This method will attempt to authenticate a user to firebase given an oauth_token (and other
     * necessary parameters depending on the provider)
     */
    public void authWithFirebase(String oauth_token) {
        Log.d(TAG, "Auth with firebase: " + oauth_token);
        dataReference.authWithOAuthToken("facebook", oauth_token, new AuthResultHandler("facebook"));
    }

    /**
     * Utility class for authentication results
     */
    private class AuthResultHandler implements Firebase.AuthResultHandler {

        private final String provider;

        public AuthResultHandler(String provider) {
            this.provider = provider;
        }

        @Override
        public void onAuthenticated(AuthData authData) {
            Log.i(TAG, provider + " auth successful");
            setAuthenticatedUser(authData);
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            Log.d(TAG, provider + " auth onAuthenticationError" + firebaseError.toString());
        }
    }
}
