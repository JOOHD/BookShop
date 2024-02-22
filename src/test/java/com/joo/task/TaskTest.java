package com.joo.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joo.mapper.AdminMapper;
import com.joo.model.AttachImageVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class TaskTest {

	@Autowired
	private AdminMapper mapper;
	
	// 디렉토리 이미지 파일 리스트(폴더에 저장된 이미지들)
	private String getFolderYesterDay() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cal = Calendar.getInstance();
				
		cal.add(Calendar.DATE, -1);
		
		String str = sdf.format(cal.getTime());
		
		return str.replace("-", File.separator);
	}
	
	@Test
	public void taskTests() { 
		
		// path 객체를 생성하기 위해 필요로한 폴더 경로 문자열(언제자 날짜)을 얻는 메서드를 작성.
		
		
		// DB에 저장된 파일 리스트
		List<AttachImageVO> fileList = mapper.checkFileList();	
		
		System.out.println("fileList : ");
		fileList.forEach( list -> System.out.println(list));
		System.out.println("========================================");
		
		// 비교 기준 파일 리스트(Path객체)
		List<Path> checkFilePath = new ArrayList<Path>();
		
			//원본 이미지
		fileList.forEach(vo ->{
			Path path = Paths.get("C:\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName());
			checkFilePath.add(path);
		});
		
		System.out.println("checkFilePath : ");
		checkFilePath.forEach(list -> System.out.println(list));
		System.out.println("========================================");
		
			//썸네일 이미지
		fileList.forEach(vo -> {
			Path path = Paths.get("C:\\upload", vo.getUploadPath(), "s_"  +  vo.getUuid() + "_" + vo.getFileName());
			checkFilePath.add(path);
		});
		
		System.out.println("checkFilePath(썸네일 이미지 정보 추가 후) : ");
		checkFilePath.forEach(list -> System.out.println(list));
		System.out.println("========================================");
		
		// 디렉토리 파일 리스트
		File targetDir = Paths.get("C:\\upload", getFolderYesterDay()).toFile();
		File[] targetFile = targetDir.listFiles();
		
		System.out.println("targetFile : ");
		for(File file : targetFile) {
			System.out.println(file); // targetFile의 배열에  디레고리에 저장되어 있는 파일의 정보들이 요소에 있는지 확인.
		}
		
		// 삭제 대상 파일 리스트(분류) 
		// ArrayList 리스트변수명 = new ArrayList<>(Arrays.asList(배열변수명));
		List<File> removeFileList = new ArrayList<File>(Arrays.asList(targetFile));
		
		System.out.println("removeFileList(필터 전) : ");		
		removeFileList.forEach(file -> {
			System.out.println(file);
		});		
		System.out.println("========================================");	
		
		for(File file : targetFile) {
			checkFilePath.forEach(checkFile ->{
				if(file.toPath().equals(checkFile))
					removeFileList.remove(file);
			});
		}
		
		System.out.println("removeFileList(필터 후) : ");
		removeFileList.forEach(file -> {
			System.out.println(file);
		});
		System.out.println("========================================");
		
		/* 파일 삭제 */
		for(File file : removeFileList) {
			System.out.println("삭제 : " + file);
			file.delete();
		}
	}
}








