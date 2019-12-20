package io.slinkydeveloper.vertx.scratch;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class ScratchEventBusVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Router router = Router.router(vertx);

        SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
        sockJSHandler.bridge(
                new BridgeOptions()
                        .addInboundPermitted(new PermittedOptions().setAddressRegex("scratch\\..*"))
                        .addOutboundPermitted(new PermittedOptions().setAddressRegex("scratch\\..*"))
            );

        router.route("/eventbus/*").handler(sockJSHandler);

        router.route().handler(StaticHandler.create());

        vertx
            .createHttpServer()
            .requestHandler(router)
            .listen(8080, ar -> {
                if (ar.succeeded()) startPromise.complete();
                else startPromise.fail(ar.cause());
            });
    }

}
