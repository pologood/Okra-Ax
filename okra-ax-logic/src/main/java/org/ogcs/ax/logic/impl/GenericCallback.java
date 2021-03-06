package org.ogcs.ax.logic.impl;

import org.ogcs.ax.gpb3.GpbD.Request;
import org.ogcs.ax.gpb3.GpbD.Response;
import org.ogcs.GpbReplys;
import org.ogcs.app.Session;
import org.ogcs.ax.component.core.AxCallback;
import org.ogcs.ax.gpb3.OkraAx.AxError;
import org.ogcs.ax.gpb3.OkraAx.AxOutbound;

/**
 * Asynchronous operation will be invoked when remote service {@link Response} or request timeout.
 * Send message to user.
 *
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @since 1.0
 */
public class GenericCallback implements AxCallback<AxOutbound> {

    private Session session;
    private Request request;

    public GenericCallback(Session session, Request request) {
        this.session = session;
        this.request = request;
    }

    @Override
    public void run(AxOutbound msg) {
        Response response;
        if (msg.hasError()) {
            AxError error = msg.getError();
            if (error.getMsg().isEmpty()) {
                response = GpbReplys.error(request.getId(), error.getState(), error.getMsg());
            } else {
                response = GpbReplys.error(request.getId(), error.getState());
            }
        } else {
            response = GpbReplys.response(request.getId(), msg);
        }
        session.writeAndFlush(response);
    }
}
