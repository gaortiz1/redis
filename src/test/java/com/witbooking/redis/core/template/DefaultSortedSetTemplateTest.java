package com.witbooking.redis.core.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultSortedSetTemplateTest {
    /*

    private DefaultSortedSetTemplate sortedSetMangerDefault;

    @BeforeEach
    public void setUp() {
        sortedSetMangerDefault = new DefaultSortedSetTemplate();
    }

    @Test
    void add() {
        assertEquals("1", sortedSetMangerDefault.add("mykey", 1, "member"));
        assertEquals("0", sortedSetMangerDefault.add("mykey", 1, "member"));
        assertEquals("1", sortedSetMangerDefault.add("mykey", 2, "member"));
        assertEquals(1, sortedSetMangerDefault.sizeByKey("mykey"));

        assertEquals("1", sortedSetMangerDefault.add("mykey", 1, "another member"));
        assertEquals(2, sortedSetMangerDefault.sizeByKey("mykey"));

        assertEquals("1", sortedSetMangerDefault.add("mykey", 1, "another member 2"));
        assertEquals(3, sortedSetMangerDefault.sizeByKey("mykey"));
    }


    @Test
    void add1() {

    }

    @Test
    void size() {

        assertEquals(0, sortedSetMangerDefault.sizeByKey("noExistKey"));

        sortedSetMangerDefault.add("mykey", 1, "member");
        assertEquals(1, sortedSetMangerDefault.sizeByKey("mykey"));
    }

    @Test
    void rank() {

        //tailSet(E).size()

        assertEquals("1", sortedSetMangerDefault.add("mykey", 10, "1"));
        assertEquals("1", sortedSetMangerDefault.add("mykey", 9, "2"));
        assertEquals(0, sortedSetMangerDefault.rank("mykey", "1"));
    }

    //https://stackoverflow.com/questions/23378906/is-there-a-java-data-structure-equivalent-to-redis-sorted-sets-zset
    //https://jameshfisher.com/2018/04/22/redis-sorted-set/#:~:text=Redis%20provides%20a%20data%20structure,smallest%20to%20the%20greatest%20score.

     */
}