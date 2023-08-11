package com.ppp.rrr.board.qna;

import java.sql.Date;
import java.util.List;

import com.iu.main.board.BoardDTO;

public class QnADTO extends BoardDTO {
	

	private Long ref;
	private Long step;
	private Long depth;
	private List<QnAFileDTO> qnAFileDTOs;
	

	public List<QnAFileDTO> getQnAFileDTOs() {
		return qnAFileDTOs;
	}
	public void setQnAFileDTOs(List<QnAFileDTO> qnAFileDTOs) {
		this.qnAFileDTOs = qnAFileDTOs;
	}

	public Long getRef() {
		return ref;
	}
	public void setRef(Long ref) {
		this.ref = ref;
	}
	public Long getStep() {
		return step;
	}
	public void setStep(Long step) {
		this.step = step;
	}
	public Long getDepth() {
		return depth;
	}
	public void setDepth(Long depth) {
		this.depth = depth;
	}

}
