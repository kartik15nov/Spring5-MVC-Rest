package com.baya.Spring5MVCRest.api.v1.mapper;

import com.baya.Spring5MVCRest.api.v1.model.VendorDTO;
import com.baya.Spring5MVCRest.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper {
    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    @Mapping(target = "vendorUrl", ignore = true)
    VendorDTO vendorToVendorDTO(Vendor vendor);

    @Mapping(target = "id", ignore = true)
    Vendor vendorDTOToVendor(VendorDTO vendorDTO);
}
