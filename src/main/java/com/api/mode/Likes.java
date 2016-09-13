package com.api.mode;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by wanganbang on 8/2/16.
 */
@Document(collection = "Likes")
public class Likes implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonProperty
    @ApiModelProperty(hidden = true)
    private String id;
    @ApiModelProperty(hidden = true)
    private String userid;
    private String eventid;
    private String type;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Likes(@JsonProperty("uuid") String userid, @JsonProperty("eventid") String eventid, @JsonProperty(value = "type") String type) {
        this.userid = userid;
        this.eventid = eventid;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
