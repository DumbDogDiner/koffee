/**
 * Copyright (c) 2020 DumbDogDiner <dumbdogdiner.com>. All rights reserved.
 * Licensed under the GPLv3 license, see LICENSE for more information...
 */
package com.dumbdogdiner.stickyapi.common.util;

import com.dumbdogdiner.stickyapi.common.util.IPUtil;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class IPUtilTest {

    // IPv4 Tests

    @Test
    public void testCheckValidRanges4() {
        assertTrue(IPUtil.checkRange("1.1.1.1", "1.1.1.1", "32"));
        assertFalse(IPUtil.checkRange("1.1.1.1", "1.1.1.2", "32"));
    }

    @Test
    public void testCheckSlashOneRange4() {
        assertTrue(IPUtil.checkRange("0.0.0.0", "127.255.255.254", "1"));
        assertTrue(IPUtil.checkRange("0.0.0.0", "127.255.255.255", "1"));

        assertFalse(IPUtil.checkRange("0.0.0.0", "128.0.0.1", "1"));
    }
    
    @Test
    public void testCheckSlashZeroRange4() {
        assertTrue(IPUtil.checkRange("0.0.0.0", "0.0.0.0", "0"));
        assertTrue(IPUtil.checkRange("0.0.0.0", "255.255.255.255", "0"));
    }
    
    // IPv6 Tests

    @Test
    public void testCheckValidRanges6Full() {
        assertTrue(IPUtil.checkRange("0000:0000:0000:0000:0000:0000:0000:0001", "0000:0000:0000:0000:0000:0000:0000:0001", "128"));
        assertFalse(IPUtil.checkRange("0000:0000:0000:0000:0000:0000:0000:0001", "0000:0000:0000:0000:0000:0000:0000:0002", "128"));
    }

    @Test
    public void testCheckValidRanges6Simple() {
        assertTrue(IPUtil.checkRange("::1", "::1", "128"));
        assertFalse(IPUtil.checkRange("::1", "::2", "128"));
    }

    @Test
    public void testCheckSlashOneRange6() {
        assertTrue(IPUtil.checkRange("::1", "7fff:ffff:ffff:ffff:ffff:ffff:ffff:ffff", "1"));

        assertFalse(IPUtil.checkRange("::1", "8000::", "1"));
    }

    @Test
    public void testCheckSlashZeroRange6() {
        assertTrue(IPUtil.checkRange("::1", "::1", "0"));
        assertTrue(IPUtil.checkRange("::1", "ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff", "0"));
    }

    // Other Tests

    @Test
    public void testCheckInvalidArguments() {
        assertFalse(IPUtil.checkRange("OwO", "1.1.1.1", "32"));
        assertFalse(IPUtil.checkRange("1.1.1.1", "OwO", "32"));
        assertFalse(IPUtil.checkRange("1.1.1.1", "1.1.1.1", "OwO"));
    }
}
