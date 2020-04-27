package com.nicow.microservicemodel.mapper;

import com.nicow.microservicemodel.dto.ComplainUserDto;
import com.nicow.microservicemodel.entities.ComplainUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ComplainUserMapper {


    ComplainUser toComplainUser(ComplainUserDto complainUserDto);
    ComplainUserDto toComplainUserDto(ComplainUser complainUser);


}

