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

import com.github.shoothzj.vmdash.module.ProcModule;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ProcShellService implements IProcService {

    @Autowired
    private IShellService shellService;

    @Override
    public List<ProcModule> listProc() throws Exception {
        final String[] outputArray = shellService.execCommandIgnoreFail("ps -ef");
        List<ProcModule> procModuleList = new ArrayList<>();
        for (int i = 1; i < outputArray.length; i++) {
            String procLine = outputArray[i];
            final String[] split = procLine.split("\\s+");
            procModuleList.add(new ProcModule(split[0], Integer.parseInt(split[1])));
        }
        return procModuleList;
    }
}
