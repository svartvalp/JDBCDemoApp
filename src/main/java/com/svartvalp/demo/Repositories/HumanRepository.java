package com.svartvalp.demo.Repositories;

import com.svartvalp.demo.Human;

import java.util.List;
import java.util.Optional;

public interface HumanRepository {
    List<Human> getAllHumans();
    Optional<Integer> insertHuman(Human human);
    void updateHuman(int id, Human human);
    void deleteHuman(int id);
    List<Human> getHumansByAge(int age);
    Optional<Human> findHumanById(int id);
    void clear();
}
