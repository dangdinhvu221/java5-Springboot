package poly.edu.assignment_earphone.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.assignment_earphone.models.TypeStatus;
import poly.edu.assignment_earphone.models.Users;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<Users, Long> {
    List<Users> findByFullNameLike(String fullName);
    List<Users> findByPhone(String phone);
    List<Users> findByTypeStatus(TypeStatus typeStatus);
    Optional<Users> findByUsername(String username);
    Users findByUsernameLike(String username);

}
