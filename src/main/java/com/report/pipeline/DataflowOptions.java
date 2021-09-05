package com.report.pipeline;

import org.apache.beam.sdk.options.*;

public interface DataflowOptions extends PipelineOptions {
//    @Description("BigQuery Table ID")
//    @Default.String("bigquery-public-data.samples.github_nested")
//    ValueProvider<String> getTableId();

//    @Description("Query to run")
//    ValueProvider<String> getQuery();
//
//    void setQuery(ValueProvider<String> value);

    @Description("Table to export. Format: `[project_id]:[dataset_id].[table_id]` or `[dataset_id].[table_id]`")
    ValueProvider<String> getTable();

    void setTable(ValueProvider<String> value);

    @Description("Path of the file to write to")
    @Validation.Required
    ValueProvider<String> getOutput();

    void setOutput(ValueProvider<String> value);

//    @Description("Query condition value")
//    ValueProvider<String> getQueryParam();
}
