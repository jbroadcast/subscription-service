package org.javadev.subscription.mapper;

public interface Mapper<F, T> {
    T toDTO (F object);
    F toEntity (T object);
}