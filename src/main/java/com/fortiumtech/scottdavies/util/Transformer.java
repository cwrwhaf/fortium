package com.fortiumtech.scottdavies.util;

public interface Transformer<I, O> {

	O transform(I input);
}
