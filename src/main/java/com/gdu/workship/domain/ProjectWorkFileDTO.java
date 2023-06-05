package com.gdu.workship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectWorkFileDTO {
	
	private int projectWorkFileNo;
	private ProjectWorkDTO projectWorkDTO;
	private String projectWorkFilePath;
	private String projectWorkOriginName;
	private String projectWorkSystemName;
}
