package com.back.global.app;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConfig {

    @Bean
    public ToolCallbackProvider tool(MemberTool memberTool) {
        return MethodToolCallbackProvider.builder()
                                         .toolObjects(memberTool)
                                         .build();
    }
}
