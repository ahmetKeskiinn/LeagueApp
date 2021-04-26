package example.com.newapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datum {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("legacy_id")
    @Expose
    private Integer legacyId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("short_code")
    @Expose
    private String shortCode;
    @SerializedName("twitter")
    @Expose
    private Object twitter;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;
    @SerializedName("national_team")
    @Expose
    private Boolean nationalTeam;
    @SerializedName("founded")
    @Expose
    private Integer founded;
    @SerializedName("logo_path")
    @Expose
    private String logoPath;
    @SerializedName("venue_id")
    @Expose
    private Integer venueId;
    @SerializedName("current_season_id")
    @Expose
    private Integer currentSeasonId;
    @SerializedName("is_placeholder")
    @Expose
    private Boolean isPlaceholder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(Integer legacyId) {
        this.legacyId = legacyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public Object getTwitter() {
        return twitter;
    }

    public void setTwitter(Object twitter) {
        this.twitter = twitter;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Boolean getNationalTeam() {
        return nationalTeam;
    }

    public void setNationalTeam(Boolean nationalTeam) {
        this.nationalTeam = nationalTeam;
    }

    public Integer getFounded() {
        return founded;
    }

    public void setFounded(Integer founded) {
        this.founded = founded;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public Integer getVenueId() {
        return venueId;
    }

    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    public Integer getCurrentSeasonId() {
        return currentSeasonId;
    }

    public void setCurrentSeasonId(Integer currentSeasonId) {
        this.currentSeasonId = currentSeasonId;
    }

    public Boolean getIsPlaceholder() {
        return isPlaceholder;
    }

    public void setIsPlaceholder(Boolean isPlaceholder) {
        this.isPlaceholder = isPlaceholder;
    }

    @Override
    public String toString() {
        return  id +"!"+ name;
    }
}
