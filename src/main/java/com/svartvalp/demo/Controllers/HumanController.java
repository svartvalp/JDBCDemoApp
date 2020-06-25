package com.svartvalp.demo.Controllers;

import com.svartvalp.demo.Exceptions.NotFoundException;
import com.svartvalp.demo.Human;
import com.svartvalp.demo.Repositories.HumanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HumanController {
    HumanRepository humanRepository;
    Logger logger = LoggerFactory.getLogger(HumanController.class);

    @Autowired
    public void setHumanRepository(HumanRepository humanRepository) {
        this.humanRepository = humanRepository;
    }

    @GetMapping(value = "/human")
    public String showAllHumans(Model model) {
        model.addAttribute("humans", humanRepository.getAllHumans());
        return "allHumans";
    }

    @GetMapping(value = "/human/add")
    public String addHumanForm(Model model) {
        model.addAttribute("human", new Human());
        return "addHuman";
    }

    @PostMapping(value = "/human/{id}/change")
    public String changeHuman(@RequestParam("name") String name,
                              @RequestParam("lastName") String lastName,
                              @RequestParam("age") int age,
                              @PathVariable("id") int id) {
        humanRepository.updateHuman(id, new Human(id, name, lastName, age));
        return "redirect:/human";
    }

    @GetMapping(value = "/human/{id}")
    public String showHuman(@PathVariable("id") int id, Model model) {
        model.addAttribute("human", humanRepository.findHumanById(id)
                .orElseThrow(() -> {throw new NotFoundException("human not found by id");}));
        return "human";
    }


    @PostMapping(value = "/human/add")
    public String addHuman(Human human) {
        humanRepository.insertHuman(human)
                .ifPresentOrElse(id -> logger.info("successfully inserted human with id : {}", id),
                        () -> logger.warn("cannot insert human into db!"));
        return "redirect:";
    }

    @DeleteMapping(value = "/human/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteHuman(@PathVariable("id") int id) {
        logger.info("trying to delete human with id : {}",id);
        humanRepository.deleteHuman(id);
        logger.info("successfully deleted human with id : {}", id);
    }
}
