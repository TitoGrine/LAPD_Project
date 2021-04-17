package com.jrpc.client;

import com.generic.models.GenericColors;
import com.jrpc.shared.ColorClient;
import com.jrpc.shared.ColorServer;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Client implements ColorClient {

    private Scanner scanner = new Scanner(System.in);

    private int getOperationFromUser(){
        System.out.println();
        System.out.println("Operations:");
        System.out.println("    [1] - Get random color");
        System.out.println("    [2] - Convert color");
        System.out.println("    [3] - Generate color palette");
        System.out.println();

        int choice = -1;

        while(choice < 0 || 3 < choice){
            System.out.println("Choose operation: ");
            while(!scanner.hasNextInt()){
                scanner.nextLine();
            }
            choice = scanner.nextInt();
        }

        scanner.nextLine();
        System.out.println();

        return choice;
    }

    public void start(ColorServer server) throws Exception {
        boolean exit = false;

        while(!exit){
            switch (getOperationFromUser()){
                case 1:
                    randomColorOperation(server);
                    break;
                case 2:
                    convertColorOperation(server);
                    break;
                case 3:
                    generatePaletteOperation(server);
                    break;
                case 0:
                default:
                    exit = true;
            }
        }
    }

    private int getColorModeFromUser(String title) {
        System.out.println();
        System.out.printf("%s:%n", title);
        System.out.println("    [1] - Hexadecimal");
        System.out.println("    [2] - RGB");
        System.out.println("    [3] - CMYK");
        System.out.println("    [4] - HSV");
        System.out.println();

        int choice = -1;

        while(choice < 1 || 4 < choice){
            System.out.println("Choose mode: ");
            while(!scanner.hasNextInt()){
                scanner.nextLine();
            }

            choice = scanner.nextInt();
        }

        scanner.nextLine();
        System.out.println();

        return choice;
    }

    private void randomColorOperation(ColorServer server) throws ExecutionException, InterruptedException {
        GenericColors.Color result;

        switch (getColorModeFromUser("Choose color mode for random color")){
            case 1:
                result = server.getRandomHexColor().get();
                System.out.println(result.toString());
                return;
            case 2:
                result = server.getRandomRGBColor().get();
                System.out.println(result.toString());
                return;
            case 3:
                result = server.getRandomCMYKColor().get();
                System.out.println(result.toString());
                return;
            case 4:
                result = server.getRandomHSVColor().get();
                System.out.println(result.toString());
                return;
            default:
        }
    }

    private GenericColors.Color getHexFromUser() {
        System.out.println("Insert an hexadecimal color code: ");
        String code = scanner.nextLine();

        return new GenericColors.Color(new GenericColors.Color.Mode(new GenericColors.HEX(code)));
    }

    private GenericColors.Color getRGBFromUser() {
        System.out.println("Insert the red color value (0-255): ");
        int red = scanner.nextInt();
        System.out.println("Insert the green color value (0-255): ");
        int green = scanner.nextInt();
        System.out.println("Insert the blue color value (0-255): ");
        int blue = scanner.nextInt();
        scanner.nextLine();

        return new GenericColors.Color(new GenericColors.Color.Mode(new GenericColors.RGB(red, green, blue)));
    }

    private GenericColors.Color getCMYKFromUser() {
        System.out.println("Insert the cyan color value (0-1): ");
        float cyan = scanner.nextFloat();
        System.out.println("Insert the magenta color value (0-1): ");
        float magenta = scanner.nextFloat();
        System.out.println("Insert the yellow color value (0-1): ");
        float yellow = scanner.nextFloat();
        System.out.println("Insert the key color value (0-1): ");
        float key = scanner.nextFloat();
        scanner.nextLine();

        return new GenericColors.Color(new GenericColors.Color.Mode(new GenericColors.CMYK(cyan, magenta, yellow, key)));
    }

    private GenericColors.Color getHSVFromUser() {
        System.out.println("Insert the hue (0-360): ");
        int hue = scanner.nextInt();
        System.out.println("Insert the saturation (0-1): ");
        float saturation = scanner.nextFloat();
        System.out.println("Insert the value (0-1): ");
        float value = scanner.nextFloat();
        scanner.nextLine();

        return new GenericColors.Color(new GenericColors.Color.Mode(new GenericColors.HSV(hue, saturation, value)));
    }

    private GenericColors.Color getColorFromUser(String text) {
        switch (getColorModeFromUser(text)){
            case 1:
                return getHexFromUser();
            case 2:
                return getRGBFromUser();
            case 3:
                return getCMYKFromUser();
            case 4:
            default:
                return getHSVFromUser();
        }
    }

    private void convertColorOperation(ColorServer server) throws ExecutionException, InterruptedException {
        GenericColors.Color color = getColorFromUser("Color mode for original color");
        GenericColors.ColorMode colorMode;

        switch (getColorModeFromUser("Color mode to convert to")){
            case 1:
                colorMode = GenericColors.ColorMode.HEX_MODE;
                break;
            case 2:
                colorMode = GenericColors.ColorMode.RGB_MODE;
                break;
            case 3:
                colorMode = GenericColors.ColorMode.CMYK_MODE;
                break;
            case 4:
            default:
                colorMode = GenericColors.ColorMode.HSV_MODE;
        }

        GenericColors.ColorConversionRequest request = new GenericColors.ColorConversionRequest(colorMode, color);
        GenericColors.ColorConversionResponse response = server.convertColor(request).get();

        System.out.println(response.toString());
        System.out.println();
    }

    private void generatePaletteOperation(ColorServer server) throws ExecutionException, InterruptedException {
        GenericColors.Color color = getColorFromUser("Color mode for base color");

        GenericColors.ColorPaletteRequest request = new GenericColors.ColorPaletteRequest(color);
        GenericColors.ColorPaletteResponse response = server.generateColorPalette(request).get();

        System.out.println(response.toString());
        System.out.println();
    }
}
