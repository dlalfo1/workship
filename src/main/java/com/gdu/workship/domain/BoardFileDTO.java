package com.gdu.workship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardFileDTO {
	
	private int boardFileNo;
	private BoardDTO boardDTO;
	private String boardFilePath;
	private String boardFileOriginName;
	private String boardFIleSystemName;
	
}
