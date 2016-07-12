package edu.galileo.android.tipcalc.domain;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by carlos.gomez on 12/07/2016.
 */
public class FacebookFirebaseHelper {
    private static final String TAG = FacebookFirebaseHelper.class.getSimpleName();

    /* The callback manager for Facebook */
    private CallbackManager mFacebookCallbackManager;
    /* Used to track user logging in/out off Facebook */
    private AccessTokenTracker mFacebookAccessTokenTracker;

    /* Data from the authenticated user */
    private AuthData mAuthData;
    /* A reference to the Firebase */
    private Firebase mFirebaseRef;
    /* Listener for Firebase session changes */
    private Firebase.AuthStateListener mAuthStateListener;

    public FacebookFirebaseHelper() {
        this.mFirebaseRef = FirebaseHelper.getInstance().getDataReference();

        mFacebookAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                FacebookFirebaseHelper.this.onFacebookAccessTokenChange(currentAccessToken);
            }
        };
        mAuthStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                FacebookFirebaseHelper.this.setAuthenticatedUser(authData);
            }
        };
        mFirebaseRef.addAuthStateListener(mAuthStateListener);
    }

    private void onFacebookAccessTokenChange(AccessToken token) {
        if (token != null) {
            mFirebaseRef.authWithOAuthToken("facebook", token.getToken(), new AuthResultHandler("facebook"));
        } else {
            // Logged out of Facebook and currently authenticated with Firebase using Facebook, so do a logout
            if (this.mAuthData != null && this.mAuthData.getProvider().equals("facebook")) {
                destroyTrackerAndListener();
                logout();
            }
        }
    }

    public void destroyTrackerAndListener(){
        if (mFacebookAccessTokenTracker != null){
            mFacebookAccessTokenTracker.stopTracking();
        }
        mFirebaseRef.removeAuthStateListener(mAuthStateListener);
    }

    public void logout() {
        if (this.mAuthData != null) {
            FirebaseHelper.getInstance().signOff();
            if (this.mAuthData.getProvider().equals("facebook")) {
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
        if (authData != null) {
            String name = null;
            if (authData.getProvider().equals("facebook")
                    || authData.getProvider().equals("google")
                    || authData.getProvider().equals("twitter")) {
                name = (String) authData.getProviderData().get("displayName");
                Log.d(TAG, "Auth user: " + name);
            }
        }
        this.mAuthData = authData;
    }

    /**
     * This method will attempt to authenticate a user to firebase given an oauth_token (and other
     * necessary parameters depending on the provider)
     */
    public void authWithFirebase(String oauth_token) {
        mFirebaseRef.authWithOAuthToken("facebook", oauth_token, new AuthResultHandler("facebook"));
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

