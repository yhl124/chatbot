package com.example.chatbot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Policy {
	private int policyId;//정책 아이디
	private String region;//지역
	private String category;//분야
	private String pName;//정책명
	private String explanation;//정책설명
	private String support;//지원내용
	private String supervision;//주관기관
	private String managePeriod;//운영기간
	private String supportPeriod;//지원기간
	private String people;//지원인원
	private String condition;//지원조건
	private String age;//연령
	private String minAge;//최소 연령
	private String maxAge;//최대 연령
	private String academicAbility;//학력요구사항
	private String major;//전공요구사항
	private String employement;//고용상태
	private String expertise;//전문분야요구사항
	private String addition;//추가단서사항
	private String limit;//참여제한대상
	private String process;//신청절차
	private String papers;//제출서류
	private String screening;//심사 및 발표
	private String url;//신청url
	private String etct;//기타
	private String manageOrg;//운영기관
	private String charge;//관리담당자
	private String callNumber;//운영기관연락처
	private String url1;//참고사이트url1
	private String sDate;//시작일
	private String eDate;//종료일
	private int sMonth;//정책시작월
	private String status;//현재진행여부
}
