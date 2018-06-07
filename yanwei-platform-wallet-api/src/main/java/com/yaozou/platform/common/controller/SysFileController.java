package com.yaozou.platform.common.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yaozou.platform.common.config.BootMemebersConfig;
import com.yaozou.platform.common.domain.SysFileDO;
import com.yaozou.platform.common.service.SysFileService;
import com.yaozou.platform.common.utils.FileType;
import com.yaozou.platform.common.utils.FileUtil;
import com.yaozou.platform.common.utils.PageUtils;
import com.yaozou.platform.common.utils.Query;
import com.yaozou.platform.common.utils.R;


/**
 * 文件上传
 * @author luojianhong
 * @version $Id: SysFileController.java, v 0.1 2017年10月11日 上午10:52:09 luojianhong Exp $
 */
@Controller
@RequestMapping("/common/sysFile")
public class SysFileController extends BaseController {

	@Autowired
	private SysFileService sysFileService;

	@Autowired
	private BootMemebersConfig bootMemebersConfig;

	@GetMapping()
	@RequiresPermissions("common:sysFile:sysFile")
	String SysFile(Model model) {
		return "common/file/file";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("common:sysFile:sysFile")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<SysFileDO> sysFileList = sysFileService.list(query);
		int total = sysFileService.count(query);
		PageUtils pageUtils = new PageUtils(sysFileList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	String add() {
		return "common/sysFile/add";
	}

	@GetMapping("/edit")
	String edit(Long id, Model model) {
		SysFileDO sysFile = sysFileService.get(id);
		model.addAttribute("sysFile", sysFile);
		return "common/sysFile/edit";
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("common:info")
	public R info(@PathVariable("id") Long id) {
		SysFileDO sysFile = sysFileService.get(id);
		return R.ok().put("sysFile", sysFile);
	}
	
	
	/**
     * 信息
     */
	@ResponseBody
    @RequestMapping("/infosrc/")
    public R infosrc(String src) {
        SysFileDO sysFile = sysFileService.getUrl(src);
        Map<String, Object> map=new  HashMap<String, Object>();
        map.put("id", sysFile.getId());
        map.put("src", sysFile.getUrl());
        return R.ok(map);
    }

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("common:save")
	public R save(SysFileDO sysFile) {
		if (sysFileService.save(sysFile) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("common:update")
	public R update(@RequestBody SysFileDO sysFile) {
		sysFileService.update(sysFile);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	public R remove(Long id, HttpServletRequest request) {
		 
		String fileName = bootMemebersConfig.getUploadPath() + sysFileService.get(id).getUrl().replace("/files/", "");
		if (sysFileService.remove(id) > 0) {
			boolean b = FileUtil.deleteFile(fileName);
			if (!b) {
				return R.error("数据库记录删除成功，文件删除失败");
			}
			return R.ok();
		} else {
			return R.error();
		}
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("common:remove")
	public R remove(@RequestParam("ids[]") Long[] ids) {
		sysFileService.batchRemove(ids);
		return R.ok();
	}

	@ResponseBody
	@PostMapping("/upload")
	public R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String fileName = file.getOriginalFilename();
		fileName = FileUtil.RenameToUUID(fileName);
		String strPath="/files/" + fileName;
		SysFileDO sysFile = new SysFileDO(FileType.fileType(fileName),strPath, new Date());
		try {
            FileUtil.uploadFile(file.getBytes(), 
                bootMemebersConfig.getUploadPath(), 
                fileName);
            
		} catch (Exception e) {
			return R.error();
		}
		if (sysFileService.save(sysFile) > 0) {
		     Map<String, Object> map=new  HashMap<String, Object>();
		     SysFileDO fileUrl=sysFileService.getUrl(strPath);
		     if(fileUrl!=null)
		      map.put("id", fileUrl.getId());
		     map.put("src", strPath);
			return R.ok(map);
		}
		return R.error();
	}
}
