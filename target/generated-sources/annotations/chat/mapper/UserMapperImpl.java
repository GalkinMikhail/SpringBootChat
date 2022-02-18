package chat.mapper;

import chat.dto.UserDto;
import chat.model.Role;
import chat.model.User;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-18T22:11:47+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.2 (Azul Systems, Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto userToUserDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setLogin( entity.getLogin() );
        userDto.setPassword( entity.getPassword() );
        userDto.setFirstName( entity.getFirstName() );
        userDto.setLastName( entity.getLastName() );
        Set<Role> set = entity.getRoles();
        if ( set != null ) {
            userDto.setRoles( new HashSet<Role>( set ) );
        }
        userDto.setUserOnline( entity.isUserOnline() );
        userDto.setBlocked( entity.isBlocked() );
        userDto.setBlockDate( entity.getBlockDate() );
        userDto.setBlockingDurationInMinutes( entity.getBlockingDurationInMinutes() );

        return userDto;
    }

    @Override
    public User userDtoToUser(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setLogin( dto.getLogin() );
        user.setPassword( dto.getPassword() );
        Set<Role> set = dto.getRoles();
        if ( set != null ) {
            user.setRoles( new HashSet<Role>( set ) );
        }
        user.setFirstName( dto.getFirstName() );
        user.setLastName( dto.getLastName() );
        user.setUserOnline( dto.isUserOnline() );
        user.setBlocked( dto.isBlocked() );
        user.setBlockDate( dto.getBlockDate() );
        user.setBlockingDurationInMinutes( dto.getBlockingDurationInMinutes() );

        return user;
    }

    @Override
    public List<UserDto> allToDto(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( users.size() );
        for ( User user : users ) {
            list.add( userToUserDto( user ) );
        }

        return list;
    }
}
