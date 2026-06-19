package com.supriya.LMS.mapper;

import com.supriya.LMS.Entity.Member;
import com.supriya.LMS.dto.MemberDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberDto toDto(Member member);

    Member toEntity(MemberDto dto);
}