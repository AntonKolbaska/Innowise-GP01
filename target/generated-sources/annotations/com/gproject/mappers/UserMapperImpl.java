package com.gproject.mappers;

import com.gproject.dto.UserDto;
import com.gproject.entity.User;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-22T17:31:50+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.18 (Amazon.com Inc.)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto userToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setUsername( user.getUsername() );
        userDto.setPassword( user.getPassword() );
        userDto.setEmail( user.getEmail() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );
        userDto.setRole( user.getRole() );
        userDto.setCompany( user.getCompany() );

        return userDto;
    }

    @Override
    public User dtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDto.getId() );
        user.setUsername( userDto.getUsername() );
        user.setPassword( userDto.getPassword() );
        user.setEmail( userDto.getEmail() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );
        user.setRole( userDto.getRole() );
        user.setCompany( userDto.getCompany() );

        return user;
    }

    @Override
    public Collection<UserDto> userToDtoCollection(Collection<User> userCollection) {
        if ( userCollection == null ) {
            return null;
        }

        Collection<UserDto> collection = new ArrayList<UserDto>( userCollection.size() );
        for ( User user : userCollection ) {
            collection.add( userToDto( user ) );
        }

        return collection;
    }

    @Override
    public Collection<User> dtoToUserCollection(Collection<UserDto> userDtoCollection) {
        if ( userDtoCollection == null ) {
            return null;
        }

        Collection<User> collection = new ArrayList<User>( userDtoCollection.size() );
        for ( UserDto userDto : userDtoCollection ) {
            collection.add( dtoToUser( userDto ) );
        }

        return collection;
    }
}
