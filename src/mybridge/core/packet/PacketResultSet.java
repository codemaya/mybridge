package mybridge.core.packet;

import mybridge.util.Buffer;

public class PacketResultSet extends Packet {
	public long fieldCount;

	@Override
	public byte[] getBytes() {
		int len = Buffer.getLCBLen(fieldCount);
		Buffer buf = new Buffer(len);
		buf.writeLCB(fieldCount);
		return buf.getBytes();
	}

	@Override
	public void putBytes(byte[] bs) {
	}

}
