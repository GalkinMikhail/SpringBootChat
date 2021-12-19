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
    UserDto userToUserDto(User entity);
    User userDtoToUser(UserDto dto);
    List<UserDto> allToDto(List<User> users);

}
