package com.yaozou.platform.common.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yaozou.platform.common.domain.SysDictDO;
import com.yaozou.platform.common.service.SysDictService;
import com.yaozou.platform.common.utils.PageUtils;
import com.yaozou.platform.common.utils.Query;
import com.yaozou.platform.common.utils.R;

/**
 * 字典表
 * @author luojianhong
 * @version $Id: SysDictController.java, v 0.1 2017年10月11日 上午10:54:03 luojianhong Exp $
 */
@Controller
@RequestMapping("/common/sysDict")
public class SysDictController extends BaseController {
    @Autowired
    private SysDictService sysDictService;

    @GetMapping()
    @RequiresPermissions("common:sysDict:sysDict")
    public String SysDict() {
        return "common/sysDict/sysDict";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("common:sysDict:sysDict")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<SysDictDO> sysDictList = sysDictService.list(query);
        int total = sysDictService.count(query);
        PageUtils pageUtils = new PageUtils(sysDictList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("common:sysDict:add")
    public String add() {
        return "common/sysDict/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("common:sysDict:edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        SysDictDO sysDict = sysDictService.get(id);
        model.addAttribute("sysDict", sysDict);
        return "common/sysDict/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("common:sysDict:add")
    public R save(SysDictDO sysDict) {
        if (sysDictService.save(sysDict) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("common:sysDict:edit")
    public R update(SysDictDO sysDict) {
        sysDictService.update(sysDict);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("common:sysDict:remove")
    public R remove(Long id) {
        if (sysDictService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("common:sysDict:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {

        sysDictService.batchRemove(ids);
        return R.ok();
    }

    @GetMapping("/type")
    @ResponseBody
    public List<SysDictDO> listType() {
        return sysDictService.listType();
    };

    //类别已经指定增加
    @GetMapping("/add/{type}/{description}")
    @RequiresPermissions("common:sysDict:add")
    public String addD(Model model, @PathVariable("type") String type,
                       @PathVariable("description") String description) {
        model.addAttribute("type", type);
        model.addAttribute("description", description);
        return "common/sysDict/add";
    }

}
