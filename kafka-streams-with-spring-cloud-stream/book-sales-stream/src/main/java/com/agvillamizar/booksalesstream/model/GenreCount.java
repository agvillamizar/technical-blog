package com.agvillamizar.booksalesstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreCount {

    private String genre;
    private long count;
}
