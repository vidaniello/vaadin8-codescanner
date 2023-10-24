package com.github.vidaniello.codescanner;

import java.io.Serializable;

/**
 * The response will be called in Vaadin session ctx active.
 * @author vidaniello (github.com/vidaniello) <vidaniello@gmail.com>
 *
 * @param <T>
 */
@FunctionalInterface
public interface CallJsListner<T> extends Serializable {
    void response(T response);
}