package com.leadinsource.prudentcook.net;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("q")
    public String queriedIngredients;

    @SerializedName("hits")
    public List<Hit> hits;


}
