package poly.edu.assignment_earphone.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import poly.edu.assignment_earphone.models.TypeGender;
import poly.edu.assignment_earphone.models.TypeRole;
import poly.edu.assignment_earphone.models.TypeStatus;
import poly.edu.assignment_earphone.models.Users;
import poly.edu.assignment_earphone.repositories.IUserRepository;
import poly.edu.assignment_earphone.services.UserService;

import java.io.IOException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public Optional<Users> findByID(String id) {
        return this.userRepository.findByUsername(id);
    }

    @Override
    public boolean LogIn(String username, String password) {
        Optional<Users> optionalUsers = findByID(username);
        if(optionalUsers.isPresent() && optionalUsers.get().getPassword().equals(password)){
            return true;
        }
        return false;
    }

    @Override
    public Users addUsers(String username, String password, String fullName, String email, String phone, MultipartFile file, Date created, TypeRole typeRole, TypeGender typeGender, TypeStatus typeStatus, String address, Date birthDay) {
        Users user = new Users();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("not a a valid file");
        }
        try {
            user.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setUsername(username);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setCreated(created);
        user.setTypeRole(typeRole);
        user.setTypeGender(typeGender);
        user.setTypeStatus(typeStatus);
        user.setAddress(address);
        user.setBirthDay(birthDay);
        return userRepository.save(user);
    }

    @Override
    public Users updateUsers(Long id, String username, String password, String fullName, String email, String phone, MultipartFile file, Date created, TypeRole typeRole, TypeGender typeGender, TypeStatus typeStatus, String address, Date birthDay) {
        Users user = new Users();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.isEmpty()) {
            user.setImage(this.userRepository.findById(id).get().getImage());
        }else{
            try {
                user.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setCreated(created);
        user.setTypeRole(typeRole);
        user.setTypeGender(typeGender);
        user.setTypeStatus(typeStatus);
        user.setAddress(address);
        user.setBirthDay(birthDay);
        return userRepository.save(user);
    }

    @Override
    public Users update(Users user) {
        Long id = user.getId();
        if (id != null) {
            Optional<Users> u = this.userRepository.findById(id);
            if (u.isPresent()) {
                user.setId(id);
                user.setTypeStatus(TypeStatus.OFFLINE);
                this.userRepository.save(user);
            }
        }
        return null;
    }

    @Override
    public List<Users> getAllUsers() {
        return this.userRepository.findByTypeStatus(TypeStatus.ONLINE);
    }

    @Override
    public List<Users> getUsersByName(String name) {
        return this.userRepository.findByFullNameLike("%" + name + "%");
    }

    @Override
    public Users getUsersByUsername(String username) {
        return this.userRepository.findByUsernameLike(username);
    }


    @Override
    public Users getUser(Long id) {
        return this.userRepository.findById(id).get();
    }

    @Override
    public Users deleteUser(Long id) {
        if (id != null) {
            Optional<Users> users = userRepository.findById(id);
            if (users.isPresent()) {
                userRepository.deleteById(id);
                return users.get();
            }
        }
        return null;
    }

    @Override
    public void deleteAllUsers(Long[] id) {
        if (id != null) {
            this.userRepository.deleteAllByIdInBatch(Arrays.asList(id));
        }
    }

    @Override
    public Page<Users> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.userRepository.findAll(pageable);
    }
}
