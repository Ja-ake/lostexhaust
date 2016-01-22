/*
 * Asphalt - A simple carpool matching service
 * Copyright (c) 2015 Jake Springer
 *
 * This file is part of Asphalt.
 *
 * Asphalt is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * Asphalt is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Asphalt.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jakespringer.lostexhaust;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;
import com.jakespringer.lostexhaust.error.InvalidConfigurationException;
import com.jakespringer.lostexhaust.user.Account;
import com.jakespringer.lostexhaust.user.AccountDbService;
import com.jakespringer.lostexhaust.user.LeAccountService;

public class LeService {
    private static AsphaltConfiguration configuration;
    
    public static void initialize() {
        Reader reader = null;
        try {
            reader = new FileReader(new File("conf", "asphalt.yaml"));
            Yaml yaml = new Yaml();
            configuration = new AsphaltConfiguration((Map<?, ?>) yaml.load(reader));
            reader.close();
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        
        List<Account> accList = AccountDbService.getInstance().getAllAccounts();
        for (Account a : accList) {
            LeAccountService.register(a);
        }
    }
    
    public static AsphaltConfiguration getConfiguration() {
        return configuration;
    }
    
    public static String fetchUrl(String urlName) throws IOException {
        StringBuilder b = new StringBuilder();
        URL url = new URL(urlName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;

        while ((line = reader.readLine()) != null) {
            b.append(line);
        }
        reader.close();
        
        return b.toString();
    }
}
