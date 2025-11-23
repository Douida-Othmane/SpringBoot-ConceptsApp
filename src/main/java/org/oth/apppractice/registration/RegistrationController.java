package org.oth.apppractice.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequestDTO request){
        return registrationService.register(request);
    }

    /*
    @GetMapping(path = "confim")
    public String confim(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }
     */
}
