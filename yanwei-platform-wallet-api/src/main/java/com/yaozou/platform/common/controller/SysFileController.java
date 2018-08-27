package com.yaozou.platform.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yaozou.platform.common.config.BootMemebersConfig;
import com.yaozou.platform.common.utils.FileUtil;
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
	private BootMemebersConfig bootMemebersConfig;

	@ResponseBody
	@PostMapping("/upload")
	public R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String fileName = file.getOriginalFilename();
		fileName = FileUtil.RenameToUUID(fileName);
		String strPath="/files/" + fileName;
		try {
            FileUtil.uploadFile(file.getBytes(), 
                bootMemebersConfig.getUploadPath(), 
                fileName);
            
		} catch (Exception e) {
			return R.error();
		}
		return R.error();
	}
}
