package com.jrpc.shared;

import com.generic.models.GenericColors;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;

import java.util.concurrent.CompletableFuture;

@JsonSegment("server")
public interface ColorServer {

    @JsonRequest("hex/random")
    CompletableFuture<GenericColors.Color> getRandomHexColor();

    @JsonRequest("rgb/random")
    CompletableFuture<GenericColors.Color> getRandomRGBColor();

    @JsonRequest("cmyk/random")
    CompletableFuture<GenericColors.Color> getRandomCMYKColor();

    @JsonRequest("hsv/random")
    CompletableFuture<GenericColors.Color> getRandomHSVColor();

    @JsonRequest("color/convert")
    CompletableFuture<GenericColors.ColorConversionResponse> convertColor(GenericColors.ColorConversionRequest request);

    @JsonRequest("color/palette")
    CompletableFuture<GenericColors.ColorPaletteResponse> generateColorPalette(GenericColors.ColorPaletteRequest request);
}
