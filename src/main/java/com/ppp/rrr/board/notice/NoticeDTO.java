package com.ppp.rrr.board.notice;

import java.sql.Date;
import java.util.List;

import com.iu.main.board.BoardDTO;

public class NoticeDTO extends BoardDTO {

	private List<NoticeFileDTO> fileDTOs;

	
	
	
	public List<NoticeFileDTO> getFileDTOs() {
		return fileDTOs;
	}

	public void setFileDTOs(List<NoticeFileDTO> fileDTOs) {
		this.fileDTOs = fileDTOs;
	}

}
