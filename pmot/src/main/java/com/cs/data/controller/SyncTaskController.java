package com.cs.data.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.lingala.zip4j.exception.ZipException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.cs.common.utils.DateUtils;
import com.cs.common.utils.RedisUtil;
import com.cs.data.entity.Export;
import com.cs.data.service.ConfigureService;
import com.cs.data.util.CompressUtil;
import com.cs.data.util.JDBCExcel;

/**
 *
 */
@Controller
@RequestMapping(value = "/backend/data/sync")
public class SyncTaskController {

	@Autowired
	private ConfigureService configureService;
	
	private String dataFolderPath = "C:\\DATA\\";
	
	private String password = "!@#$%^&*Cscx_86000!@#$%^&*";
	
	private String IMPORT = "import_";
	
	private String EXPORT = "export_";

	@RequestMapping(value = "/import_export")
	public String importExportUI(HttpServletRequest request) throws Exception {
		return "backend/data/import_export";
	}
	
	@RequestMapping(value = "/import_ui")
	public String importUI(HttpServletRequest request) throws Exception {
		return "backend/data/import";
	}

	/**
	 * 导入
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public String importFile(HttpServletRequest request, Model model) {

		String exportMark = RedisUtil.getValue(IMPORT);
		if(exportMark==null){
			RedisUtil.setValue(IMPORT,DateUtils.getDateTime(),60);
		}else{
			model.addAttribute("message", "工作人员在1分钟内已导入过数据，请查看数据是否已经导入专网。");
			return "backend/data/import_export";
		}
		
		// ZIP包名
		String zipName = new Date().getTime() + ".zip";
		// ZIP包路径
		String zipPath = dataFolderPath + "\\" + zipName;
		// EXCEL文件夹路径
		String excelFolderPath = dataFolderPath + "\\" + new Date().getTime();
		
		// 上传文件
		try {
			if (!upload(request, zipPath)) {
				model.addAttribute("message",
						"页面form中没有enctype=\"multipart/form-data\"");
				return "backend/data/import_export";
			}
		} catch (IOException e1) {
			model.addAttribute("message", "上传失败");
			return "backend/data/import_export";
		}
		// 解压ZIP到EXCEL文件夹路径
		try {
			CompressUtil.unzip(zipPath, excelFolderPath,password);
		} catch (ZipException e) {
			e.printStackTrace();
			model.addAttribute("message", "压缩文件不合法,可能被损坏");
			return "backend/data/import_export";
		}
		
		Map<String, Map<String, String>> errorMap = new HashMap<String, Map<String,String>>();
		
		//获取EXCEL文件并导入数据库
		File file = new File(excelFolderPath);
		File[] s = file.listFiles();
		for (int i = 0; i < s.length; i++) {
			String excelName = s[i].getName();
			if(excelName.indexOf(".xls")!=-1){
				String excelPath = excelFolderPath+"\\"+excelName;
				String tableName = excelName.split("\\.")[0];
				try {
					Map<String, String> map = JDBCExcel.batchInsert(excelPath, tableName);
					if(map.size()>0){
						errorMap.put(tableName,JDBCExcel.batchInsert(excelPath, tableName));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//删除临时文件
		deleteDirectory(new File(zipPath));
		deleteDirectory(new File(excelFolderPath));
		System.out.println("输出信息:"+errorMap);
		if(errorMap.size()>0){
			model.addAttribute("errorMap", errorMap);
		}
		model.addAttribute("message", "导入完成");
		return "backend/data/import_export";
	}

	/**
	 * 导出
	 * @param request
	 * @param response
	 * @param startDate
	 * @param endDate
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/export",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> exportFile(HttpServletRequest request,
			HttpServletResponse response, String beginDate, String endDate) {

		
		String exportMark = RedisUtil.getValue(EXPORT);
		if(exportMark==null){
			RedisUtil.setValue(EXPORT,DateUtils.getDateTime(),60);
		}else{
			return buildResult("-1", "在1分钟内已导出数据，请稍等片刻后，再查看数据是否已经导入外网。", "");
		}
		
		// 处理时间
		if (beginDate == null||endDate==null) {
			if(beginDate == null&&endDate==null){
				beginDate = DateUtils.getDate();
				endDate = DateUtils.getDate();
			}else{
				return buildResult("-1", "请选择导出时间", "");
			}
		}
		
		beginDate += " 00:00:00";
		endDate += " 23:59:59";

		// ZIP包名
		String zipName = new Date().getTime() + ".zip";
		// ZIP包路径
		String zipPath = dataFolderPath +zipName;
		// excel文件夹路径
		String excelFolderPath = dataFolderPath + new Date().getTime();
		
		//获取配置的同步表
		List<Export> exportList = new ArrayList<Export>();
		try {
			exportList = configureService.findEnable();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		for (int i = 0; i < exportList.size(); i++) {
			try {
				String tableName = exportList.get(i).getTableName();
				String excelPath = excelFolderPath+ "\\" + tableName +".xls";
				String param = exportList.get(i).getField();
				JDBCExcel.exportBigDataExcel(excelPath, tableName, beginDate,endDate,param);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		File excelFile = new File(excelFolderPath);
		if (!excelFile.exists()) {
			return buildResult("-1", "在该时间段内，没有数据。", "");
		}
		
		// 压缩临时文件到ZIP
		CompressUtil.zip(excelFolderPath, zipPath,password);
		
		deleteDirectory(new File(excelFolderPath));
		
		return buildResult("1", "生成zip文件成功", zipName);
	}

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 * @param zipName
	 * @throws IOException
	 */
	@RequestMapping(value = "/download",method=RequestMethod.POST)
	public void download(HttpServletRequest request,
			HttpServletResponse response, String name) throws IOException {

		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		// 获取下载文件露肩
		String downLoadPath = dataFolderPath + name;
		downLoadPath = downLoadPath.replace("\\", "/");
		// 获取文件的长度
		long fileLength = new File(downLoadPath).length();

		// 设置文件输出类型
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String("data.zip".getBytes("utf-8"), "ISO8859-1"));
		// 设置输出长度
		response.setHeader("Content-Length", String.valueOf(fileLength));
		// 获取输入流
		bis = new BufferedInputStream(new FileInputStream(downLoadPath));
		// 输出流
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		// 关闭流
		bis.close();
		bos.close();
		
