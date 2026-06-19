package com.supriya.LMS.mapper;

import com.supriya.LMS.Entity.Book;
import com.supriya.LMS.dto.BookDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto toDto(Book book);

    Book toEntity(BookDto dto);
}