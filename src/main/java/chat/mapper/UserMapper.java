package chat.mapper;

import chat.dto.UserDto;
import chat.model.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    @Named("userToUserDto")
    UserDto userToUserDto(User entity);
    User userDtoToUser(UserDto dto);
    @IterableMapping(qualifiedByName = "userToUserDto")
    List<UserDto> allToDto(List<User> users);

}
