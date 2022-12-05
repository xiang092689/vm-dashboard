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
import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.channel.Channel;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MinaShellService implements IShellService {

    private VmSshConfig sshConfig;

    @Override
    public ShellResult execCmd(String command) throws Exception {
        final ShellResult shellResult = new ShellResult();
        final SshClient sshClient = SshClient.setUpDefaultClient();
        sshClient.start();
        try (ClientSession session = sshClient.connect(sshConfig.username, sshConfig.host, sshConfig.port)
                .verify(5_000, TimeUnit.MILLISECONDS).getClientSession()) {
            session.addPasswordIdentity(sshConfig.password);
            session.auth().verify(5_000, TimeUnit.MILLISECONDS);
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 ByteArrayOutputStream errStream = new ByteArrayOutputStream();
                 ClientChannel channel = session.createChannel(Channel.CHANNEL_EXEC, command)) {
                channel.setOut(outputStream);
                channel.setErr(errStream);
                try {
                    channel.open().verify(5, TimeUnit.SECONDS);
                    channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 5_000);
                    shellResult.setOutputContent(outputStream.toString(StandardCharsets.UTF_8));
                    shellResult.setErrorContent(errStream.toString(StandardCharsets.UTF_8));
                } finally {
                    channel.close(false);
                }
            }
        }
        return shellResult;
    }

}
