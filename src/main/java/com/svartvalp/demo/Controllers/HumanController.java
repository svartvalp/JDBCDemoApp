package com.svartvalp.demo.Controllers;

import com.svartvalp.demo.Human;
import com.svartvalp.demo.Repositories.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String addHumanForm(Human human) {
        return "addHuman";
    }

    @PostMapping(value = "/human/add")
    public String addHuman(Human human) {
        humanRepository.insertHuman(human);
        return "redirect:";
    }
}
