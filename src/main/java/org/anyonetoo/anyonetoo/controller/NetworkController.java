package org.anyonetoo.anyonetoo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NetworkController {

    @GetMapping("/healthcheck")
    public String healthcheck(){
        return "200";
    }

}
