package com.agvillamizar.booksalesstream.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookSold {

    private String genre;
    private long value;
}
