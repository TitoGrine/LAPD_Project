package com.jrpc.server;

import com.generic.controllers.*;
import com.generic.models.GenericColors;
import com.jrpc.shared.ColorClient;
import com.jrpc.shared.ColorServer;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server implements ColorServer {
    private final List<ColorClient> clients = new CopyOnWriteArrayList<>();

    public CompletableFuture<GenericColors.Color> getRandomHexColor(){
        return CompletableFuture.completedFuture(ColorBuilderKt.buildHEXColor(ColorRandomizerKt.getRandomHexColor()));
    }

    public CompletableFuture<GenericColors.Color> getRandomRGBColor(){
        return CompletableFuture.completedFuture(ColorBuilderKt.buildRGBColor(ColorRandomizerKt.getRandomRGBColor()));
    }

    public CompletableFuture<GenericColors.Color> getRandomCMYKColor(){
        return CompletableFuture.completedFuture(ColorBuilderKt.buildCMYKColor(ColorRandomizerKt.getRandomCMYKColor()));
    }

    public CompletableFuture<GenericColors.Color> getRandomHSVColor(){
        return CompletableFuture.completedFuture(ColorBuilderKt.buildHSVColor(ColorRandomizerKt.getRandomHSVColor()));
    }

    public CompletableFuture<GenericColors.ColorConversionResponse> convertColor(GenericColors.ColorConversionRequest request) {
        if(request == null)
            return CompletableFuture.failedFuture(new Throwable("Request can't be null"));

        GenericColors.ColorConversionResponse response = ColorConverterKt.convertColor(request);

        return CompletableFuture.completedFuture(response);
    }

    public CompletableFuture<GenericColors.ColorPaletteResponse> generateColorPalette(GenericColors.ColorPaletteRequest request) {
        if(request == null)
            return CompletableFuture.failedFuture(new Throwable("Request can't be null"));

        GenericColors.ColorPaletteResponse response = ColorPaletteGeneratorKt.generatePalette(request);

        return CompletableFuture.completedFuture(response);
    }

    public Runnable addClient(ColorClient client) {
        this.clients.add(client);
        return () -> this.clients.remove(client);
    }
}
