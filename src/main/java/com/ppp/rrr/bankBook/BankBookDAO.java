package com.ppp.rrr.bankBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iu.main.util.Pager;
import com.ppp.rrr.bankBook.comment.CommentDTO;

@Repository //해당클래스의 객체를 생성
public class BankBookDAO {
	
	

	@Autowired
	private SqlSession sqlSession;
	private final String NAMESPACE="com.iu.main.bankBook.BankBookDAO.";
//	comment
	public long getCommentTotal(CommentDTO commentDTO) throws Exception{
		return sqlSession.selectOne(NAMESPACE+"getCommentTotal", commentDTO);
	}
	public List<CommentDTO> getCommentList(Map<String, Object> map) throws Exception{
		return sqlSession.selectList(NAMESPACE+"getCommentList" ,map);
	}
	public int setCommentAdd(CommentDTO commentDTO) throws Exception{
		return sqlSession.insert(NAMESPACE+"setCommentAdd", commentDTO);
	}
	
//	BankBook
	
	//total
	public Long getTotal() throws Exception{
		return sqlSession.selectOne(NAMESPACE+"getTotal");
	}
	public long getSeqence() throws Exception{
		return sqlSession.selectOne(NAMESPACE+"getSeqence");
	}
	public int setFileAdd(BankFileDTO bankFileDTO) throws Exception{
		return sqlSession.insert(NAMESPACE+"setFileAdd", bankFileDTO);
	}
	//List
	public List<BankBookDTO> getList(Pager pager) throws Exception{
		return sqlSession.selectList(NAMESPACE+"getList",pager);
	}
	//Detail
	public BankBookDTO getDetail(BankBookDTO bankBookDTO) throws Exception{
	
		return sqlSession.selectOne(NAMESPACE+"getDetail",bankBookDTO);
	}
	//add
	public int setAdd(BankBookDTO bankBookDTO) throws Exception {
		return sqlSession.insert(NAMESPACE+"setAdd", bankBookDTO);
	}
	//update
	public int setUpdate(BankBookDTO bankBookDTO)throws Exception{
		return sqlSession.delete(NAMESPACE+"setUpdate", bankBookDTO);
	}
	//delete
	public int setDelete(Long num) throws Exception {
		return sqlSession.delete(NAMESPACE+"setDelete", num);
	}
	
}
