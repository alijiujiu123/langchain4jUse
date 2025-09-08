package org.example.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MathUtilTest {

    @Test
    void testAddNormalNumbers() {
        // 测试正常加法
        assertEquals(5, MathUtil.add(2, 3));
        assertEquals(0, MathUtil.add(-2, 2));
        assertEquals(-5, MathUtil.add(-2, -3));
    }

    @Test
    void testAddWithZero() {
        // 测试与0相加
        assertEquals(10, MathUtil.add(10, 0));
        assertEquals(-5, MathUtil.add(0, -5));
        assertEquals(0, MathUtil.add(0, 0));
    }

    @Test
    void testAddMaxValues() {
        // 测试边界值（但不溢出）
        assertEquals(Integer.MAX_VALUE - 1, MathUtil.add(Integer.MAX_VALUE - 2, 1));
        assertEquals(Integer.MIN_VALUE + 1, MathUtil.add(Integer.MIN_VALUE + 2, -1));
    }

    @Test
    void testAddOverflowThrowsException() {
        // 测试整数溢出应该抛出异常
        assertThrows(ArithmeticException.class, () -> {
            MathUtil.add(Integer.MAX_VALUE, 1);
        });

        assertThrows(ArithmeticException.class, () -> {
            MathUtil.add(Integer.MAX_VALUE, Integer.MAX_VALUE);
        });
    }

    @Test
    void testAddUnderflowThrowsException() {
        // 测试整数下溢应该抛出异常
        assertThrows(ArithmeticException.class, () -> {
            MathUtil.add(Integer.MIN_VALUE, -1);
        });

        assertThrows(ArithmeticException.class, () -> {
            MathUtil.add(Integer.MIN_VALUE, Integer.MIN_VALUE);
        });
    }
}