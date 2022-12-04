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

package com.github.shoothzj.vmdash.config;

import com.github.shoothzj.vmdash.module.SshClientType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VmSshConfig {

    @Value("${VM_SSH_TYPE:JSCH}")
    public SshClientType sshClientType;

    @Value("${VM_SSH_HOST:localhost}")
    public String host;

    @Value("${VM_SSH_PORT:22}")
    public int port;

    @Value("${VM_SSH_USERNAME:root}")
    public String username;

    @Value("${VM_SSH_PASSWORD:")
    public String password;

}
