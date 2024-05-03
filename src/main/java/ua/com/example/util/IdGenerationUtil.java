package ua.com.example.util;

import java.util.concurrent.atomic.AtomicLong;

public final class IdGenerationUtil {

    private static final AtomicLong idCounter = new AtomicLong(1);

    public static Long generateId() {
        return idCounter.getAndIncrement();
    }
}
