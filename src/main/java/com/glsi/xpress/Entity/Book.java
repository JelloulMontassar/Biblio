package com.glsi.xpress.Entity;

import com.glsi.xpress.Entity.Enum.BookCategory;
import com.glsi.xpress.Entity.Enum.BookStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String auther;
    private LocalDateTime publishmentDate;
    private Long isbn;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    @Enumerated(EnumType.STRING)
    private BookCategory category;
    @Lob
    private byte[] BookCover;
}
