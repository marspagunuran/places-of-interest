/** */
package com.pccw.platform.service.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** @author dxiong */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminCreateSampleRequest extends CreateSampleRequest {

  @NotBlank private String companyId;
}
