package com.elib.userclient.dao;
import com.elib.userclient.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser,Integer> {

    CustomUser findByAccountInfo_Email(String email);

    CustomUser findUserByAccountInfo_Email(@Param("email") String email);
}
