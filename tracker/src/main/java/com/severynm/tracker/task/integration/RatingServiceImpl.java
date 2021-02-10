package com.severynm.tracker.task.integration;

import feign.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class RatingServiceImpl implements RatingService {

    private final static Logger LOG = LoggerFactory.getLogger(RatingServiceImpl.class);

    private final RatingApi ratingApi;

    public RatingServiceImpl(RatingApi ratingApi) {
        this.ratingApi = ratingApi;
    }

    @Override
    @Retryable(
            value = {RetryableException.class},
            backoff = @Backoff(delay = 20)
    )
    public Map<Long, Integer> getRating(Set<Long> users) {
        LOG.info("[ratingRequest] get rating for users {}", users);
        Map<Long, Integer> rate = ratingApi.getRating(users);
        LOG.info("[ratingRequest] retrieved ratings {}", rate);
        return rate;
    }
}
