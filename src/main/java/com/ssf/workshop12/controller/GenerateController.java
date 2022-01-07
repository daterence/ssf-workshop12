package com.ssf.workshop12.controller;

import com.ssf.workshop12.excepetion.RandomNumberException;
import com.ssf.workshop12.model.Generate;
import org.springframework.boot.logging.LoggerGroup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Controller
public class GenerateController {

    private Logger logger = LoggerFactory.getLogger(GenerateController.class);

    @GetMapping("/generate")
    public String showGenerateForm(Model model) {
        Generate generate = new Generate();
        model.addAttribute("generate", generate);
        return "generate";
    }

    @PostMapping("/generate")
    public String GenerateNumbers(@ModelAttribute Generate generate, Model model) {
        logger.info("From the form: " + generate.getNumberVal());
        int numberOfRandomNumbers = generate.getNumberVal();


        if (numberOfRandomNumbers > 10) {
//            throw new RandomNumberException();
            model.addAttribute("errorMessage", "OMG exceeded 10 already");
            return "error";
        }


        String[] imgNumbers = {
                "number0.jpg", "number1.jpg", "number2.jpg", "number3.jpg", "number4.jpg",
                "number5.jpg", "number6.jpg", "number7.jpg", "number8.jpg", "number9.jpg", "number10.jpg"
        };
        List<String> selectedImg = new ArrayList<>();
        Random randNum = new Random();
        Set<Integer> uniqueGeneratedRandNumSet = new LinkedHashSet<Integer>();

        while (uniqueGeneratedRandNumSet.size() < numberOfRandomNumbers) {
            Integer resultOfRandNumbers = randNum.nextInt(generate.getNumberVal() + 1);
            uniqueGeneratedRandNumSet.add(resultOfRandNumbers);
        }

        Iterator<Integer> it = uniqueGeneratedRandNumSet.iterator();
        Integer currentElem = null;
        while (it.hasNext()) {
            currentElem = it.next();
            logger.info("Current Element > " + currentElem);
//            selectedImg.add(imgNumbers[currentElem.intValue()]);
            selectedImg.add(imgNumbers[currentElem]);
        }
        model.addAttribute("randNumsResult", selectedImg.toArray());
        model.addAttribute("numVal", numberOfRandomNumbers);
        return "result";

    }
}
