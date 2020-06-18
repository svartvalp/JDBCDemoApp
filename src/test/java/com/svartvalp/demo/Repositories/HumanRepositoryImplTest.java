package com.svartvalp.demo.Repositories;

import com.svartvalp.demo.Human;
import com.svartvalp.demo.Repositories.HumanRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HumanRepositoryImplTest {

    @Autowired
    HumanRepositoryImpl humanRepository;

    @Test
    void getAllHumans() {
        humanRepository.clear();
        humanRepository.insertHuman(new Human("a", "b", 2));
        var result = humanRepository.getAllHumans();
        assertEquals(1, result.size());
    }

    @Test
    void insertHuman() {
        Human human = new Human("Kasyan", "Kasyanenko", 20);
        int id = humanRepository.insertHuman(human).get();
        var result = humanRepository.findHumanById(id).get();
        assertEquals("Kasyan", result.getName());
    }

    @Test
    void updateHuman() {
        humanRepository.clear();
        humanRepository.insertHuman(new Human("a", "b", 1));
        var allHumans = humanRepository.getAllHumans();
        if(allHumans.size() > 0) {
            var human = allHumans.get(0);
            human.setName("Chan");
            humanRepository.updateHuman(human.getId(), human);
            var result = humanRepository.findHumanById(human.getId()).get();
            assertEquals("Chan", result.getName());
        } else {
            throw new RuntimeException("something went wrong, result set for all rows has 0 size");
        }
    }

    @Test
    void deleteHuman() {
        int id = humanRepository.insertHuman(new Human("a", "b", 2)).get();
        humanRepository.deleteHuman(id);
        var result = humanRepository.findHumanById(id);
        assertTrue(result.isEmpty());
    }

    @Test
    void getHumansByAge() {
        humanRepository.clear();
        humanRepository.insertHuman(new Human("abc", "def", 23));
        var result = humanRepository.getHumansByAge(23);
        assertTrue(result.contains(new Human("abc", "def", 23)));
    }

    @Test
    void findHumanById() {
        humanRepository.clear();
        int id = humanRepository.insertHuman(new Human("abc", "def", 12)).get();
        var result = humanRepository.findHumanById(id).get();
        assertEquals("abc", result.getName());
    }

    @Test
    void clear() {
        humanRepository.clear();
        var result = humanRepository.getAllHumans();
        assertEquals(0, result.size());
    }
}