package com.kindredgroup.unibetlivetest.api;

import com.kindredgroup.unibetlivetest.dto.BetRequest;
import com.kindredgroup.unibetlivetest.dto.GenericResponse;
import com.kindredgroup.unibetlivetest.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Urls.BASE_PATH)
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class BetApi {

    private final BetService betService;

    @PostMapping(Urls.ADD_BET)
    public ResponseEntity<GenericResponse<String>> saveBet(
            @RequestHeader(name = "Customer-Pseudo") String customerPseudo,
            @Valid @RequestBody BetRequest betRequest
    ) {
        betService.saveBet(customerPseudo, betRequest);
        return ResponseEntity.ok(
                GenericResponse.<String>builder()
                        .response("Bet has been saved successfully")
                        .build()
        );
    }

}
