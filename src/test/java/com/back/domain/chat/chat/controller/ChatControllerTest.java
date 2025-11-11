package com.back.domain.chat.chat.controller;

import com.back.domain.chat.chat.dto.CreateChatRoomReqBody;
import com.back.domain.chat.chat.service.ChatService;
import com.back.domain.member.member.common.MemberRole;
import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ChatControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChatService chatService;

    @BeforeEach
    void setUp() {
        // ---------- 기존 member 테이블 유지 + 테스트용 회원 삽입 ----------
        memberRepository.deleteAll(); // 데이터만 초기화

        memberRepository.save(Member.builder()
                .email("user1@test.com")
                .password("1234")
                .name("홍길동")
                .phoneNumber("010-1111-1111")
                .address1("서울시 강남구")
                .address2("테헤란로 123")
                .nickname("hong")
                .isBanned(false)
                .role(MemberRole.USER) // Enum 타입으로 변경
                .profileImgUrl(null)
                .build()
        );

        memberRepository.save(Member.builder()
                .email("user2@test.com")
                .password("1234")
                .name("김철수")
                .phoneNumber("010-2222-2222")
                .address1("서울시 서초구")
                .address2("서초대로 456")
                .nickname("kim")
                .isBanned(false)
                .role(MemberRole.USER)
                .profileImgUrl(null)
                .build()
        );
    }

    @Test
    @WithUserDetails(value = "user1@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("채팅방 생성 성공")
    void createChatRoom_success() throws Exception {
        // given
        Member targetMember = memberRepository.findByEmail("user2@test.com").orElseThrow();
        CreateChatRoomReqBody reqBody = new CreateChatRoomReqBody(targetMember.getId());

        // when
        ResultActions resultActions = mvc.perform(post("/api/v1/chats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqBody)))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.msg").value(Matchers.containsString("채팅방이 생성되었습니다.")))
                .andExpect(jsonPath("$.data.message").value("kim&hong"))
                .andExpect(jsonPath("$.data.chatRoomId").exists());
    }
}
