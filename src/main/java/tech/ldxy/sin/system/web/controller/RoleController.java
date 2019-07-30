package tech.ldxy.sin.system.web.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.ldxy.sin.core.bean.ApiResponse;
import tech.ldxy.sin.system.model.entity.Role;
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

    /**
     * 新增角色
     */
    @PostMapping("/add")
    public ApiResponse add(@RequestParam @Length(min = 1, max = 30) String code,
                           @RequestParam @Length(min = 1, max = 30) String name) {
        Role role = new Role(code, name);
        roleService.add(role);
        return ApiResponse.successOfMessage("新增角色成功。");
    }

    /**
     * 修改角色
     */
    @PostMapping("/edit")
    public ApiResponse edit(@RequestParam Long id,
                            @RequestParam @Length(min = 1, max = 30) String code,
                            @RequestParam @Length(min = 1, max = 30) String name) {
        Role role = new Role(id, code, name);
        roleService.edit(role);
        return ApiResponse.successOfMessage("修改角色成功。");
    }

}
