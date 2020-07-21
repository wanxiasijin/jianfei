package com.chenganrt.smartSupervision.business.electronic.okhttp;

import android.os.Handler;

import com.chenganrt.smartSupervision.business.electronic.Progress;
import com.chenganrt.smartSupervision.business.electronic.util.AppConstant;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Options;
import okio.Sink;
import okio.Timeout;


public class ProgressResponseBody extends ResponseBody {
    private ResponseBody body;
    private Handler handler;

    public ProgressResponseBody(ResponseBody body, Handler handler) {
        this.body = body;
        this.handler = handler;
    }

    @Override
    public MediaType contentType() {
        return body.contentType();
    }

    @Override
    public long contentLength() {
        return body.contentLength();
    }

    @Override
    public BufferedSource source() {
        return new ProgressBufferedSource(body.source(), contentLength());
    }

    class ProgressSink implements Sink {
        private Sink sink;
        private ProgressBufferedSource progress;

        public ProgressSink(Sink sink, ProgressBufferedSource progress) {
            this.sink = sink;
            this.progress = progress;
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            sink.write(source, byteCount);
            if (progress != null) progress.onProgress(byteCount, true);
        }

        @Override
        public void flush() throws IOException {
            sink.flush();
        }

        @Override
        public Timeout timeout() {
            return sink.timeout();
        }

        @Override
        public void close() throws IOException {
            sink.close();
        }
    }

    class ProgressBufferedSource implements BufferedSource {

        private BufferedSource source;
        private Progress p;

        public ProgressBufferedSource(BufferedSource source, long contentLength) {
            this.source = source;
            this.p = new Progress(0, contentLength, false);
        }

        public void onProgress(long readLength, boolean progress) {
            if (readLength == -1) return;
            if (progress && handler != null) {
                p.current += readLength;
                handler.obtainMessage(AppConstant.TaskState.ISRUNING, p).sendToTarget();
            }
        }

        @Override
        public Buffer buffer() {
            return source.buffer();
        }

        @Override
        public boolean exhausted() throws IOException {
            return source.exhausted();
        }

        @Override
        public void require(long byteCount) throws IOException {
            source.require(byteCount);
        }

        @Override
        public boolean request(long byteCount) throws IOException {
            return source.request(byteCount);
        }

        @Override
        public byte readByte() throws IOException {
            return source.readByte();
        }

        @Override
        public short readShort() throws IOException {
            return source.readShort();
        }

        @Override
        public short readShortLe() throws IOException {
            return source.readShortLe();
        }

        @Override
        public int readInt() throws IOException {
            return source.readInt();
        }

        @Override
        public int readIntLe() throws IOException {
            return source.readIntLe();
        }

        @Override
        public long readLong() throws IOException {
            return source.readLong();
        }

        @Override
        public long readLongLe() throws IOException {
            return source.readLongLe();
        }

        @Override
        public long readDecimalLong() throws IOException {
            return source.readDecimalLong();
        }

        @Override
        public long readHexadecimalUnsignedLong() throws IOException {
            return source.readHexadecimalUnsignedLong();
        }

        @Override
        public void skip(long byteCount) throws IOException {
            source.skip(byteCount);
        }

        @Override
        public ByteString readByteString() throws IOException {
            return source.readByteString();
        }

        @Override
        public ByteString readByteString(long byteCount) throws IOException {
            onProgress(byteCount, true);
            return source.readByteString(byteCount);
        }

        @Override
        public int select(Options options) throws IOException {
            return source.select(options);
        }

        @Override
        public byte[] readByteArray() throws IOException {
            return source.readByteArray();
        }

        @Override
        public byte[] readByteArray(long byteCount) throws IOException {
            onProgress(byteCount, true);
            return source.readByteArray(byteCount);
        }

        @Override
        public int read(byte[] sink) throws IOException {
            return source.read(sink);
        }

        @Override
        public void readFully(byte[] sink) throws IOException {
            source.readFully(sink);
        }

        @Override
        public int read(byte[] sink, int offset, int byteCount) throws IOException {
            onProgress(byteCount, true);
            return source.read(sink, offset, byteCount);
        }

        @Override
        public void readFully(Buffer sink, long byteCount) throws IOException {
            onProgress(byteCount, true);
            source.readFully(sink, byteCount);
        }

        @Override
        public long readAll(Sink sink) throws IOException {
            return source.readAll(new ProgressSink(sink, this));
        }

        @Override
        public String readUtf8() throws IOException {
            return source.readUtf8();
        }

        @Override
        public String readUtf8(long byteCount) throws IOException {
            onProgress(byteCount, true);
            return source.readUtf8(byteCount);
        }

        @Override
        public String readUtf8Line() throws IOException {
            return source.readUtf8Line();
        }

        @Override
        public String readUtf8LineStrict() throws IOException {
            return source.readUtf8LineStrict();
        }

        @Override
        public String readUtf8LineStrict(long limit) throws IOException {
            return null;
        }

//        @Override
//        public String readUtf8LineStrict(long limit) throws IOException {
//            return source.readUtf8LineStrict(limit);
//        }

        @Override
        public int readUtf8CodePoint() throws IOException {
            return source.readUtf8CodePoint();
        }

        @Override
        public String readString(Charset charset) throws IOException {
            return source.readString(charset);
        }

        @Override
        public String readString(long byteCount, Charset charset) throws IOException {
            onProgress(byteCount, true);
            return source.readString(byteCount, charset);
        }

        @Override
        public long indexOf(byte b) throws IOException {
            return source.indexOf(b);
        }

        @Override
        public long indexOf(byte b, long fromIndex) throws IOException {
            return source.indexOf(b, fromIndex);
        }

        @Override
        public long indexOf(byte b, long fromIndex, long toIndex) throws IOException {
            return 0;
        }

//        @Override
//        public long indexOf(byte b, long fromIndex, long toIndex) throws IOException {
//            return source.indexOf(b, fromIndex, toIndex);
//        }

        @Override
        public long indexOf(ByteString bytes) throws IOException {
            return source.indexOf(bytes);
        }

        @Override
        public long indexOf(ByteString bytes, long fromIndex) throws IOException {
            return source.indexOf(bytes, fromIndex);
        }

        @Override
        public long indexOfElement(ByteString targetBytes) throws IOException {
            return source.indexOfElement(targetBytes);
        }

        @Override
        public long indexOfElement(ByteString targetBytes, long fromIndex) throws IOException {
            return source.indexOfElement(targetBytes, fromIndex);
        }

        @Override
        public boolean rangeEquals(long offset, ByteString bytes) throws IOException {
            return source.rangeEquals(offset, bytes);
        }

        @Override
        public boolean rangeEquals(long offset, ByteString bytes, int bytesOffset, int byteCount) throws IOException {
            return source.rangeEquals(offset, bytes, bytesOffset, byteCount);
        }

        @Override
        public InputStream inputStream() {
            return source.inputStream();
        }

        @Override
        public long read(Buffer sink, long byteCount) throws IOException {
            onProgress(byteCount, true);
            return source.read(sink, byteCount);
        }

        @Override
        public Timeout timeout() {
            return source.timeout();
        }

        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public void close() throws IOException {
            source.close();
        }

        @Override
        public int read(ByteBuffer dst) throws IOException {
            return 0;
        }
    }
}