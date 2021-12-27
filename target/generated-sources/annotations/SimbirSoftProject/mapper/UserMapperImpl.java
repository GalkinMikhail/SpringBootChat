package SimbirSoftProject.mapper;

import SimbirSoftProject.dto.UserDto;
import SimbirSoftProject.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-27T14:34:30+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto userToUserDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( entity.getId() );
        userDto.setLogin( entity.getLogin() );
        userDto.setPassword( entity.getPassword() );
        userDto.setFirstName( entity.getFirstName() );
        userDto.setLastName( entity.getLastName() );
        userDto.setUserOnline( entity.isUserOnline() );
        userDto.setBlocked( entity.isBlocked() );
        userDto.setBlockDate( entity.getBlockDate() );
        userDto.setBlockingDuration( entity.getBlockingDuration() );

        return userDto;
    }

    @Override
    public User userDtoToUser(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setId( dto.getId() );
        user.setLogin( dto.getLogin() );
        user.setPassword( dto.getPassword() );
        user.setFirstName( dto.getFirstName() );
        user.setLastName( dto.getLastName() );
        user.setUserOnline( dto.isUserOnline() );
        user.setBlocked( dto.isBlocked() );
        user.setBlockDate( dto.getBlockDate() );
        user.setBlockingDuration( dto.getBlockingDuration() );

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
