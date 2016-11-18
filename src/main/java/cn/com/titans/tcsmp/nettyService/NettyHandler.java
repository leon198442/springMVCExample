package cn.com.titans.tcsmp.nettyService;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.buffer.ByteBuf;
@Sharable
public class NettyHandler extends ChannelInboundHandlerAdapter {
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf data = (ByteBuf) msg;
		byte[] bs = new byte[data.readableBytes()];
		data.readBytes(bs);
		StringBuilder sb = new StringBuilder("");
		char[] chars = "0123456789ABCDEF".toCharArray();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			sb.append("0x");
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
			sb.append(",");
		}

		System.out.println("length="+bs.length+" data:"+sb);
		ctx.write(msg);// 写回数据，
	}

	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER) // flush掉所有写回的数据
				.addListener(ChannelFutureListener.CLOSE); // 当flush完成后关闭channel
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();// 捕捉异常信息
		ctx.close();// 出现异常时关闭channel
	}
}
