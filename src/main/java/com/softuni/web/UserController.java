package com.softuni.web;

import com.softuni.model.binding.UserProfileEditBindingModel;
import com.softuni.model.binding.UserRegisterBindingModel;
import com.softuni.model.service.UserServiceModel;
import com.softuni.model.view.UserProfileViewModel;
import com.softuni.service.JobService;
import com.softuni.service.RealEstateService;
import com.softuni.service.UserService;
import com.softuni.service.VehicleService;
import com.softuni.web.annotation.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;


    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;

    }

    @GetMapping("/login")
    @PageTitle("login")
    @PreAuthorize("isAnonymous()")
    public String login() {
        return "login";
    }


    @PageTitle("register")
    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String register(Model model) {
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }
        return "register";
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
                                        BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            modelAndView.addObject("userRegisterBindingModel", userRegisterBindingModel);
            modelAndView.setViewName("register");
            return modelAndView;
        }
        this.userService.registerUser(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        modelAndView.setViewName("redirect:/users/login");
        return modelAndView;
    }

    @PageTitle("all users")
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView usersAll(ModelAndView modelAndView) {
        List<UserServiceModel> allUsers = this.userService.findAllUsers().stream()
                .map(user -> this.modelMapper.map(user, UserServiceModel.class)).collect(Collectors.toList());
        modelAndView.addObject("users", allUsers);
        modelAndView.setViewName("user/users-all");
        return modelAndView;
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setUser(@PathVariable String id, ModelAndView modelAndView) {
        this.userService.setRoleToUser(id, "user");
        modelAndView.setViewName("redirect:/users/all");
        return modelAndView;
    }

    @PostMapping("/set-moderator/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setModerator(@PathVariable String id, ModelAndView modelAndView) {
        userService.setRoleToUser(id, "moderator");
        modelAndView.setViewName("redirect:/users/all");
        return modelAndView;
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin(@PathVariable String id, ModelAndView modelAndView) {
        this.userService.setRoleToUser(id, "admin");
        modelAndView.setViewName("redirect:/users/all");
        return modelAndView;
    }

    @PageTitle("profile")
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(ModelAndView modelAndView, Principal principal) {
        modelAndView.addObject("model", this.modelMapper.map(this.userService.findByUsername(principal.getName()), UserProfileViewModel.class));
        modelAndView.setViewName("user/profile");
        return modelAndView;
    }
    @PageTitle("edit profile")
    @GetMapping("/edit-profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfile(ModelAndView modelAndView, Principal principal) {
        modelAndView.addObject("userProfileEditBindingModel", this.modelMapper.map(this.userService.findByUsername(principal.getName()), UserProfileEditBindingModel.class));
        modelAndView.setViewName("user/edit-profile");
        return modelAndView;
    }

    @PostMapping("/edit-profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfileConfirm(@Valid @ModelAttribute("userProfileEditBindingModel") UserProfileEditBindingModel userProfileEditBindingModel,
                                        BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors() || !userProfileEditBindingModel.getPassword().equals(userProfileEditBindingModel.getConfirmPassword())) {
            modelAndView.addObject("userProfileEditBindingModel", userProfileEditBindingModel);
            modelAndView.setViewName("user/edit-profile");
            return modelAndView;
        }
        this.userService.editUserProfile(this.modelMapper.map(userProfileEditBindingModel, UserServiceModel.class), userProfileEditBindingModel.getOldPassword());
        modelAndView.setViewName("redirect:/users/profile");
        return modelAndView;
    }


}
