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

import com.github.shoothzj.vmdash.config.VmSshConfig;
import com.github.shoothzj.vmdash.module.ShellResult;
import com.github.shoothzj.vmdash.util.CommonUtil;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class JschShellService implements IShellService {

    @Autowired
    private VmSshConfig sshConfig;

    @Override
    public ShellResult execCmd(String command) throws Exception {
        final ShellResult shellResult = new ShellResult();
        Session session = null;
        ChannelExec channel = null;
        try {
            session = new JSch().getSession(sshConfig.username, sshConfig.host, sshConfig.port);
            session.setPassword(sshConfig.password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errStream = new ByteArrayOutputStream();
            channel.setOutputStream(outputStream);
            channel.setErrStream(errStream);
            channel.connect();
            while (!channel.isClosed()) {
                CommonUtil.sleep(TimeUnit.MILLISECONDS, 500);
            }
            shellResult.setOutputContent(outputStream.toString(StandardCharsets.UTF_8));
            shellResult.setErrorContent(errStream.toString(StandardCharsets.UTF_8));
            shellResult.setReturnCode(channel.getExitStatus());
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
        return shellResult;
    }

}
