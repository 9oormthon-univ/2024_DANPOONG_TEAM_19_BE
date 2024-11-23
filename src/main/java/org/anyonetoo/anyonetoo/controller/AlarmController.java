package org.anyonetoo.anyonetoo.controller;

import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.alarm.AlarmResponseDto;
import org.anyonetoo.anyonetoo.domain.dto.alarm.OrderAlarmResponseDto;
import org.anyonetoo.anyonetoo.domain.dto.res.ResponseDto;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.service.AlarmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/alarm")
public class AlarmController {

    private final AlarmService alarmService;
    @GetMapping
    public ResponseEntity<ResponseDto<List<AlarmResponseDto>>> getAlarms(@AuthenticationPrincipal User user){
     return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(alarmService.getAlarms(user), "알람 불러오기 성공"));
    }

}
