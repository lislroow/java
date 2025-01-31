#### doc site

- https://opentelemetry.io/docs/languages/java/configuration/

#### otel.exporter.otlp.endpoint

- The endpoint to send all OTLP traces, metrics, and logs to. Often the address of an OpenTelemetry Collector. Must be a URL with a scheme of either http or https based on the use of TLS.
- `default` http://localhost:4317 when protocol is grpc, and http://localhost:4318 when protocol is http/protobuf.

#### otel.exporter.otlp.timeout

- The maximum waiting time, in milliseconds, allowed to send each OTLP trace, metric, and log batch.
- `default` 10000

#### otel.exporter.otlp.metrics.temporality.preference

- The preferred output aggregation temporality. Options include DELTA, LOWMEMORY, and CUMULATIVE. If CUMULATIVE, all instruments will have cumulative temporality. If DELTA, counter (sync and async) and histograms will be delta, up down counters (sync and async) will be cumulative. If LOWMEMORY, sync counter and histograms will be delta, async counter and up down counters (sync and async) will be cumulative.

