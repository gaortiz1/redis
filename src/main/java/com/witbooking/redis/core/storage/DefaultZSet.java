package com.witbooking.redis.core.storage;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DefaultZSet implements ZSet {

    private final TreeMap<Integer, TreeSet<String>> scores = new TreeMap<>();
    private final TreeMap<String, Integer> members = new TreeMap<>();

    public boolean add(Integer score, String member) {

        boolean wasAdded = true;

        if (members.containsKey(member)) {
            removeOldScoreIfItChanged(members.get(member), member, score);
            wasAdded = false;
        }

        scores.computeIfAbsent(score, (s) -> new TreeSet<>()).add(member);
        members.put(member, score);

        return wasAdded;
    }

    public Optional<Integer> rank(String member) {
        if (members.containsKey(member)) {
            final Integer score = members.get(member);
            return Optional.of(
                    Collections.binarySearch(getMembersSorted(), ScoreMember.of(score, member))
            );
        }
        return Optional.empty();
    }

    public List<String> range(int from, int to) {
        return rangeWithScores(from, to)
                .stream()
                .map(ScoreMember::getMember)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScoreMember> rangeWithScores(int from, int to) {
        to = Math.min(to, members.size());
        from = Math.max(from, 0);

        return getMembersSorted()
                .subList(from, to);
    }

    public int size() {
        return members.size();
    }

    private void removeOldScoreIfItChanged(Integer oldScore, String member, Integer newScore){
        if (oldScore.compareTo(newScore) != 0) {
            final TreeSet<String> oldScoreMembers = scores.computeIfAbsent(oldScore, (s) -> new TreeSet<>());
            if (oldScoreMembers.size() > 1) {
                oldScoreMembers.remove(member);
            } else {
                scores.remove(oldScore);
            }
        }
    }

    private List<ScoreMember> getMembersSorted(){
        return members.entrySet()
                .stream()
                .map(entry -> ScoreMember.of(entry.getValue(), entry.getKey()))
                .sorted()
                .collect(Collectors.toList());
    }
}


