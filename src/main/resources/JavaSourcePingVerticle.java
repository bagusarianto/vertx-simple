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
 *
 */

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

import java.util.Random;

/*
 * This is a simple Java *source* verticle which receives `ping` messages on the event bus and sends back `pong`
 * replies.
 *
 * Note that we don't precompile this - Vert.x can do this on the fly when it's run
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class JavaSourcePingVerticle extends Verticle {

    public void start() {

        JsonObject sConfig = new JsonObject();
        sConfig.putString("web_root", "webroot")
                .putNumber("port", 8083)
                .putString("host", "localhost")
                .putBoolean("bridge", true)
                .putArray("inbound_permitted", new JsonArray().addObject(new JsonObject().putString("address", "news.feed")))
                .putArray("outbound_permitted", new JsonArray().addObject(new JsonObject().putString("address", "news.feed")));
        container.deployModule("io.vertx~mod-web-server~2.0.0-final", sConfig);
        EventBus eb = vertx.eventBus();
        vertx.setPeriodic(1000, new Handler<Long>() {
            @Override
            public void handle(Long timerID) {
                Random random = new Random();
                vertx.eventBus().publish("news.feed", "halo from server " + random.nextInt(100));
            }
        });

    }
}
