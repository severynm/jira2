package com.severynm.tracker.task.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Set;

@FeignClient(name = "ratingApi",url = "http://localhost:8081")
public interface RatingApi {

    @PostMapping("rating")
    Map<Long,Integer> getRating(@RequestBody Set<Long> users);
}
