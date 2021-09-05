package com.report.pipeline;

import com.report.pipeline.model.Repository;
import com.report.pipeline.model.Result;
import org.apache.avro.generic.GenericRecord;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.bigquery.SchemaAndRecord;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.schemas.transforms.Convert;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SerializableFunction;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptors;

import java.util.TimeZone;

import static java.time.ZoneOffset.UTC;

public class ReportPipeline {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone(UTC));

        DataflowOptions dataflowOptions = PipelineOptionsFactory.fromArgs(args)
                .withValidation()
                .as(DataflowOptions.class);

        Pipeline p = Pipeline.create(dataflowOptions);

        String header = "Url,HasDownloads,RepositoryCreatedAt,HasIssues,Description,Public,Actor";

        PCollection<Result> resultsFromBigQuery = p.apply("ReadFromBigQuery", BigQueryIO
//                .read((SerializableFunction<SchemaAndRecord, Result>) input -> {
//                    GenericRecord record = input.getRecord();
//                    return new Result(
//                            (Repository) record.get("repository"),
//                            record.get("created_at").toString(),
//                            (Boolean) record.get("public"),
//                            record.get("actor").toString()
//                    );
//                })
//                .withCoder(SerializableCoder.of(Result.class))
                        .readTableRowsWithSchema()
                        .from(dataflowOptions.getTable())
                        .withMethod(BigQueryIO.TypedRead.Method.DIRECT_READ)
                        .withoutValidation()
                        .withTemplateCompatibility())
                .apply(Convert.to(Result.class));

        PCollection<String> stringifiedRows = resultsFromBigQuery.apply(MapElements
                .into(TypeDescriptors.strings())
                .via(Result::unwindAndStringify));

        Pipeline pipeline = stringifiedRows.apply(TextIO.write()
                        .to(dataflowOptions.getOutput())
                        .withoutSharding()
                        .withSuffix(".csv")
                        .withHeader(header))
                .getPipeline();

        pipeline.run().waitUntilFinish();
    }

}
