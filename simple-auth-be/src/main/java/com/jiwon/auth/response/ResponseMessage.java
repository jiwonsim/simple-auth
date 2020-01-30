package com.jiwon.auth.response;

public class ResponseMessage {
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String SAVE_USER = "회원 정보 저장 성공";
    public static final String SUCCESSFUL_LOGOFF = "로그아웃 성공";

    public static final String GOOGLE_LOGIN = "구글 계정으로 로그인 성공";

    public static final String DUPLICATED_USER = "회원 정보 중복";
    public static final String NOT_FOUND_USER = "회원 정보 조회 실패";
    public static final String INVALID_INFO = "잘못된 정보 입력";
}
