package com.viwcy.basemodel.convert;

import com.viwcy.basecommon.entity.User;
import com.viwcy.basemodel.dto.SimpleUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Mapper(componentModel = "Spring")
public interface UserConvert {

    @Mappings({
            @Mapping(source = "userName", target = "name"),
            @Mapping(source = "headPhoto", target = "headPortrait")
    })
    SimpleUserDTO toSimpleUserDTO(User user);
}
