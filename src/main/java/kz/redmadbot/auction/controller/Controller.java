package kz.redmadbot.auction.controller;

import kz.redmadbot.auction.DTO.AnnouncementDto;
import kz.redmadbot.auction.DTO.SignUpDTO;
import kz.redmadbot.auction.entity.Role;
import kz.redmadbot.auction.entity.User;
import kz.redmadbot.auction.exceptionHandling.InfoMessage;
import kz.redmadbot.auction.repository.RoleRepository;
import kz.redmadbot.auction.repository.UserRepository;
import kz.redmadbot.auction.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private InfoMessage message;

    @PutMapping("/auction/{id}/{cost}")
    public ResponseEntity<InfoMessage> suggestPrice(@PathVariable("id") long id,
                                                    @PathVariable("cost") int cost) {
        boolean result = announcementService.suggestingPrice(id, cost);
        if(result) {
            message.setInfo("Successfully accepted");
            return new ResponseEntity<> (message, HttpStatus.OK);
        }
        else {
            message.setInfo("Something went wrong, try again");
            return new ResponseEntity<> (message, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/auction")
    public List<AnnouncementDto> getAllActiveAnnouncements() {
        return announcementService.getAllActive();
    }

    @PostMapping("/auction")
    public ResponseEntity<InfoMessage> saveAnnouncement(@Valid @RequestBody AnnouncementDto announcementDto) {
        message.setInfo("Announcement added successfully");
        announcementService.save(announcementDto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDTO signUpDto){
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            message.setInfo("Email is already used!");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        if(!signUpDto.getPassword().equals(signUpDto.getPasswordConfirmation())){
            message.setInfo("Password and password confirmation do not match");
            return new ResponseEntity<>(message,
                    HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        Role roles = roleRepository.findByRoleName("ROLE_USER").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        message.setInfo("User registered successfully");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @PostMapping("/auth/signin")
    public ResponseEntity<InfoMessage> authenticateUser(@RequestBody User user){
        String incorrect = "Username or password is incorrect";
        String success = "User signed-in successfully!";
        if(!userRepository.existsByEmail(user.getEmail())){
            message.setInfo(incorrect);
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }
        if(!passwordEncoder.matches(user.getPassword(),
                userRepository.findByEmail(user.getEmail()).get().getPassword())){
            message.setInfo(incorrect);
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        user.getEmail(), user.getPassword()));
        message.setInfo(success);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
