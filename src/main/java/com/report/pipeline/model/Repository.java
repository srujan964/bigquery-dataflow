package com.report.pipeline.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.io.Serializable;

@DefaultSchema(JavaBeanSchema.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Repository implements Serializable {
    private String url;
    private Boolean hasDownloads;
    private String createdAt;
    private Boolean hasIssues;
    private String description;
    private Integer forks;
    private Boolean fork;
    private Boolean hasWiki;
    private String homepage;
    private String integrateBranch;
    private String masterBranch;
    private Integer size;
    @JsonProperty("private")
    private Boolean priv;
    private String name;
    private String organization;
    private String owner;
    private Integer openIssues;
    private Integer watchers;
    private String pushedAt;
    private String language;
}
