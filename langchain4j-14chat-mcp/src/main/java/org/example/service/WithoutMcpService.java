package org.example.service;

import reactor.core.publisher.Flux;

public interface WithoutMcpService {
    Flux<String> chat(String prompt);
}
