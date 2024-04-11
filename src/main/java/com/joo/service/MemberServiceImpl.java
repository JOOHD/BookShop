package com.joo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joo.mapper.MemberMapper;
import com.joo.model.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper membermapper;

	/* 회원가입 */
	@Override
	public void memberJoin(MemberVO member) throws Exception {
		
		membermapper.memberJoin(member); // MemberMapper join 메서드 호출
		
		log.info("api test");
	}

	/* 아이디 중복 검사 */
	@Override
	public int idCheck(String memberId) throws Exception {
		
		return membermapper.idCheck(memberId);
	}

	/* 로그인 */
	@Override
	public MemberVO memberLogin(MemberVO member) throws Exception {
		
		return membermapper.memberLogin(member);
	}

	/* 주문자 정보 */
	@Override
	public MemberVO getMemberInfo(String memberId) {
		
		return membermapper.getMemberInfo(memberId);
	}
	
	

}
