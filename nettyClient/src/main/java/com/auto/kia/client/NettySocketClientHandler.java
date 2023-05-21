package com.auto.kia.client;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettySocketClientHandler extends ChannelInboundHandlerAdapter {

	private String msg;
	
	public NettySocketClientHandler(String msg) {
		this.msg = msg;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf messageBuffer = Unpooled.buffer();
		messageBuffer.writeBytes(msg.getBytes());
	
		ctx.writeAndFlush(messageBuffer);
	
		System.out.println("Client : send message {" + msg + "}");
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String rcvMsg = ((ByteBuf) msg).toString(Charset.defaultCharset());
		System.out.println("Client : receive message {" + ((ByteBuf) msg).toString(Charset.defaultCharset()) +"}");

		if(rcvMsg.equals("ACK")) {
			System.out.println("자료 전송 정상 처리됨");
		}else if(rcvMsg.equals("NAK")) {
			System.out.println("자료 전송 실패 처리됨");
		}
		
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.close();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println(cause);
		ctx.close();
	}
}
