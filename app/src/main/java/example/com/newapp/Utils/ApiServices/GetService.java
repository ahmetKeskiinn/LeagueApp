package example.com.newapp.Utils.ApiServices;

import java.util.List;

import example.com.newapp.Models.TeamModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetService {
    @GET("teams/season/16020")
    Call<TeamModel> WhatIsTeam (@Query("api_token") String token);
}
