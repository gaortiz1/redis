package com.witbooking.redis.core.storage;

import com.witbooking.redis.core.storage.DefaultStrings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class DefaultStringsTest {

    private final long MAX_DURATION_IN_SECONDS = 100;
    private final long MIN_DURATION_IN_SECONDS = 1;
    private final long DEFAULT_DURATION_IN_SECONDS = 3;

    private DefaultStrings strings;

    @BeforeEach
    void setUp() {
        strings = new DefaultStrings();
    }

    @Nested
    class WhenCallSetMethod {

        @Test
        void shouldSaveValueWithoutExpiringWithinNSeconds() {
            final String key = "key";

            strings.set(key, "value", MAX_DURATION_IN_SECONDS);

            await()
                    .during(DEFAULT_DURATION_IN_SECONDS, SECONDS)
                    .untilAsserted(() -> assertThat(strings.get(key).orElse(null)).isEqualTo("value") );
        }

        @Test
        void shouldNotSaveValueWhenItExpiredWithinNSeconds() {
            final String key = "key";

            strings.set(key, "value", MIN_DURATION_IN_SECONDS);

            await()
                    .during(DEFAULT_DURATION_IN_SECONDS, SECONDS)
                    .untilAsserted(() -> assertThat(strings.get(key).orElse(null)).isNull() );
        }
    }

    @Nested
    class WhenCallReplaceMethod {

        @Test
        void shouldReplaceValueWithoutExpiringWithinNSeconds() {
            final String key = "key";

            strings.set(key, "value", MAX_DURATION_IN_SECONDS);
            strings.replace(key, "value1");

            await()
                    .during(DEFAULT_DURATION_IN_SECONDS, SECONDS)
                    .untilAsserted(() -> assertThat(strings.get("key").orElse(null)).isEqualTo("value1") );
        }

        @Test
        void shouldNotReplaceValueWhenItExpiredWithinNSeconds() {
            final String key = "key";
            strings.set(key, "value", MIN_DURATION_IN_SECONDS);

            strings.replace(key, "value1");

            await()
                    .during(DEFAULT_DURATION_IN_SECONDS, SECONDS)
                    .untilAsserted(() -> assertThat(strings.get(key).orElse(null)).isNull() );
        }

    }

    @Nested
    class WhenCallDeleteMethod {

        @Test
        void shouldDeleteValueWhenItIsNotExpiredWithinNSeconds() {
            final String key = "key";

            strings.set(key, "value", MAX_DURATION_IN_SECONDS);
            assertThat(strings.delete(key).orElse(null)).isEqualTo("value");
        }

        @Test
        void shouldDeleteValueWhenItExpiredWithinNSeconds() {
            final String key = "key";

            strings.set(key, "value", MIN_DURATION_IN_SECONDS);

            await()
                    .during(DEFAULT_DURATION_IN_SECONDS, SECONDS)
                    .untilAsserted(() -> assertThat(strings.delete(key).orElse(null)).isNull() );
        }
    }

    @Nested
    class WhenCallSizeMethod {
        @Test
        void shouldGetSizeWhenItIsNotExpiredWithinNSeconds() {
            final String key = "key";

            strings.set(key, "value", MAX_DURATION_IN_SECONDS);
            assertThat(strings.size()).isEqualTo(1);
        }

        @Test
        void shouldGetSizeWhenItExpiredWithinNSeconds() {
            final String key = "key";

            strings.set(key, "value", MIN_DURATION_IN_SECONDS);

            await()
                    .during(DEFAULT_DURATION_IN_SECONDS, SECONDS)
                    .untilAsserted(() -> assertThat(strings.size()).isEqualTo(0) );
        }
    }
}