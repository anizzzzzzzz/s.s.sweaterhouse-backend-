package com.anizzzz.product.sssweaterhouse.utils.impl;

import com.anizzzz.product.sssweaterhouse.utils.ICompresserUtils;
import com.googlecode.pngtastic.core.PngImage;
import com.googlecode.pngtastic.core.PngOptimizer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PngCompresserUtil implements ICompresserUtils {
    @Override
    public void compressImages(InputStream inputStream, String destination) throws IOException {
        PngImage image=new PngImage(inputStream);

        PngOptimizer optimizer=new PngOptimizer();
        PngImage optimizedImage=optimizer.optimize(image);

        ByteArrayOutputStream optimizedBytes=new ByteArrayOutputStream();
        optimizedImage.writeDataOutputStream(optimizedBytes);
        optimizedImage.export(destination,optimizedBytes.toByteArray());
    }
}
