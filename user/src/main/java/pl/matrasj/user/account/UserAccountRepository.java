package pl.matrasj.user.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {
    @Query("SELECT UserAccountEntity FROM UserAccountEntity uea WHERE (LOWER(uea.email) = :email)")
    Optional<UserAccountEntity> findByEmail(@Param("email") String email);
}
