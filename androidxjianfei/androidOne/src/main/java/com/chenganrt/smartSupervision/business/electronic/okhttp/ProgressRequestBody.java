package com.chenganrt.smartSupervision.business.electronic.okhttp;

import android.os.Handler;

import com.chenganrt.smartSupervision.business.electronic.Progress;
import com.chenganrt.smartSupervision.business.electronic.util.AppConstant;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.Source;
import okio.Timeout;


public class ProgressRequestBody extends RequestBody {

    private RequestBody body;
    private Handler handler;


    public ProgressRequestBody(RequestBody body, Handler handler) {
        this.body = body;
        this.handler = handler;
    }

    @Override
    public MediaType contentType() {
        return body.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return body.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        ProgressBufferedSink bufferedSink = new ProgressBufferedSink(sink, contentLength());
        body.writeTo(bufferedSink);
    }

    private class ProgressSource implements Source {
        private Source source;
        private ProgressBufferedSink progress;

        private ProgressSource(Source source, ProgressBufferedSink sink) {
            this.source = source;
            this.progress = sink;
        }

        @Override
        public long read(Buffer sink, long byteCount) throws IOException {
            long count = source.read(sink, byteCount);
            if (progress != null) progress.onProgress(count, true);
            return count;
        }

        @Override
        public Timeout timeout() {
            return source.timeout();
        }

        @Override
        public void close() throws IOException {
            source.close();
        }
    }

    private class ProgressBufferedSink implements BufferedSink {
        private BufferedSink sink;
        private Progress p;

        private ProgressBufferedSink(BufferedSink sink, long contentLength) {
            this.sink = sink;
            this.p = new Progress(0, contentLength, false);
        }

        private void onProgress(long writeLength, boolean progress) {
            if (writeLength == -1) return;
            if (progress && handler != null) {
                p.current += writeLength;
                handler.obtainMessage(AppConstant.TaskState.ISRUNING, p).sendToTarget();
            }
        }

        @Override
        public Buffer buffer() {
            return sink.buffer();
        }

        @Override
        public BufferedSink write(ByteString byteString) throws IOException {
            onProgress(byteString == null ? 0L : byteString.size(), false);
            return sink.write(byteString);
        }

        @Override
        public BufferedSink write(byte[] source) throws IOException {
            onProgress(source == null ? 0L : source.length, false);
            return sink.write(source);
        }

        @Override
        public BufferedSink write(byte[] source, int offset, int byteCount) throws IOException {
            onProgress(byteCount, false);
            return sink.write(source, offset, byteCount);
        }

        @Override
        public long writeAll(Source source) throws IOException {
            return sink.writeAll(new ProgressSource(source, this));
        }

        @Override
        public BufferedSink write(Source source, long byteCount) throws IOException {
            onProgress(byteCount, false);
            return sink.write(source, byteCount);
        }

        @Override
        public BufferedSink writeUtf8(String string) throws IOException {
            onProgress(string == null ? 0L : string.length(), false);
            return sink.writeUtf8(string);
        }

        @Override
        public BufferedSink writeUtf8(String string, int beginIndex, int endIndex) throws IOException {
            onProgress(beginIndex - endIndex, false);
            return sink.writeUtf8(string, beginIndex, endIndex);
        }

        @Override
        public BufferedSink writeUtf8CodePoint(int codePoint) throws IOException {
            onProgress(1, false);
            return sink.writeUtf8CodePoint(codePoint);
        }

        @Override
        public BufferedSink writeString(String string, Charset charset) throws IOException {
            onProgress(string == null ? 0L : string.length(), false);
            return sink.writeString(string, charset);
        }

        @Override
        public BufferedSink writeString(String string, int beginIndex, int endIndex, Charset charset) throws IOException {
            onProgress(beginIndex - endIndex, false);
            return sink.writeString(string, beginIndex, endIndex, charset);
        }

        @Override
        public BufferedSink writeByte(int b) throws IOException {
            onProgress(1, false);
            return sink.writeByte(b);
        }

        @Override
        public BufferedSink writeShort(int s) throws IOException {
            onProgress(1, false);
            return sink.writeShort(s);
        }

        @Override
        public BufferedSink writeShortLe(int s) throws IOException {
            onProgress(1, false);
            return sink.writeShortLe(s);
        }

        @Override
        public BufferedSink writeInt(int i) throws IOException {
            onProgress(1, false);
            return sink.writeInt(i);
        }

        @Override
        public BufferedSink writeIntLe(int i) throws IOException {
            onProgress(1, false);
            return sink.writeIntLe(i);
        }

        @Override
        public BufferedSink writeLong(long v) throws IOException {
            onProgress(1, false);
            return sink.writeLong(v);
        }

        @Override
        public BufferedSink writeLongLe(long v) throws IOException {
            onProgress(1, false);
            return sink.writeLongLe(v);
        }

        @Override
        public BufferedSink writeDecimalLong(long v) throws IOException {
            onProgress(1, false);
            return sink.writeDecimalLong(v);
        }

        @Override
        public BufferedSink writeHexadecimalUnsignedLong(long v) throws IOException {
            onProgress(1, false);
            return sink.writeHexadecimalUnsignedLong(v);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            onProgress(byteCount, false);
            sink.write(source, byteCount);
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
        public boolean isOpen() {
            return false;
        }

        @Override
        public void close() throws IOException {
            sink.close();
        }

        @Override
        public BufferedSink emit() throws IOException {
            return sink.emit();
        }

        @Override
        public BufferedSink emitCompleteSegments() throws IOException {
            return sink.emitCompleteSegments();
        }

        @Override
        public OutputStream outputStream() {
            return sink.outputStream();
        }

        @Override
        public int write(ByteBuffer src) throws IOException {
            return 0;
        }
    }
}
