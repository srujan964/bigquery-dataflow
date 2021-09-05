package com.report.pipeline.model;

import org.apache.beam.sdk.testing.TestPipeline;
import org.junit.Rule;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class ResultTest {

    @Rule
    public final transient TestPipeline p = TestPipeline.create();

    @Test
    void shouldUnwindAndStringify() {
        Repository repository = Repository.builder()
                .url("https://www.example.com")
                .hasDownloads(true)
                .createdAt("2011/09/30 11:47:48 -0700")
                .hasIssues(true)
                .description("")
                .build();
        Result result = Result.builder()
                .repository(repository)
                .isPublic(true)
                .actor("example")
                .build();
        String expected = "https://www.example.com,true,2011/09/30 11:47:48 -0700,true,,true,example";

        String actual = result.unwindAndStringify();

        assertThat(actual, is(equalTo(expected)));
    }
}