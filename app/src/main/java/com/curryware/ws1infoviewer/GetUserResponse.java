package com.curryware.ws1infoviewer;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserResponse {

    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;
    @SerializedName("itemsPerPage")
    @Expose
    private Integer itemsPerPage;
    @SerializedName("startIndex")
    @Expose
    private Integer startIndex;
    @SerializedName("schemas")
    @Expose
    private List<String> schemas = null;
    @SerializedName("Resources")
    @Expose
    private List<UserResource> resources = null;

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public List<String> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<String> schemas) {
        this.schemas = schemas;
    }

    public List<UserResource> getResources() {
        return resources;
    }

    public void setResources(List<UserResource> resources) {
        this.resources = resources;
    }

}