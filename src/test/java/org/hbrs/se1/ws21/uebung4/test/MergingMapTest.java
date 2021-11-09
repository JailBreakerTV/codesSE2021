package org.hbrs.se1.ws21.uebung4.test;

import org.hbrs.se1.ws21.uebung4.util.MergingMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class MergingMapTest {
    private MergingMap<String> userNames;

    @BeforeEach
    public void init() {
        this.userNames = new MergingMap<>();
    }

    @Test
    public void testMerging() {
        assertEquals(0, this.userNames.size());
        this.userNames.put("user_name", "ABC");
        assertEquals(1, this.userNames.size());
        assertEquals("ABC", this.userNames.get("user_name"));
        this.userNames.put("user_name", "DEF");
        assertEquals(1, this.userNames.size());
        assertInstanceOf(Collection.class, this.userNames.get("user_name"));
        this.userNames.put("user_name", "GHI");
        assertEquals(1, this.userNames.size());
        assertInstanceOf(Collection.class, this.userNames.get("user_name"));
        final Collection<Object> internUserNames = this.userNames.getCollection("user_name");
        assertNotNull(internUserNames);
        assertEquals(3, internUserNames.size());
    }
}
