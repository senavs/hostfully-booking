package com.senavs.booking.mapper;

public interface Mapper<A, B> {

    B unmap(final A a);

    A map(final B b);
}
