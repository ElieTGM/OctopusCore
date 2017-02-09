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
package net.octopusmc.language;

import net.octopusmc.OctoCore;
import net.octopusmc.player.OctopusPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LanguageManager implements ILanguageManager {

    @Override
    public Language getLanguage(OctopusPlayer player) {
        Language res = null;
        ResultSet result = OctoCore.getMainSQLConnection().executeQuery("SELECT * FROM `Account` WHERE playername='" + player.getUsername() + "'", false);
        try {
            if (result.next()) {
                for (Language r : Language.values()) {
                    if (r.toString().toLowerCase().equalsIgnoreCase(result.getString("language"))) {
                        res = r;
                    }
                }
            }
        } catch (SQLException e) {
            res = Language.ENGLISH;
        }
        return res;
    }

    @Override
    public void setLanguage(OctopusPlayer player, Language language) {
        String languageString = language.toString().toLowerCase();
        languageString = "" + Character.toUpperCase(languageString.charAt(0)) + languageString.substring(1, languageString.length());

        OctoCore.getMainSQLConnection().executeUpdate("UPDATE `Account` SET language='" + languageString + "' WHERE playername='" + player.getUsername() + "'");
    }

    @Override
    public String getPrefix(Language language) {
        String prefix = "";

        if (language == Language.ENGLISH) {
            return "&b&l[&1ENGLISH&b&l]";
        }
        if (language == Language.FRENCH) {
            return "&4&l[&cFRENCH&4&l]";
        }
        if (language == Language.ARABIC) {
            return "&8&l[&7ARABIC&8&l]";
        }

        return prefix;
    }

}