		deleteDirectory(new File(downLoadPath));
	}
	
	
	// 上传文件
	private boolean upload(HttpServletRequest request, String zipPath) throws IOException{
		// 获取系统当前时间戳
		long startTime = System.currentTimeMillis();
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next()
						.toString());
				if (file != null) {
					checkPath(zipPath);
					// 上传
					file.transferTo(new File(zipPath));
				}
			}
		} else {
			return false;
		}
		long endTime = System.currentTimeMillis();
		System.out.println("导入zip包时间：" + String.valueOf(endTime - startTime)
				+ "ms");
		return true;
	}
	
	
	// 检查路径是否存在，不存在创建路径
	private void checkPath(String path) throws IOException {
		File tmpFile = new File(path);
		if (!tmpFile.exists()) {
			tmpFile.getParentFile().mkdirs();
			tmpFile.createNewFile();
		}
	}

	// 生成AJAX返回 MAP
	private Map<String, String> buildResult(String code, String message,
			String name) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("code", code);
		map.put("message", message);
		map.put("name", name);
		return map;
	}
	
	private  void deleteDirectory(File file) {  
        if (file.isFile()) {// 表示该文件不是文件夹  
            file.delete();  
        } else {  
            // 首先得到当前的路径  
            String[] childFilePaths = file.list();  
            for (String childFilePath : childFilePaths) {
                File childFile = new File(file.getAbsolutePath() + "/" + childFilePath);  
                deleteDirectory(childFile);  
            }  
            file.delete();  
        }  
    } 

}
