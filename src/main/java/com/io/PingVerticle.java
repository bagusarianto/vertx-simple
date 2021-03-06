package com.io;
/*
 * Copyright 2013 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

import java.util.Random;

/*
This is a simple Java verticle which receives `ping` messages on the event bus and sends back `pong` replies
 */
public class PingVerticle extends Verticle {

    public void start() {
        JsonObject sConfig = new JsonObject();
        sConfig.putString("web_root", "webroot").putNumber("port", 8083).putString("host", "localhost");
        container.deployModule("io.vertx~mod-web-server~2.0.0-final", sConfig);
        final EventBus eb = vertx.eventBus();

        vertx.setPeriodic(1000, new Handler<Long>() {
            @Override
            public void handle(Long timerID) {
                vertx.eventBus().publish("news-feed", "more news!");
            }
        });

    }
}
