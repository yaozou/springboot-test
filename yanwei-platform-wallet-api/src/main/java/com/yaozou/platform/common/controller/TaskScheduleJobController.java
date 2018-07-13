package com.yaozou.platform.common.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yaozou.platform.common.domain.TaskScheduleJobDO;
import com.yaozou.platform.common.service.TaskScheduleJobService;
import com.yaozou.platform.common.utils.PageUtils;
import com.yaozou.platform.common.utils.Query;
import com.yaozou.platform.common.utils.R;

/**
 * 定时任务管理类
 * @author luojianhong
 * @version $Id: TaskScheduleJobController.java, v 0.1 2017年10月11日 上午10:49:46 luojianhong Exp $
 */
@Controller
@RequestMapping("/common/taskScheduleJob")
public class TaskScheduleJobController extends BaseController {
    @Autowired
    private TaskScheduleJobService taskScheduleJobService;

    @GetMapping()
    String TaskScheduleJob() {
        return "common/taskScheduleJob/taskScheduleJob";
    }

    @ResponseBody
    @GetMapping("/list")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        Query query = new Query(params);
        List<TaskScheduleJobDO> taskScheduleJobList = taskScheduleJobService.list(query);
        int total = taskScheduleJobService.count(query);
        PageUtils pageUtils = new PageUtils(taskScheduleJobList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    String add() {
        return "common/taskScheduleJob/add";
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Long id, Model model) {
        TaskScheduleJobDO taskScheduleJob = taskScheduleJobService.get(id);
        model.addAttribute("TaskScheduleJob", taskScheduleJob);
        return "common/taskScheduleJob/edit";
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        TaskScheduleJobDO taskScheduleJob = taskScheduleJobService.get(id);
        return R.ok().put("taskScheduleJob", taskScheduleJob);
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    public R save(TaskScheduleJobDO taskScheduleJob) {

        if (taskScheduleJobService.save(taskScheduleJob) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TaskScheduleJobDO taskScheduleJob) {

        taskScheduleJobService.update(taskScheduleJob);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Long id) {

        if (taskScheduleJobService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") Long[] ids) {

        taskScheduleJobService.batchRemove(ids);

        return R.ok();
    }

    @PostMapping(value = "/changeJobStatus")
    @ResponseBody
    public R changeJobStatus(Long id, String cmd) {
        String label = "停止";
        if (cmd.equals("start")) {
            label = "启动";
        } else {
            label = "停止";
        }
        try {
            taskScheduleJobService.changeStatus(id, cmd);
            return R.ok("任务" + label + "成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok("任务" + label + "失败");
    }

}
