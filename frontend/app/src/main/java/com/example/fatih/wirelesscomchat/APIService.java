package com.example.fatih.wirelesscomchat;

import com.example.fatih.wirelesscomchat.model.TransferInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by vmanohar on 3/19/18.
 */

public interface APIService {

    @Headers({
            "Content-Type: application/json",
            "Cache-Control: no-cache",
            "Postman-Token: e51eb09f-afcd-f9b7-f19b-27583979b002"
    })
    @POST("/payments/kafka")
    Call<TransferInfo> makePayment(@Body TransferInfo transferInfo);
}
