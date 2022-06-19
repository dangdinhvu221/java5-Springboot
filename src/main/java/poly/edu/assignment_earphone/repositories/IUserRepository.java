package poly.edu.assignment_earphone.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.assignment_earphone.models.typeEnum.TypeStatus;
import poly.edu.assignment_earphone.models.Users;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<Users, Long> {
    List<Users> findByFullNameLike(String fullName);
    List<Users> findByTypeStatus(TypeStatus typeStatus);
    Optional<Users> findByUsername(String username);
    Users findByUsernameAndPassword(String username, String password);

    Users findByUsernameLike(String username);

}
