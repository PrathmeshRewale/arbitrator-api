package com.mac.arbitrator.util;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    public static <T> T getRandomElement(List<T> list) {
        int index = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(index);
    }
}
