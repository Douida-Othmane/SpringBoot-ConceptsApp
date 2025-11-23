package org.oth.apppractice.controller;

import lombok.AllArgsConstructor;
import org.oth.apppractice.dto.RegistrationRequestDto;
import org.oth.apppractice.service.RegistrationService;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequestDto request){
        return registrationService.register(request);
    }

    /*
    @GetMapping(path = "confim")
    public String confim(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }
     */
}
