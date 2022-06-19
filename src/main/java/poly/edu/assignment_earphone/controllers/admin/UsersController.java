package poly.edu.assignment_earphone.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import poly.edu.assignment_earphone.models.typeEnum.TypeGender;
import poly.edu.assignment_earphone.models.typeEnum.TypeRole;
import poly.edu.assignment_earphone.models.typeEnum.TypeStatus;
import poly.edu.assignment_earphone.models.Users;
import poly.edu.assignment_earphone.services.UserService;
import java.util.Date;

@Controller
@RequestMapping("/earPhone/dashAdmin")
@PreAuthorize("isAuthenticated() and hasAuthority('ADMIN')")
public class UsersController {

    @Autowired
     UserService userService;

    @GetMapping("/manageUsers")
    private String manageUser(ModelMap model) {
//        model.addAttribute("listUsers", userService.getAllUsers());
        return findPaginated(1, model);
    }

    @GetMapping("/formUser")
    private String showFormUser(ModelMap model) {
        model.addAttribute("BASEURL", "/earPhone/dashAdmin");
        model.addAttribute("CREATE", "/addUser");
        return "dashAdmin/fragments/form-CreatedUser";
    }


    @PostMapping("/addUser")
    private String createUser(@RequestParam("image") MultipartFile image,
                              @RequestParam("fullName") String fullName,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("email") String email,
                              @RequestParam("address") String address,
                              @RequestParam("birth")
                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthDay,
                              @RequestParam("gender") TypeGender gender,
                              @RequestParam("role") TypeRole typeRole,
                              @RequestParam("phone") String phone
    ) {
        this.userService.addUsers(username, password, fullName, email, phone, image, new Date(), typeRole, gender, TypeStatus.ONLINE, address, birthDay);
        return "redirect:/earPhone/dashAdmin/formUser";
    }

    @GetMapping("/editUser/{ids}")
    private String editUser(ModelMap model,
                            @PathVariable() Long ids
    ) {
        Users user = this.userService.getUser(ids);
        model.addAttribute("user", user);
        model.addAttribute("UPDATE", "/updateUser");
        model.addAttribute("BASEURL", "/earPhone/dashAdmin");
        return "dashAdmin/fragments/form-EditUser";
    }

    @PostMapping("/updateUser")
    public String updateUser(ModelMap model, @RequestParam("idUser") Long id,
                             @RequestParam("image") MultipartFile image,
                             @RequestParam("fullName") String fullName,
                             @RequestParam("username") String username,
                             @RequestParam("email") String email,
                             @RequestParam("address") String address,
                             @RequestParam("birth")
                             @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthDay,
                             @RequestParam("gender") TypeGender gender,
                             @RequestParam("role") TypeRole typeRole,
                             @RequestParam("phone") String phone

    ) {
        Users user = this.userService.getUser(id);
        this.userService.updateUsers(id, username, user.getPassword(), fullName, email, phone, image, user.getCreated(), typeRole, gender, TypeStatus.ONLINE, address, birthDay);
        model.addAttribute("UPDATE", "/updateUser");
        model.addAttribute("BASEURL", "/earPhone/dashAdmin");
        return "redirect:/earPhone/dashAdmin/manageUsers";
    }

    @GetMapping("/blockUser/{id}")
    public String blockUser(@PathVariable Long id
    ) {
        Users user = this.userService.getUser(id);
        this.userService.update(user);
        return "redirect:/earPhone/dashAdmin/manageUsers";
    }

    @GetMapping("/deleteUsers")
    public String deleteUser(@RequestParam("checkedID") Long[] ids) {
        this.userService.deleteAllUsers(ids);
        return "redirect:/earPhone/dashAdmin/manageUsers";
    }

    @GetMapping("/searchUsers")
    public String searchUsers(ModelMap model,
                              @RequestParam("search") String name
    ) {
        model.addAttribute("ListUsers", this.userService.getUsersByName(name));
        Page<Users> pageUser = userService.findPaginated(1, 5);
        model.addAttribute("page", pageUser);
        model.addAttribute("currentPage", 1);
        this.actions(model);
        return "dashAdmin/fragments/manage-Users";
    }

    @GetMapping("/pageUsers/{pageNo}")
    public String findPaginated(@PathVariable("pageNo") int pageNo, ModelMap model) {
        int pageSize = 5;
        Page<Users> pageUser = userService.findPaginated(pageNo, pageSize);
        model.addAttribute("ListUsers", pageUser.getContent());
        model.addAttribute("page", pageUser);
        model.addAttribute("currentPage", pageNo);
        this.actions(model);
        return "dashAdmin/fragments/manage-Users";
    }

    public void actions(ModelMap model) {
        model.addAttribute("ADD", "/formUser");
        model.addAttribute("BASEURL", "/earPhone/dashAdmin");
        model.addAttribute("BLOCK", "/blockUser/");
        model.addAttribute("SEARCH", "/searchUsers");
        model.addAttribute("EDIT", "/editUser/");
        model.addAttribute("DELETE_ALL", "/earPhone/dashAdmin/deleteUsers");
        model.addAttribute("PAGE", "/pageUsers/");
    }
}
