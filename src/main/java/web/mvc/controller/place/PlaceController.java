package web.mvc.controller.place;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.mvc.entity.user.Users;
import web.mvc.service.place.PlaceService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
@Slf4j
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/")
    public ResponseEntity<?> getPlaceByUserAddress(@RequestBody Users user){
        log.info(user.getAddress());
        return ResponseEntity.status(HttpStatus.OK).body(placeService.getPlaceByUserAddress(user.getAddress()));
    }
}
