package SimbirSoftProject.mapper;

import SimbirSoftProject.controller.dto.UserDto;
import SimbirSoftProject.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "login", source = "login"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "roles", source = "roles"),
            @Mapping(target = "firstName", source = "firstName"),
            @Mapping(target = "lastName", source = "lastName"),
            @Mapping(target = "is_user_online", source = "isUserOnline"),
            @Mapping(target = "is_blocked", source = "isBlocked"),
            @Mapping(target = "block_date", source = "blockDate"),
            @Mapping(target = "blocking_duration", source = "blockingDuration")
    })
    UserDto userToUserDto(User entity);
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "login", source = "login"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "roles", source = "roles"),
            @Mapping(target = "firstName", source = "firstName"),
            @Mapping(target = "lastName", source = "lastName"),
            @Mapping(target = "isUserOnline", source = "is_user_online"),
            @Mapping(target = "isBlocked", source = "is_blocked"),
            @Mapping(target = "blockDate", source = "block_date"),
            @Mapping(target = "blockingDuration", source = "blocking_duration")
    })
    User userDtoToUser(UserDto dto);
    List<UserDto> allToDto(List<User> users);

}
