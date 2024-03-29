package com.pervukhin.rest;

import com.pervukhin.domain.Profile;
import com.pervukhin.domain.ResultEmailAndPassword;
import com.pervukhin.rest.dto.ProfileDto;
import com.pervukhin.service.ProfileService;
import com.pervukhin.service.ProfileServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ProfileController  {
    private final ProfileService profileService;

    public ProfileController() throws SQLException, ClassNotFoundException {
        this.profileService = new ProfileServiceImpl();
    }


    @GetMapping("/profile/{id}")
    public ProfileDto getProfileById(@PathVariable int id) {
        return ProfileDto.toDto(profileService.getById(id));
    }

    @PostMapping("/profile")
    public String createProfile(@RequestParam String name,
                              @RequestParam String login,
                              @RequestParam String password,
                              @RequestParam String number){
            Profile profile = new Profile(
                    name,
                    login,
                    password,
                    number);

            return profileService.insert(profile);
    }

    @PostMapping("/profile/{id}")
    public String updateProfile(
            @PathVariable int id ,
            @RequestParam String name,
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String number){
        Profile profile = new Profile(
                id,
                name,
                login,
                password,
                number);

        return profileService.update(profile);
    }

    @DeleteMapping("/profile/{id}")
    public String deleteProfile(@PathVariable int id){
        return profileService.delete(id);
    }

    @PostMapping("/profile/numbers")
    public List<ProfileDto> getByNumber(@RequestBody List<Map<String, String>> body){
        List<String> list = new ArrayList<>();
        for (Map<String,String> map: body){
            list.add(map.get("number"));
        }
        return ProfileDto.toDto(profileService.getByNumbers(list));
    }

    @GetMapping("/profile/{login}/{password}")
    public ResultEmailAndPassword isRightPasswordAndLogin(@PathVariable String login, @PathVariable String password){
        return profileService.isRightPasswordAndLogin(login,password);
    }
}
