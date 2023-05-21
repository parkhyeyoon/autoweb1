package com.auto.kia.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettySocketClient {
    private String msg;
    private String host;
    private int port;
    
    public NettySocketClient(String host, int port, String msg){
        this.msg = msg;
        this.host = host;
        this.port = port;
    }
    /*
     * ChannelFuture : I/O 처리가 완료 되었는지 확인 하고 결과를 검색
     *     ddListener()     : 작업 리스너를 등록
     *	   removeListener() : 작업 리스너를 제거
     *	   await()          : I/O 작업이 완료 되기를 대기함.
     *	   sync()           : I/O 작업이 끝날 때까지 대기 했다가 실패 하면 실패 원인을 반환함.
     *
     * EventLoop     : 채널에 등록된 모든 I/O 작업을 처리하는 인터페이스
     * 	   NioEventLoop
     *     SingleThreadEventLoop 
     *     
     * ChannelHandler : 실제 이벤트 처리를 하거나 다음 처리기로 전달하는 기능, 실제로 비즈니스 로직에 의해 처리되는 부분.
     *     ChannelInboundHandler
     *     ChannelOutboundHandler
     *     
     * ChannelPipeline : 네티의 I/O 이벤트가 구동되는 순서
     *
     */
    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        System.out.println("----- netty clinet run method -----");
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new NettySocketClientHandler(msg));
                }
            });
            System.out.println("----- netty clinet run method inner-----");
            //client connect
            try {
                ChannelFuture f = b.connect(host, port).sync();
                f.channel().closeFuture().sync();
            System.out.println("----- netty clinet run method try -----");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            workerGroup.shutdownGracefully();
        }
    }
}
