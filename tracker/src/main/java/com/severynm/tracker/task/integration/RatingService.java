package com.severynm.tracker.task.integration;

import java.util.Map;
import java.util.Set;

public interface RatingService {

    Map<Long, Integer> getRating(Set<Long> users);

}
