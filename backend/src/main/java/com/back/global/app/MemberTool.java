package com.back.global.app;

import com.back.domain.member.dto.MemberDto;
import com.back.domain.member.dto.MemberJoinReqBody;
import com.back.domain.member.entity.Member;
import com.back.domain.member.repository.MemberRepository;
import com.back.domain.member.service.MemberService;
import com.back.global.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberTool {

    private final S3Uploader s3Uploader;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Tool
    public MemberDto createMember(MemberJoinReqBody reqBody) {
        Member member = memberService.join(reqBody);
        String presignedUrl = s3Uploader.generatePresignedUrl(member.getProfileImgUrl());

        log.info("MCP 서버가 사용자를 생성하였습니다.");
        return new MemberDto(member, presignedUrl);
    }

    @Tool(name = "getAllMembers", description = "사용자 목록을 조회해야 할 때 이 도구를 사용하세요.")
    public List<MemberDto> getAllMembers() {
        return memberRepository.findAll()
                               .stream()
                               .map(m -> new MemberDto(m, s3Uploader.generatePresignedUrl(m.getProfileImgUrl())))
                               .toList();
    }

    @Tool
    public String getWeather(String location) {
        final String[] weathers = {"Sunny", "Rainy", "Cloudy"};
        final Random random = new Random();

        return weathers[random.nextInt(weathers.length)];
    }
}
