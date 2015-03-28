package com.softvision.botanica.common.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * class used to listen for network connectivity changes
 */
public class NetworkConnectivity {

    private static final List<ConnectivityListener> listeners = new ArrayList<ConnectivityListener>();

    public static void addConnectivityListener(ConnectivityListener listener) {
        synchronized (listeners) {
            if (!listeners.contains(listener)) {
                listeners.add(listener);
            }
        }
    }

    public static void removeConnectivityListener(ConnectivityListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    private static void notifyListeners(boolean connected) {
        for (ConnectivityListener listener : listeners) {
            listener.onConnectivityChange(connected);
        }
    }

    /**
     * Receives android network connectivity intents and notifies listeners in
     */
    public static class ConnectivityBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            notifyListeners(isNetworkAvailable(context));
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] infos = connMgr.getAllNetworkInfo();

        for (NetworkInfo info : infos) {
            if (info != null && info.isConnected()) {
                return true;
            }
        }

        return false;
    }

    /**
     * @author bogdan.varga
     */
    public static interface ConnectivityListener {

        public void onConnectivityChange(boolean connected);

    }
}
