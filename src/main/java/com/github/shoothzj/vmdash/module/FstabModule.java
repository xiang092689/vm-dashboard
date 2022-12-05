/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.shoothzj.vmdash.module;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class FstabModule {

    private String fileSystem;

    private String mountPoint;

    private String type;

    private String options;

    private String dump;

    private String paas;

    public FstabModule() {
    }

    public static List<FstabModule> parse(List<String> lines) {
        ArrayList<FstabModule> fstabModules = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("#")) {
                continue;
            }
            String[] split = line.split("\\s+");
            FstabModule fstabModule = new FstabModule();
            fstabModule.setFileSystem(split[0]);
            fstabModule.setMountPoint(split[1]);
            fstabModule.setType(split[2]);
            fstabModule.setOptions(split[3]);
            fstabModule.setDump(split[4]);
            fstabModule.setPaas(split[5]);
            fstabModules.add(fstabModule);
        }
        return fstabModules;
    }

    public String getFstabStr() {
        return fileSystem + " " + mountPoint + " " + type + " " + options + " " + dump + " " + paas;
    }
}
