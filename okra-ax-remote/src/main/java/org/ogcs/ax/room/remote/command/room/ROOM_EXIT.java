package org.ogcs.ax.room.remote.command.room;

import org.ogcs.ax.room.module.Room;
import org.ogcs.ax.room.remote.RemoteCommand;
import org.ogcs.gpb.generated.GpbRoom.ReqExit;
import org.ogcs.gpb.generated.GpbRoom.ResExit;
import org.ogcs.app.Session;
import org.ogcs.ax.utilities.AxReplys;
import org.ogcs.ax.gpb3.OkraAx.AxInbound;

/**
 * 退出房间
 *
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/5
 */
public class ROOM_EXIT extends RemoteCommand {
    @Override
    public void execute(Session session, AxInbound inbound) throws Exception {
        ReqExit reqExit = ReqExit.parseFrom(inbound.getData());

        Room room = roomManager.get(reqExit.getRoomId());
        if (room == null) {
            session.writeAndFlush(
                    AxReplys.error(inbound.getRid(), -1)
            );
            return;
        }
        session.writeAndFlush(
                AxReplys.axOutbound(inbound.getRid(),
                        ResExit.getDefaultInstance(),
                        inbound.getSource()
                )
        );
        // 退出房间
        room.exit(inbound.getSource());
    }
}
