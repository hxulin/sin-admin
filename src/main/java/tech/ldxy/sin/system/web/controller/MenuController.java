package tech.ldxy.sin.system.web.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.ldxy.sin.core.bean.ApiResponse;
import tech.ldxy.sin.system.model.entity.Menu;
import tech.ldxy.sin.system.service.IMenuService;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@RestController
@RequestMapping(value = "/menu")
@Validated
public class MenuController {

    @Autowired
    private IMenuService menuService;

    /**
     * 新增菜单
     */
    @PostMapping("/add")
    public ApiResponse add(@RequestParam Long parentId,
                           @RequestParam @Length(min = 1, max = 20) String menuName,
                           @RequestParam @Length(min = 1, max = 200) String path,
                           @RequestParam Integer menuType,
                           @Length(max = 100) String icon,
                           @Length(max = 20) String btnClass,
                           Long sort) {
        menuService.save(new Menu(parentId, menuName, path, menuType, icon, btnClass, sort));
        return ApiResponse.successOfMessage("新增菜单成功。");
    }
}
