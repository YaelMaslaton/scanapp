package com.mend.scanapp.mapper;

import com.mend.scanapp.DTO.ScanDTO;
import com.mend.scanapp.model.Scan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ScanAppMapper {
    @Mapping(target = "appUser.userId", source = "userId")
    @Mapping(target = "commit.commitId", source = "commitId")
    Scan scanDTOToScan(ScanDTO scanDTO);
}
