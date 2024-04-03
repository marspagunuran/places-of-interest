package com.pccw.platform.service.model;

import com.pccw.platform.service.persist.AbstractModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** @author dxiong */
@Data
@EqualsAndHashCode(callSuper = true)
public class SampleDataModel extends AbstractModel {
  private String companyId;
  private String name;
  private String description;
}
