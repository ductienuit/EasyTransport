package com.transport.easytransport;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import transportapisdk.StopQueryOptions;
import transportapisdk.TransportApiClient;
import transportapisdk.TransportApiClientSettings;
import transportapisdk.TransportApiResult;
import transportapisdk.models.Stop;

public class DataHelper {
    private static DataHelper mInstance = null;
    private String mClientId;
    private String mClientSecret;
    private static Context mContext;

    private TransportApiClientSettings mApiSettings;
    private TransportApiClient mApiClient;


    private DataHelper(Context context) {
        mClientId = context.getResources().getString(R.string.transportApiClientId);
        mClientSecret = context.getResources().getString(R.string.transportApiClientSecret);

        mApiSettings = new TransportApiClientSettings(mClientId, mClientSecret);
        mApiClient = new TransportApiClient(mApiSettings);
    }

    public static DataHelper getInstance(Context context) {
        mContext = context;
        if (mInstance == null)
            mInstance = new DataHelper(context);

        return mInstance;
    }

    public ArrayList<List<Stop>> getStopNearBy(double lat, double lon, int radius) {
        ArrayList<List<Stop>> res = new ArrayList<>();
        StopQueryOptions options = StopQueryOptions.defaultQueryOptions();
        TransportApiResult<List<Stop>> busRes = mApiClient.getStopsNearby(options, lat, lon, radius);
        if (!busRes.isSuccess) {
            return null;
        }

        res.add(busRes.data);
        return res;
    }
}
