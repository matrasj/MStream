package pl.matrasj.user.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {
    Optional<UserAccountEntity> findByEmail(String email);

    @Query("SELECT uae FROM UserAccountEntity uae WHERE (uae.removed <> TRUE) AND (uae.isAssignedForNewsletter = true)")
    List<UserAccountEntity> findAllUsersAssignedForNewsletter();
}
