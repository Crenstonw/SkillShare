package com.triana.salesianos.edu.skillshare.message.controller;

import com.triana.salesianos.edu.skillshare.message.model.DirectMessage;
import com.triana.salesianos.edu.skillshare.message.repository.DirectMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageDeleter {
    private final DirectMessageRepository repository;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteDirectMessages() {
        LocalDateTime expireDate = LocalDateTime.now().minusYears(2);

        List<DirectMessage> ExpiredMessages = repository.findDirectMessagesExpired(expireDate);
        repository.deleteAll(ExpiredMessages);
    }
}
