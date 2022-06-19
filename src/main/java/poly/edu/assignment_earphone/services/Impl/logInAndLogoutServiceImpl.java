package poly.edu.assignment_earphone.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.assignment_earphone.models.typeEnum.TypeRole;
import poly.edu.assignment_earphone.models.Users;
import poly.edu.assignment_earphone.repositories.IUserRepository;
import poly.edu.assignment_earphone.services.AuthenService;

import javax.servlet.http.HttpServletRequest;

@Service
public class logInAndLogoutServiceImpl implements AuthenService {
    private static final String ATT_USER_LOGIN = "login";

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IUserRepository usersRepository;

    @Override
    public Users login(String username, String pass) {
        System.out.println(usersRepository.findByUsernameAndPassword(username,pass).getTypeRole().toString());
        return usersRepository.findByUsernameAndPassword(username,pass);
    }

    @Override
    public boolean isAdmin() {
        Users user = getUser();
        return user.getTypeRole().getValue().equals(TypeRole.ADMIN.getValue());
    }

    @Override
    public void logout() {
        request.getSession().removeAttribute(ATT_USER_LOGIN);
    }

    @Override
    public Users getUser() {
        Users user = (Users) request.getSession().getAttribute(ATT_USER_LOGIN);
        if (user == null) {
            user = new Users();
            request.getSession().setAttribute(ATT_USER_LOGIN, user);
        }
        return user;
    }
}
