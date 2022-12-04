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

import com.github.shoothzj.vmdash.constant.ShellConst;
import com.github.shoothzj.vmdash.module.ShellResult;

public interface IShellService {
    default String[] execCommandIgnoreFail(String command) throws Exception {
        final ShellResult sshShellResult = execCommand(command);
        if (sshShellResult.getReturnCode() != 0) {
            throw new Exception(String.format("exec shell exception, command is %s return code is %d error is %s",
                    command, sshShellResult.getReturnCode(), sshShellResult.getErrorContent()));
        }
        return sshShellResult.getOutputContent().split(ShellConst.LINE_SPLIT);
    }
    ShellResult execCommand(String command) throws Exception;
}
