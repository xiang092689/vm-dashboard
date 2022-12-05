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

package com.github.shoothzj.vmdash.service;

import com.github.shoothzj.vmdash.config.VmConfig;
import com.github.shoothzj.vmdash.module.CreateDirReq;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FsService {

    private final VmConfig vmConfig;

    public FsService(@Autowired VmConfig vmConfig) {
        this.vmConfig = vmConfig;
    }

    public void mkdir(CreateDirReq createDirReq) throws IOException {
        String path = vmConfig.hostFs + createDirReq.getPath();
        Files.createDirectories(Paths.get(path));
    }

    public void appendStr(String relativePath, String str) throws IOException {
        String path = vmConfig.hostFs + relativePath;
        FileUtils.writeStringToFile(new File(path), str, StandardCharsets.UTF_8, true);
    }

    public List<String> readLines(String relativePath) throws IOException {
        String path = vmConfig.hostFs + relativePath;
        return FileUtils.readLines(new File(path), StandardCharsets.UTF_8);
    }
}
