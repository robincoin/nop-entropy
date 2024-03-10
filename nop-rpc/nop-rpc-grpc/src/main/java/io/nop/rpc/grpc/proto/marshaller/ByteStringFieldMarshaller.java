/**
 * Copyright (c) 2017-2024 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-entropy
 * Github: https://github.com/entropy-cloud/nop-entropy
 */
package io.nop.rpc.grpc.proto.marshaller;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import io.nop.commons.bytes.ByteString;
import io.nop.commons.type.BinaryScalarType;
import io.nop.rpc.grpc.proto.IFieldMarshaller;

import java.io.IOException;

public class ByteStringFieldMarshaller implements IFieldMarshaller {
    public static ByteStringFieldMarshaller INSTANCE = new ByteStringFieldMarshaller();

    @Override
    public String getGrpcTypeName() {
        return "bytes";
    }

    @Override
    public boolean isObject() {
        return false;
    }

    @Override
    public Object readField(CodedInputStream in) throws IOException {
        return ByteString.of(in.readByteArray());
    }

    @Override
    public void writeField(CodedOutputStream out, int propId, Object value) throws IOException {
        out.writeByteArray(propId, toBytes(value));
    }

    @Override
    public void writeFieldNoTag(CodedOutputStream out, Object value) throws IOException {
        out.writeByteArrayNoTag(toBytes(value));
    }

    byte[] toBytes(Object value) {
        return ((ByteString) value).toByteArray();
    }

    @Override
    public int computeSize(int propId, Object value) {
        return CodedOutputStream.computeByteArraySize(propId, toBytes(value));
    }

    @Override
    public int computeSizeNoTag(Object value) {
        return CodedOutputStream.computeByteArraySizeNoTag(toBytes(value));
    }

    @Override
    public BinaryScalarType getBinaryScalarType() {
        return BinaryScalarType.BYTES;
    }
}
