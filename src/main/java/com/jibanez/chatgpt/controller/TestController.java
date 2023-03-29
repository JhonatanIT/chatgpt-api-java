package com.jibanez.chatgpt.controller;

import com.jibanez.chatgpt.bean.MultiChatRecord;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
@Log4j2
public class TestController {

    private ChatgptService chatgptService;

    @GetMapping
    public ResponseEntity<String> getMessage(@RequestParam String prompt) {

        log.info("Prompt received: {}", prompt);
        String responseMessage = chatgptService.sendMessage(prompt).trim();
        log.info("Message generated: {}", responseMessage);

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PostMapping("/multi-chat")
    public ResponseEntity<String> testMultiChat(@RequestBody List<MultiChatRecord> multiChatRecordList) {

        List<MultiChatMessage> messages = multiChatRecordList.stream()
                .map(x -> {
                    log.info("Element chat (role: {}, content: {})", x.role(), x.content());
                    return new MultiChatMessage(x.role(), x.content());
                })
                .toList();

        String responseMessage = chatgptService.multiChat(messages);
        log.info("Message generated: {}", responseMessage);

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/{promptImage}")
    public ResponseEntity<String> testImage(@PathVariable String promptImage) {

        log.info("Image prompt received: {}", promptImage);
        String imageUrl = chatgptService.imageGenerate(promptImage);
        log.info("Image generated: {}", imageUrl);

        return new ResponseEntity<>(imageUrl, HttpStatus.OK);
    }
}
