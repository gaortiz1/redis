package com.witbooking.redis.core.template;

import com.witbooking.redis.core.exception.RedisException;
import com.witbooking.redis.core.storage.Strings;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultStringTemplateTest {

    @InjectMocks
    private DefaultStringTemplate defaultStringManager;

    @Mock
    private Strings stringsMock;

    @Nested
    class WhenCallSetMethod {

        @Nested
        class WithDuration {

            private final Long tenSeconds = 10L;

            @Nested
            class ShouldThrowException {

                @Test
                void whenKeyIsNull() {
                    final IllegalArgumentException exception = assertThrows(
                            IllegalArgumentException.class,
                            () -> defaultStringManager.set(null, "value", tenSeconds)
                    );

                    assertEquals("key is required", exception.getMessage());
                }

                @Test
                void whenValueIsNull() {
                    final IllegalArgumentException exception = assertThrows(
                            IllegalArgumentException.class,
                            () -> defaultStringManager.set("key", null, tenSeconds)
                    );

                    assertEquals("value is required", exception.getMessage());
                }

                @Test
                void whenDurationInSecondsIsZero() {
                    final long zeroDuration = 0;
                    final IllegalArgumentException exception = assertThrows(
                            IllegalArgumentException.class,
                            () -> defaultStringManager.set("key", "value", zeroDuration)
                    );

                    assertEquals("invalid expire time in 'set' command", exception.getMessage());
                }
            }

            @Test
            void shouldReplaceValueIfKeyDoesExist() {
                final String key = "key";
                final String oldValue = "value";
                final String newValue = "value2";


                when(stringsMock.get(anyString())).thenReturn(Optional.of(oldValue));
                when(stringsMock.replace(anyString(), anyString())).thenReturn(newValue);

                assertEquals("OK", defaultStringManager.set(key, newValue, tenSeconds));

                verify(stringsMock, times(1)).get(eq(key));
                verify(stringsMock, times(1)).replace(eq(key), eq(newValue));

                verify(stringsMock, never()).set(anyString(), anyString(), anyLong());
            }

            @Test
            void shouldSetValueIfKeyDoesNotExist() {
                final String newKey = "key";
                final String value = "value";

                when(stringsMock.get(anyString())).thenReturn(Optional.empty());
                when(stringsMock.set(anyString(), anyString(), anyLong())).thenReturn(value);

                assertEquals("OK", defaultStringManager.set(newKey, value, tenSeconds));

                verify(stringsMock, times(1)).get(eq(newKey));
                verify(stringsMock, times(1)).set(eq(newKey), eq(value), eq(tenSeconds));

                verify(stringsMock, never()).replace(anyString(), anyString());
            }
        }
    }

    @Nested
    class WhenCallIncrementsMethod {

        @Nested
        class ShouldThrowException {

            @Test
            void whenKeyIsNull() {

                final IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class,
                        () -> defaultStringManager.incrementsValue(null)
                );

                assertEquals("key is required", exception.getMessage());
            }


            @Test
            void whenValueIsNotNumber() {
                final String valueIsNotNumber = "It's not number";

                when(stringsMock.get(anyString())).thenReturn(Optional.of(valueIsNotNumber));

                final RedisException exception = assertThrows(
                        RedisException.class,
                        () -> defaultStringManager.incrementsValue("key")
                );

                assertEquals("value is not an integer", exception.getMessage());
            }

        }

        @Test
        void shouldSetOneWhenKeyDoesNotExist() {
            final String keyNotExist = "keyNotExist";

            when(stringsMock.get(anyString())).thenReturn(Optional.empty());
            when(stringsMock.set(anyString(), anyInt(), anyLong())).thenReturn(String.valueOf(1));

            assertEquals("1", defaultStringManager.incrementsValue(keyNotExist));

            verify(stringsMock, times(1)).get(eq(keyNotExist));
            verify(stringsMock, times(1)).set(eq(keyNotExist), eq(1), anyLong());

            verify(stringsMock, never()).replace(anyString(), anyInt());

        }

        @Test
        void shouldAddOneToValueWhenKeyExist() {
            final String keyExist = "keyExist";
            final String numberAsString = "10";

            final Integer resultExpected = 11;

            when(stringsMock.get(anyString())).thenReturn(Optional.of(numberAsString));
            when(stringsMock.replace(anyString(), anyInt())).thenReturn(String.valueOf(11));

            assertEquals(resultExpected.toString(), defaultStringManager.incrementsValue(keyExist));

            verify(stringsMock, times(1)).get(eq(keyExist));
            verify(stringsMock, times(1)).replace(eq(keyExist), eq(resultExpected));

            verify(stringsMock, never()).set(anyString(), anyInt(), anyLong());
        }
    }

    @Nested
    class WhenCallGetMethod {

        @Test
        void shouldThrowExceptionWhenKeyDoesNotExist() {
            final IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> defaultStringManager.get(null)
            );

            assertEquals("key is required", exception.getMessage());
        }

        @Test
        void shouldGetValueWhenKeyExist() {
            final String keyExist = "keyExist";
            final String value = "value";

            when(stringsMock.get(anyString())).thenReturn(Optional.of(value));

            assertEquals("value", defaultStringManager.get(keyExist));

            verify(stringsMock, times(1)).get(eq(keyExist));
        }

        @Test
        void shouldGetNilWhenKeyDoesNotExist() {
            final String keyDoesNotExist = "keyDoesNotExist";

            when(stringsMock.get(anyString())).thenReturn(Optional.empty());

            assertEquals("nil", defaultStringManager.get(keyDoesNotExist));

            verify(stringsMock, times(1)).get(eq(keyDoesNotExist));
        }
    }

    @Nested
    class WhenCallDeleteMethod {
        @Test
        void shouldThrowExceptionWhenKeyIsNull() {
            final IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> defaultStringManager.get(null)
            );

            assertEquals("key is required", exception.getMessage());
        }

        @Test
        void shouldReturnOneWhenKeyExist(){
            final String keyExist = "keyExist";
            final String value = "value";

            when(stringsMock.delete(anyString())).thenReturn(Optional.of(value));

            assertEquals("1", defaultStringManager.delete(keyExist));

            verify(stringsMock, times(1)).delete(eq(keyExist));
        }

        @Test
        void shouldReturnZeroWhenKeyDoesNotExist(){
            final String keyNotExist = "keyNotExist";

            when(stringsMock.delete(anyString())).thenReturn(Optional.empty());

            assertEquals("0", defaultStringManager.delete(keyNotExist));

            verify(stringsMock, times(1)).delete(eq(keyNotExist));
        }
    }

    @Nested
    class WhenCallSizeMethod {

        @Test
        void shouldReturnSizeOfStrings() {
            when(stringsMock.size()).thenReturn(0);

            assertEquals(0, defaultStringManager.size());

            verify(stringsMock, times(1)).size();
        }

    }
}