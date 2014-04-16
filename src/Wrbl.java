/**
 * Created with IntelliJ IDEA.
 * User: myan
 * Date: 14-3-28
 * Time: 下午8:36
 * To change this template use File | Settings | File Templates.
 */

import dbutils.DatabaseAccess;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.handler.codec.*;

import java.io.IOException;
import java.util.*;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.ByteToMessageDecoder;

import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import server.*;

class CommandInput extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if ((in != null) && (in.readableBytes() > 0)) {
            byte[] b = new byte[in.readableBytes()];
            in.readBytes(b);
            GenericMessage msg = GlobalGson.sharedGson().fromJson(new String(b), GenericMessage.class);
            out.add(msg);
        }
    }
}

class CommandExecutor extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg == null)
            return;

        GenericMessage theMsg = (GenericMessage)msg;
        int cmd = Integer.parseInt(theMsg.cmd);
        if (CommandType.check(cmd) == false)
            return;

        ReplyMessage reply = CommandHandler.sharedHandler().call(cmd, theMsg.data);
        if (reply != null) {
            GenericMessage d = new GenericMessage(
                    String.valueOf(CommandType.cmdReplyMessage),
                            GlobalGson.sharedGson().toJson(reply));
            ctx.channel().writeAndFlush(GlobalGson.sharedGson().toJson(d));
        }
    }
}


class ServerHandler extends MessageToByteEncoder<String> {
    @Override
    public void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        out.writeBytes(msg.getBytes());
    }
}

class DiscardServer {
    private int port;

    public DiscardServer(int port) {
        this.port = port;

        CommandHandler.sharedHandler().register(CommandType.cmdLogin, new LoginMessage());
        //CommandHandler.sharedHandler().register(CommandType.cmdRegister, new RegisterMessage());
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(DiscardServer.class, LogLevel.INFO));
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(256, 0, 2, 0, 2));  // inbound
                            ch.pipeline().addLast(new CommandInput());                                 // inbound
                            ch.pipeline().addLast(new CommandExecutor());                              // inbound

                            ch.pipeline().addLast(new LengthFieldPrepender(2));  // outbound
                            ch.pipeline().addLast(new ServerHandler());          // outbound

                            //ch.pipeline().addLast(new LoggingHandler(DiscardServer.class, LogLevel.INFO));   // inbound & outbound

                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}

public class Wrbl {

    /**
     * @param args args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        try {
            new DiscardServer(8090).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


