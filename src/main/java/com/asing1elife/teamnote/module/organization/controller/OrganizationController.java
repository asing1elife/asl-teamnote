package com.asing1elife.teamnote.module.organization.controller;

import com.asing1elife.teamnote.core.controller.BaseController;
import com.asing1elife.teamnote.model.OrganizationModel;
import com.asing1elife.teamnote.module.organization.service.OrganizationServiceImpl;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiresAuthentication
@RequestMapping("/api/organizations")
public class OrganizationController extends BaseController<OrganizationModel, OrganizationServiceImpl> {

}
