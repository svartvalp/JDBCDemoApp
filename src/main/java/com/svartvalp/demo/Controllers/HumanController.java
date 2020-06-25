package com.svartvalp.demo.Controllers;

import com.svartvalp.demo.Human;
import com.svartvalp.demo.Repositories.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HumanController {
    HumanRepository humanRepository;

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
        model.addAttribute("human", humanRepository.findHumanById(id).orElseThrow(() -> {throw new RuntimeException("no,no,NO");}));
        return "human";
    }


    @PostMapping(value = "/human/add")
    public String addHuman(Human human) {
        humanRepository.insertHuman(human);
        return "redirect:";
    }

    @DeleteMapping(value = "/human/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteHuman(@PathVariable("id") int id) {
        humanRepository.deleteHuman(id);
    }
}
