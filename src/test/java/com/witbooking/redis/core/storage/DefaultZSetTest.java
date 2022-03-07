package com.witbooking.redis.core.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DefaultZSetTest {

    private DefaultZSet zset;

    @BeforeEach
    void setUp() {
        zset = new DefaultZSet();
    }

    @Nested
    class AddMethod {

        @Test
        void shouldAddScoreAndMemberWhenBothNotExist() {
            final Integer scoreNotExist = 1;
            final String memberNotExist = "member";

            zset.add(scoreNotExist, memberNotExist);


            assertEquals(1, zset.size());
            assertEquals(ZSet.ScoreMember.of(scoreNotExist, memberNotExist), zset.rangeWithScores(0, 1).get(0));
        }

        @Test
        void shouldNotAddScoreAndMemberWhenBothExist() {
            final Integer scoreNotExist = 1;
            final String memberNotExist = "member";

            zset.add(scoreNotExist, memberNotExist);
            zset.add(scoreNotExist, memberNotExist);

            assertEquals(1, zset.size());
            assertEquals(ZSet.ScoreMember.of(scoreNotExist, memberNotExist), zset.rangeWithScores(0, 1).get(0));
        }

        @Test
        void shouldAddNewMemberWhenScoreExists() {
            final Integer scoreExists = 1;

            zset.add(scoreExists, "member");
            zset.add(scoreExists, "member2");

            assertEquals(2, zset.size());
            List<ZSet.ScoreMember> range = zset.rangeWithScores(0, 2);
            assertEquals(ZSet.ScoreMember.of(scoreExists, "member"), range.get(0));
            assertEquals(ZSet.ScoreMember.of(scoreExists, "member2"), range.get(1));
        }

        @Test
        void shouldChangeScoreWhenMemberExists() {
            final Integer oldScore = 1;
            final Integer newScore = 2;
            final String member = "member";

            zset.add(oldScore, member);
            zset.add(newScore, member);

            assertEquals(1, zset.size());
            assertEquals(ZSet.ScoreMember.of(newScore, member), zset.rangeWithScores(0, 1).get(0));
        }

    }

    @Nested
    class RankMethod {

        @Test
        void shouldGetIndexWhenMemberExists(){
            zset.add(1, "one");
            zset.add(2, "two");
            zset.add(3, "three");

            assertEquals(2, zset.rank("three").get());
            assertEquals(1, zset.rank("two").get());
            assertEquals(0, zset.rank("one").get());
        }

        @Test
        void shouldNotGetIndexWhenMemberNotExists(){
            zset.add(3, "one");
            zset.add(6, "two");

            assertFalse(zset.rank("four").isPresent());
        }
    }

    @Nested
    class SizeMethod {

        @Test
        void shouldGetSizeWhenScoreAndMemberExist() {
            zset.add(1, "one");
            zset.add(2, "two");
            zset.add(3, "two");

            assertEquals(2, zset.size());
        }

        @Test
        void shouldBeSizeEqZeroWhenScoreAndMemberNotExist() {
            assertEquals(0, zset.size());
        }
    }

}