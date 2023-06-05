package com.gdu.workship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectFileDTO {

	private int projectFileNo;
	private ProjectDTO projectDTO;
	private String projectFilePath;
	private String projectFileOriginName;
	private String projectFileSystemName;
}
