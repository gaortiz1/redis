package com.witbooking.redis.core.template;

import com.witbooking.redis.core.storage.SortedSet;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultSortedSetTemplateTest {

    @InjectMocks
    private DefaultSortedSetTemplate defaultSortedSetTemplate;

    @Mock
    private SortedSet sortedSetMock;

    @Nested
    class CallAddMethod {

        @Nested
        class ShouldThrowException {

            @Test
            void whenKeyIsNull() {
                final IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class,
                        () -> defaultSortedSetTemplate.add(null, 1, "vale")
                );

                assertEquals("key is required", exception.getMessage());
            }

            @Test
            void whenScoreIsNull() {
                final IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class,
                        () -> defaultSortedSetTemplate.add("key", null, "vale")
                );

                assertEquals("score is required", exception.getMessage());
            }

            @Test
            void whenMemberIsNull() {
                final IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class,
                        () -> defaultSortedSetTemplate.add("key", 1, null)
                );

                assertEquals("member is required", exception.getMessage());
            }
        }

        @Nested
        class WhenKeyNotExist {

            @Test
            void shouldAddScoreAndMemberThenReturnOne(){

                when(sortedSetMock.add(anyString(), anyInt(), anyString())).thenReturn(true);

                assertEquals("1", defaultSortedSetTemplate.add("keyNotExist", 1, "value"));

                verify(sortedSetMock, Mockito.times(1)).add(eq("keyNotExist"), eq(1), eq("value"));
            }

            @Test
            void shouldUpdateScoreAndMemberWhenItExistsThenReturnZero(){
                when(sortedSetMock.add(anyString(), anyInt(), anyString())).thenReturn(false);

                assertEquals("0", defaultSortedSetTemplate.add("keyExist", 1, "value"));

                verify(sortedSetMock, Mockito.times(1)).add(eq("keyExist"), eq(1), eq("value"));
            }
        }
    }

    @Nested
    class CallRankMethod {

        @Nested
        class ShouldThrowException {

            @Test
            void whenKeyIsNull() {
                final IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class,
                        () -> defaultSortedSetTemplate.rank(null,"vale")
                );

                assertEquals("key is required", exception.getMessage());
            }

            @Test
            void whenMemberIsNull() {
                final IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class,
                        () -> defaultSortedSetTemplate.rank("key",null)
                );

                assertEquals("member is required", exception.getMessage());
            }
        }

        @Test
        void shouldReturnsRankOfMemberWhenItExists() {
            when(sortedSetMock.rank(anyString(), anyString())).thenReturn(Optional.of(4));

            assertEquals("4", defaultSortedSetTemplate.rank("key", "member"));

            verify(sortedSetMock, Mockito.times(1)).rank(eq("key"), eq("member"));
        }

        @Test
        void shouldReturnsNillWhenRankOfMemberNotExists() {
            when(sortedSetMock.rank(anyString(), anyString())).thenReturn(Optional.empty());

            assertEquals("nil", defaultSortedSetTemplate.rank("key", "member"));

            verify(sortedSetMock, Mockito.times(1)).rank(eq("key"), eq("member"));
        }
    }

    @Nested
    class CallRangeMethod {

        @Nested
        class ShouldThrowException {

            @Test
            void whenKeyIsNull() {
                final IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class,
                        () -> defaultSortedSetTemplate.range(null,1, 2)
                );

                assertEquals("key is required", exception.getMessage());
            }

            @Test
            void whenStartIsNull() {
                final IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class,
                        () -> defaultSortedSetTemplate.range("key",null, 2)
                );

                assertEquals("start is required", exception.getMessage());
            }

            @Test
            void whenStopIsNull() {
                final IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class,
                        () -> defaultSortedSetTemplate.range("key",1, null)
                );

                assertEquals("stop is required", exception.getMessage());
            }

            @Test
            void whenStartIsLessThanZero() {
                final IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class,
                        () -> defaultSortedSetTemplate.range("key",-1, 2)
                );

                assertEquals("start must be greater than 0", exception.getMessage());
            }

            @Test
            void whenSopIsLessThanZero() {
                final IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class,
                        () -> defaultSortedSetTemplate.range("key",1, -1)
                );

                assertEquals("stop must be greater than 0", exception.getMessage());
            }

            @Test
            void whenStartIsGreaterThanStop() {
                final IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class,
                        () -> defaultSortedSetTemplate.range("key",10, 0)
                );

                assertEquals("start must be less than stop", exception.getMessage());
            }
        }
    }
}