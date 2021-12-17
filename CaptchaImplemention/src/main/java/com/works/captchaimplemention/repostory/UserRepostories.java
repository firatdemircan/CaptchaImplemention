package com.works.captchaimplemention.repostory;

import com.works.captchaimplemention.model.Usertmo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepostories extends JpaRepository<Usertmo,Integer> {
    Optional<Usertmo> findByUserName(String userName);


}
