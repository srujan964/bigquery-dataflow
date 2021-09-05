# bigquery-dataflow

### Stage Dataflow template in GCS

```
gradle clean execute \
     -Dexec.mainClass=com.example.myclass \
     -Dexec.args="--runner=DataflowRunner \
                  --project=PROJECT_ID \
                  --stagingLocation=gs://BUCKET_NAME/staging \
                  --templateLocation=gs://BUCKET_NAME/templates/TEMPLATE_NAME
                  --region=REGION"
```
