package poly.edu.assignment_earphone.services;

import poly.edu.assignment_earphone.models.Users;

public interface AuthenService {
    Users login(String username, String pass);
    boolean isAdmin();
    void logout();
    Users getUser();
}
