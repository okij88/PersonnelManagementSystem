package com.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.bean.Document;
import com.hms.bean.DocumentExample;
import com.hms.dao.DocumentMapper;
@Service
public class DocumentService {
	@Autowired
	private DocumentMapper documentMapper;

	public List<Document> getAll() {
		// TODO Auto-generated method stub
		return documentMapper.selectByExampleWithUserAndEmp(null);
	}

	public void saveDocument(Document document) {
		// TODO Auto-generated method stub
		documentMapper.insertSelective(document);
	}

	public void deleteDocument(Integer id) {
		// TODO Auto-generated method stub
		documentMapper.deleteByPrimaryKey(id);
	}

	public void deleteDocuments(List<Integer> idList) {
		// TODO Auto-generated method stub
		DocumentExample example = new DocumentExample();
		com.hms.bean.DocumentExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(idList);
		documentMapper.deleteByExample(example);
	}

	public Document getDocument(Integer id) {
		// TODO Auto-generated method stub
		return documentMapper.selectByPrimaryKey(id);
	}

	public void updateDocument(Document Document) {
		// TODO Auto-generated method stub
		documentMapper.updateByPrimaryKeySelective(Document);
	}
}
