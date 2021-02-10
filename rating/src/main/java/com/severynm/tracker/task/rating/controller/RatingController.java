package com.severynm.tracker.task.rating.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
public class RatingController {

    private final Random randomizer = new Random();

    @PostMapping("rating")
    public Map<Long, Integer> getRating(@RequestBody List<Long> users) {
        return users.stream()
                .collect(Collectors.toMap(userId -> userId, userId -> randomizer.nextInt(3) + 1));
    }

}
