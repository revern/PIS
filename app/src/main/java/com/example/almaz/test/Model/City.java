package com.example.almaz.test.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Айрат on 14.12.2015.
 */
public class City {
    @SerializedName("_id")
    public int _id;
    @SerializedName("name")
    public String name;
    @SerializedName("country")
    public String country;

}
