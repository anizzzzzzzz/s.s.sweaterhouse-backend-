package com.anizzzz.product.sssweaterhouse.utils;

import java.io.IOException;
import java.io.InputStream;

public interface ICompresserUtils {
    void compressImages(InputStream inputStream, String destination) throws IOException;
}
