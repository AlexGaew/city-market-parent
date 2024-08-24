package ru.gaew.springcourse.managerapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gaew.springcourse.managerapp.entity.CityMarketUser;

import java.util.Optional;

public interface CityMarketUserReposittory extends CrudRepository<CityMarketUser, Integer> {
    Optional<CityMarketUser> findByuserName(String username);
}
