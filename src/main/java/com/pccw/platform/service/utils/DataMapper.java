/** */
package com.pccw.platform.service.utils;

import com.pccw.platform.service.entity.SampleEntity;
import com.pccw.platform.service.model.SampleDataModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** @author dxiong */
@Mapper
public interface DataMapper {

  @Mapping(source = "entityId", target = "id")
  SampleDataModel map(SampleEntity entity);
}
