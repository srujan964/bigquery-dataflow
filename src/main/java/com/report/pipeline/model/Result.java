package com.report.pipeline.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.io.Serializable;
import java.util.ArrayList;

@DefaultSchema(JavaBeanSchema.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {
    private Repository repository;
    private String createdAt;
    @JsonProperty("public")
    private Boolean isPublic;
    private String actor;

    public String unwindAndStringify() {
        ArrayList<String> builder = new ArrayList<>();
        builder.add(repository.getUrl());
        builder.add(repository.getHasDownloads().toString());
        builder.add(repository.getCreatedAt());
        builder.add(repository.getHasIssues().toString());
        builder.add(repository.getDescription());
        builder.add(isPublic.toString());
        builder.add(actor);
        return String.join(",", builder);
    }
}
