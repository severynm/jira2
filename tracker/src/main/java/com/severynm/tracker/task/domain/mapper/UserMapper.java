package com.severynm.tracker.task.domain.mapper;

import com.severynm.tracker.task.domain.dto.UserDto;
import com.severynm.tracker.task.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "departmentName", source = "department.name")
    UserDto toUserDto(User user);
}
