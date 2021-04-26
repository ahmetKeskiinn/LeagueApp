package example.com.newapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Meta {  @SerializedName("plans")
@Expose
private List<Plan> plans = null;
    @SerializedName("sports")
    @Expose
    private List<Sport> sports = null;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    public List<Sport> getSports() {
        return sports;
    }

    public void setSports(List<Sport> sports) {
        this.sports = sports;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "plans=" + plans +
                ", sports=" + sports +
                ", pagination=" + pagination +
                '}';
    }
}

