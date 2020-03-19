package com.nicow.microservicemodel.mapper;

import com.nicow.microservicemodel.dto.ComplainUserDto;
import com.nicow.microservicemodel.entities.ComplainUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ComplainUserMapper {

    ComplainUserMapper INSTANCE = Mappers.getMapper( ComplainUserMapper.class );

    ComplainUser toComplainUser(ComplainUserDto complainUserDto);
    ComplainUserDto toComplainUserDto(ComplainUser complainUser);


}

