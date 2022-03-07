package com.witbooking.redis.core.storage;

import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface ZSet {

    boolean add(Integer score, String member);

    Optional<Integer> rank(String member);

    List<String> range(int from, int to);

    List<ScoreMember> rangeWithScores(int from, int to);

    int size();

    @Data
    class ScoreMember implements Comparable<ScoreMember> {

        private final Integer score;
        private final String member;

        public ScoreMember(Integer score, String member) {
            this.score = score;
            this.member = member;
        }

        @Override
        public int compareTo(ScoreMember scoreMember) {
            return Comparator.comparing(ScoreMember::getScore)
                    .thenComparing(ScoreMember::getMember)
                    .compare(this, scoreMember);
        }

        public static ScoreMember of(Integer score, String member) {
            return new ScoreMember(score, member);
        }
    }

}
