package com.dev.mevur.baselocation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mevur on 01/22/18.
 */

public interface BaseInfoService {
    @GET("/cell/")
    Call<ResponseBody> getBaseStationInfo(@Query("mcc") int mcc,
                                          @Query("mnc") int mnc,
                                          @Query("lac") Integer lac,
                                          @Query("ci") Integer ci);
}
