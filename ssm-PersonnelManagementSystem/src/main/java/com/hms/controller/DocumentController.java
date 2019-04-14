package com.hms.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hms.bean.Document;
import com.hms.bean.Msg;
import com.hms.service.DocumentService;

@Controller
public class DocumentController {
	@Autowired
	private DocumentService documentService;

	@RequestMapping("/document/select")
	@ResponseBody
	public Msg getDocument(@RequestParam(value = "id") Integer id) {
		Document document = documentService.getDocument(id);
		return Msg.success().add("document", document);
	}

	@ResponseBody
	@RequestMapping("/documents/select")
	public Msg getDocuments() {
		List<Document> documents = documentService.getAll();
		return Msg.success().add("documents", documents);
	}

	@RequestMapping("/document/delete")
	@ResponseBody
	public Msg deleteDocument(@RequestParam(value = "id") Integer id,@RequestParam(value = "filename") String filename) {
		File file=new File(filename);
        if(file.exists()&&file.isFile()) {
        	documentService.deleteDocument(id);
        	file.delete();
        	return Msg.success();
        }
        else {
        	return Msg.fail();
        }
	}

	@RequestMapping("/documents/delete")
	@ResponseBody
	public Msg deleteDocuments(@RequestParam(value = "idList[]") List<Integer> idList) {
		System.out.println(idList);
		int count =0;
		for(int i=0;i<idList.size();i++) {
			Document document = documentService.getDocument(idList.get(i));
			File file=new File(document.getRealfilename());
	        if(file.exists()&&file.isFile()) {
	        	documentService.deleteDocument(idList.get(i));
	        	file.delete();
	        	count++;
	        }
		}
		if(count==idList.size()) {
			return Msg.success();
		}else {
			return Msg.fail();
		}
	}
	
	@RequestMapping("/document/update")
	@ResponseBody
	public Msg updateDocument(@RequestBody Document document) {
		documentService.updateDocument(document);
		return Msg.success();
	}
	@RequestMapping("/document/upload")
	@ResponseBody
	public Msg upload(HttpServletRequest request,
			@RequestParam(value="files[]",required=false)List<MultipartFile> files
            )throws IOException {
		Integer userId=Integer.valueOf(request.getParameter("userId"));
		Integer empId=Integer.valueOf(request.getParameter("empId"));
		String title=request.getParameter("title");
		String remark=request.getParameter("remark");
		for(MultipartFile multipartFile : files) {
			String fileName = multipartFile.getOriginalFilename();
			System.out.println(fileName);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			int random = new Random().nextInt(10000);
			String realFileName = random+multipartFile.getOriginalFilename();
			String path = "D:\\receiveFile\\"+df.format(new Date());
			FileUtils.copyInputStreamToFile(
					multipartFile.getInputStream(),
					new File(path,realFileName));
			Document document = new Document();
			document.setTitle(title);
			document.setRemark(remark);
			document.setUserId(userId);
			document.setEmpId(empId);
			document.setFilename(fileName);
			document.setRealfilename(path+"\\"+realFileName);
			documentService.saveDocument(document);
		}
		return Msg.success();
	}
	@RequestMapping("/document/download")
	@ResponseBody
	public Msg download(@RequestParam(value="id")Integer id,HttpServletResponse response) throws IOException {
		Document document = documentService.getDocument(id);
		System.out.println(document.getRealfilename());
		String path = document.getRealfilename();
		String filename = document.getFilename();
		
		InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
		filename = URLEncoder.encode(filename, "UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + filename);
		response.setContentType("multipart/form-data");
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		int len = 0;
		while ((len = bis.read()) != -1) {
			out.write(len);
			out.flush();
		}
		out.close();
		return Msg.success();
		
	}
}
