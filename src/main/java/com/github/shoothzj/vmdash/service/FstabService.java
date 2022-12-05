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

import com.github.shoothzj.vmdash.constant.PathConst;
import com.github.shoothzj.vmdash.module.FstabModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FstabService {

    private final FsService fsService;

    public FstabService(@Autowired FsService fsService) {
        this.fsService = fsService;
    }

    public void addFstabModule(FstabModule fstabModule) throws IOException {
        String fstabStr = fstabModule.getFstabStr();
        fsService.appendStr(fstabStr, PathConst.FSTAB);
    }

    public List<FstabModule> getFstabModules() throws IOException {
        return FstabModule.parse(fsService.readLines(PathConst.FSTAB));
    }

}
