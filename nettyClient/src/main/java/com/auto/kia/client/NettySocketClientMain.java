package com.auto.kia.client;

public class NettySocketClientMain {

	public static void main(String[] args) {
		
		System.out.println("----- netty clinet -----");
		NettySocketClient client = new NettySocketClient("127.0.0.1", 5010, "message hi");
		
		client.run();

	}

}
