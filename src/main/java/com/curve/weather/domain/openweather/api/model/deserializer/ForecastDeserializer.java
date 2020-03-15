package com.curve.weather.domain.openweather.api.model.deserializer;

import com.curve.weather.domain.openweather.api.model.Forecast;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.NumericNode;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ForecastDeserializer extends StdDeserializer<Forecast> {

    private static final long serialVersionUID = -1129616522742151566L;

    public ForecastDeserializer() {
        this(null);
    }

    public ForecastDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public Forecast deserialize(final JsonParser jp, final DeserializationContext ctxt)
            throws IOException {
        TreeNode node = jp.getCodec().readTree(jp);

        // date mapping
        final Long epochMillis = ((IntNode) node.get("dt")).longValue();

        // TODO: review if harcoding UTC is appropriate
        final LocalDateTime dt = Instant.ofEpochSecond(epochMillis).atZone(ZoneId.of("UTC")).toLocalDateTime();

        // temperature mapping
        final Double temp = ((NumericNode) node.get("main").get("temp")).doubleValue();

        return new Forecast(dt, temp);
    }
}