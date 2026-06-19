package com.supriya.LMS.mapper;

import com.supriya.LMS.Entity.BookIssue;
import com.supriya.LMS.dto.BookIssueDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookIssueMapper {


    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "member.id", target = "memberId")
    BookIssueDto toDto(BookIssue bookIssue);
}