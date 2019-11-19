
package com.sashashtmv.vkapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("owner_id")
    @Expose
    private Integer ownerId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date")
    @Expose
    private Integer date;
    @SerializedName("comments")
    @Expose
    private Integer comments;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("photo_130")
    @Expose
    private String photo130;
    @SerializedName("photo_320")
    @Expose
    private String photo320;
    @SerializedName("photo_800")
    @Expose
    private String photo800;
    @SerializedName("photo_1280")
    @Expose
    private String photo1280;
    @SerializedName("first_frame_320")
    @Expose
    private String firstFrame320;
    @SerializedName("first_frame_160")
    @Expose
    private String firstFrame160;
    @SerializedName("first_frame_4096")
    @Expose
    private String firstFrame4096;
    @SerializedName("first_frame_130")
    @Expose
    private String firstFrame130;
    @SerializedName("first_frame_720")
    @Expose
    private String firstFrame720;
    @SerializedName("first_frame_1024")
    @Expose
    private String firstFrame1024;
    @SerializedName("first_frame_1280")
    @Expose
    private String firstFrame1280;
    @SerializedName("first_frame_800")
    @Expose
    private String firstFrame800;
    @SerializedName("access_key")
    @Expose
    private String accessKey;
    @SerializedName("repeat")
    @Expose
    private Integer repeat;
    @SerializedName("can_add")
    @Expose
    private Integer canAdd;
    @SerializedName("track_code")
    @Expose
    private String trackCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getPhoto130() {
        return photo130;
    }

    public void setPhoto130(String photo130) {
        this.photo130 = photo130;
    }

    public String getPhoto320() {
        return photo320;
    }

    public void setPhoto320(String photo320) {
        this.photo320 = photo320;
    }

    public String getPhoto800() {
        return photo800;
    }

    public void setPhoto800(String photo800) {
        this.photo800 = photo800;
    }

    public String getPhoto1280() {
        return photo1280;
    }

    public void setPhoto1280(String photo1280) {
        this.photo1280 = photo1280;
    }

    public String getFirstFrame320() {
        return firstFrame320;
    }

    public void setFirstFrame320(String firstFrame320) {
        this.firstFrame320 = firstFrame320;
    }

    public String getFirstFrame160() {
        return firstFrame160;
    }

    public void setFirstFrame160(String firstFrame160) {
        this.firstFrame160 = firstFrame160;
    }

    public String getFirstFrame4096() {
        return firstFrame4096;
    }

    public void setFirstFrame4096(String firstFrame4096) {
        this.firstFrame4096 = firstFrame4096;
    }

    public String getFirstFrame130() {
        return firstFrame130;
    }

    public void setFirstFrame130(String firstFrame130) {
        this.firstFrame130 = firstFrame130;
    }

    public String getFirstFrame720() {
        return firstFrame720;
    }

    public void setFirstFrame720(String firstFrame720) {
        this.firstFrame720 = firstFrame720;
    }

    public String getFirstFrame1024() {
        return firstFrame1024;
    }

    public void setFirstFrame1024(String firstFrame1024) {
        this.firstFrame1024 = firstFrame1024;
    }

    public String getFirstFrame1280() {
        return firstFrame1280;
    }

    public void setFirstFrame1280(String firstFrame1280) {
        this.firstFrame1280 = firstFrame1280;
    }

    public String getFirstFrame800() {
        return firstFrame800;
    }

    public void setFirstFrame800(String firstFrame800) {
        this.firstFrame800 = firstFrame800;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public Integer getRepeat() {
        return repeat;
    }

    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
    }

    public Integer getCanAdd() {
        return canAdd;
    }

    public void setCanAdd(Integer canAdd) {
        this.canAdd = canAdd;
    }

    public String getTrackCode() {
        return trackCode;
    }

    public void setTrackCode(String trackCode) {
        this.trackCode = trackCode;
    }

}
