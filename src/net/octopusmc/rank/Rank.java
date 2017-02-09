/*
 * Copyright (c) 2016, OctopusMC and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of OctopusMC or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package net.octopusmc.rank;

public enum Rank {

    PLAYER(0),
    VIP(1),
    ELITE(2),
    LEGEND(3),
    YOUTUBE(3),
    TWITCH(3),
    BUILDER(3),
    HELPER(4),
    MODERATOR(5),
    ADMIN(6),
    DEV(6),
    OWNER(7);

    public int l;

    private Rank(int level) {
        this.l = level;
    }

    public Level getRankLevel() {
        Level r = null;

        if (l == 0) {
            return Level.PLAYER;
        }
        if (l == 1) {
            return Level.VIP;
        }
        if (l == 2) {
            return Level.ELITE;
        }
        if (l == 3) {
            return Level.LEGEND;
        }
        if (l == 4) {
            return Level.HELPER;
        }
        if (l == 5) {
            return Level.MODERATOR;
        }
        if (l == 6) {
            return Level.ADMIN;
        }
        if (l == 7) {
            return Level.OWNER;
        }

        return r;
    }

    public enum Level {
        PLAYER,
        VIP,
        ELITE,
        LEGEND,
        HELPER,
        MODERATOR,
        ADMIN,
        OWNER,
    }

}
