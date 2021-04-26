package example.com.newapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plan {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("request_limit")
    @Expose
    private String requestLimit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestLimit() {
        return requestLimit;
    }

    public void setRequestLimit(String requestLimit) {
        this.requestLimit = requestLimit;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "name='" + name + '\'' +
                ", requestLimit='" + requestLimit + '\'' +
                '}';
    }
}

