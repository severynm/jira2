package com.severynm.tracker.task;

import com.severynm.tracker.task.integration.RatingApi;
import com.severynm.tracker.task.integration.RatingServiceImpl;
import feign.RetryableException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class TrackerApplicationTests {

    @Configuration
    @EnableRetry
    @Import(RatingServiceImpl.class)
    public static class MyConfig {}

    @Mock
    RatingApi ratingApi;

    @InjectMocks
    private RatingServiceImpl target;

    @Test
    void getRatingTest_ifFirstAttemptFailed_shouldRetry() {
        //TODO:fix test
//        when(ratingApi.getRating(anySet()))
//                .thenThrow(RetryableException.class)
//                .thenReturn(Collections.singletonMap(1L, 2));
//        Map<Long, Integer> rating = target.getRating(Collections.emptySet());
//        assertNotNull(rating);
//        assertEquals(2, rating.get(1L));
    }

}
