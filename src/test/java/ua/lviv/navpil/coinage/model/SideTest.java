package ua.lviv.navpil.coinage.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SideTest {

    @Test
    public void testFlip() throws Exception {
        assertEquals(Side.HEADS, Side.TAILS.flip());
        assertEquals(Side.TAILS, Side.HEADS.flip());

        for (Side side : Side.values()) {
            assertEquals(side, side.flip().flip());
        }
    }
}
