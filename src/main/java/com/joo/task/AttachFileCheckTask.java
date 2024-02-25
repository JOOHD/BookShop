package com.joo.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.joo.mapper.AdminMapper;
import com.joo.model.AttachImageVO;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class AttachFileCheckTask {

	@Autowired
	private AdminMapper mapper;
	
	private String getFolderYesterDay() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cal = Calendar.getInstance(); // 현재 시간의 Calendar 객체를 가져온다.
		
		cal.add(Calendar.DATE, -1);	// 현재 날짜를 가져오는 코드
		
		String str = sdf.format(cal.getTime());// Calendar -> Date 객체로 변환
		
		return str.replace("-", File.separator);
	}
	
	@Scheduled(cron="0 * * * * *")
	public void checkFiles() throws Exception{
		log.warn("File Check Task Run.................");
		log.warn(new Date());
		log.warn("===================================================");
		
		// DB에 저장된 파일 리스트
		List<AttachImageVO> fileList = mapper.checkFileList();
		
		// 비교 기준 파일 리스트(Path 객체)
		List<Path> checkFilePath = new ArrayList<Path>();
			// 원본 이미지
		fileList.forEach(vo -> {
			Path path = Paths.get("C:\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName());
			checkFilePath.add(path);
		});
		
			// 썸네일 이미지
		fileList.forEach(vo -> {
			Path path = Paths.get("C:\\upload", vo.getUploadPath(), "s_" +  vo.getUuid() + "_" + vo.getFileName());
			checkFilePath.add(path);
		});
		
		// 디렉토리 파일 리스트(폴더에 저장된 이미지들의 정보)
		File targetDir = Paths.get("C:\\upload", getFolderYesterDay()).toFile();
		File[] targetFile = targetDir.listFiles(); // 해당 디렉토리에 저장되어 있는 파일들을 File 객체로 생성하여 요소로 가지는 배열 반환.
		
		// 삭제 대상 파일 리스트(분류)       
		List<File> removeFileList = new ArrayList<File>(Arrays.asList(targetFile));
		
		for(File file : targetFile) {
			
			checkFilePath.forEach(checkFile ->{
				if(file.toPath().equals(checkFile))
						removeFileList.remove(file);
			});
		}
		
		// 삭제 대상 파일 제거
		log.warn("file Delete : ");
		for(File file : removeFileList) {
			log.warn(file);
			file.delete();
		}		
		
		log.warn("========================================");
	}
}
