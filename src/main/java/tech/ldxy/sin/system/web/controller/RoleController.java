package tech.ldxy.sin.system.web.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.ldxy.sin.core.bean.ApiResponse;
import tech.ldxy.sin.system.service.IRoleService;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@RestController
@RequestMapping("/role")
@Validated
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @RequestMapping("/save")
    public ApiResponse save(String id,
                            @RequestParam @Length(min = 1, max = 30) String code,
                            @RequestParam @Length(min = 1, max = 30) String name) {
        roleService.saveOrUpdate(id, code, name);
        return ApiResponse.successOfMessage("角色保存成功。");
    }

}
