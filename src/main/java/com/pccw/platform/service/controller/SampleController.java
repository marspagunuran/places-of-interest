package com.pccw.platform.service.controller;

import com.pccw.platform.service.dto.CreateSampleRequest;
import com.pccw.platform.service.dto.UpdateSampleRequest;
import com.pccw.platform.service.model.Paging;
import com.pccw.platform.service.model.SampleDataModel;
import com.pccw.platform.service.service.SampleService;
import com.pccw.platform.service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    value = "${server.rest.base:}/samples/v1",
    produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Customer", description = "Customer Access Only")
@RequiresRoles(
    logical = Logical.OR,
    value = {
      "PARTNER_ADMIN",
      "PARTNER_SUPER_ADMIN",
      "PARTNER_USER",
      "CUSTOMER_ADMIN",
      "CUSTOMER_SUPER_ADMIN",
      "CUSTOMER_USER"
    })
public class SampleController {

  @Autowired private SampleService service;
  @Autowired private UserService rbac;

  @Operation(summary = "Search entities")
  @GetMapping()
  public Paging<SampleDataModel> search(
      @RequestParam(required = false) String companyId,
      @RequestParam(required = false) Boolean deleted,
      @RequestParam(required = false, defaultValue = Paging.PAGE_START_INDEX_STR) int page,
      @RequestParam(required = false, defaultValue = Paging.PAGE_SIZE_DEFAULT_STR) int size) {
    return service.search(rbac.getLoginUser().getCompany(), deleted, Paging.toPageable(page, size));
  }

  @Operation(summary = "Find a entity details")
  @GetMapping(path = "/{id}")
  public SampleDataModel findOne(@PathVariable("id") String id) {
    SampleDataModel data = service.findOne(id);
    service.checkPermission(data.getCompanyId());
    return data;
  }

  @Operation(summary = "Delete a entity")
  @DeleteMapping(path = "/{id}")
  public SampleDataModel delete(@PathVariable("id") String id) {
    this.findOne(id);
    return service.delete(id);
  }

  @Operation(summary = "Update a entity")
  @PatchMapping(path = "/{id}")
  public SampleDataModel update(@PathVariable("id") String id, @Valid UpdateSampleRequest request) {
    this.findOne(id);
    return service.update(id, request);
  }

  @Operation(summary = "create a entity")
  @PostMapping
  public SampleDataModel create(@Valid CreateSampleRequest request) {
    return service.create(rbac.getLoginUser().getCompany(), request);
  }
}
