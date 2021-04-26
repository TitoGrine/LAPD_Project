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

    private final void printOperation(String op) {
        System.out.println();
        System.out.println("\t\t ---- JSON RPC ----");
        System.out.println(op);
        System.out.println();
    }

    public CompletableFuture<GenericColors.Color> getRandomHexColor(){
        printOperation("getRandomHexColor()");

        return CompletableFuture.supplyAsync(() -> ColorBuilderKt.buildHEXColor(ColorRandomizerKt.getRandomHexColor()));
    }

    public CompletableFuture<GenericColors.Color> getRandomRGBColor(){
        printOperation("getRandomRGBColor()");

        return CompletableFuture.supplyAsync(() -> ColorBuilderKt.buildRGBColor(ColorRandomizerKt.getRandomRGBColor()));
    }

    public CompletableFuture<GenericColors.Color> getRandomCMYKColor(){
        printOperation("getRandomCMYKColor()");

        return CompletableFuture.supplyAsync(() -> ColorBuilderKt.buildCMYKColor(ColorRandomizerKt.getRandomCMYKColor()));
    }

    public CompletableFuture<GenericColors.Color> getRandomHSVColor(){
        printOperation("getRandomHSVColor()");

        return CompletableFuture.supplyAsync(() -> ColorBuilderKt.buildHSVColor(ColorRandomizerKt.getRandomHSVColor()));
    }

    public CompletableFuture<GenericColors.ColorConversionResponse> convertColor(GenericColors.ColorConversionRequest request) {
        printOperation("convertColor(\n\t" + request.toString() + "\n)");

        return CompletableFuture.supplyAsync(() -> {
            if(request == null)
                return null;

            return ColorConverterKt.convertColor(request);
        });
    }

    public CompletableFuture<GenericColors.ColorPaletteResponse> generateColorPalette(GenericColors.ColorPaletteRequest request) {
        printOperation("generateColorPalette(\n\t" + request.toString() + "\n)");

        return CompletableFuture.supplyAsync(() -> {
            if(request == null)
                return null;

            return ColorPaletteGeneratorKt.generatePalette(request);
        });
    }

    public Runnable addClient(ColorClient client) {
        this.clients.add(client);
        return () -> this.clients.remove(client);
    }
}
