package team.stc.newrelicTestJava;

import com.newrelic.api.agent.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class NRTest {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Scheduled(fixedDelay = 3000)
    @Trace(dispatcher = true)
    void test() {
        List<String> snapshots = new ArrayList<>();
        for (int i = 0; i < 7000; i++) {
            snapshots.add(UUID.randomUUID().toString());
        }


        String key = UUID.randomUUID().toString();
        redisTemplate.opsForHash().put("NRTest", key, snapshots);
        List<Object> list = (List<Object>) redisTemplate.opsForHash().get("NRTest", key);
        System.out.println(list.get(0));
        redisTemplate.opsForHash().delete("NRTest", key);
    }

}
