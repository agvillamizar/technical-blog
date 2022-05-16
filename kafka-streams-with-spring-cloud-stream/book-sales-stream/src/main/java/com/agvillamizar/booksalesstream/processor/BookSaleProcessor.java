package com.agvillamizar.booksalesstream.processor;

import com.agvillamizar.booksalesstream.model.BookSold;
import com.agvillamizar.booksalesstream.model.GenreCount;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Slf4j
@Component
public class BookSaleProcessor implements Function<KStream<Void, BookSold>, KStream<String, GenreCount>> {

    @Override
    public KStream<String, GenreCount> apply(KStream<Void, BookSold> bookSoldStream) {
        return bookSoldStream
                // Log for debugging
                .peek((unused, bookSold) -> log.info("Processing book sold {}", bookSold))
                // Group the stream by book genre to ensure the key of the record is the genre.
                .groupBy((unused, value) -> value.getGenre(),
                        Grouped.with(Serdes.String(), new JsonSerde<>(BookSold.class)))
                // Count the occurrences of each book genre (record key).
                .count()
                // Produces a GenreCount record with the genre and the genre count
                .mapValues(GenreCount::new)
                // Convert the `KTable<String, Long>` into a `KStream<String, Long>`.
                .toStream()
                // Log for debugging
                .peek((key, genreCount) -> log.info("Genre sale count updated {}", genreCount));
    }
}
