package lwjglTests;


public class ColorWheel {
    public ColorWheel() {
        hex = new char[6];
        hex[0] = 'f';
        hex[1] = 'f';
        hex[2] = '0';
        hex[3] = '0';
        hex[4] = '0';
        hex[5] = '0';
    }

    private char[] hex;

    public void incRed(int amount) {
        amount = amount % 255;

        if (hex[0] == 'f' && hex[1] == 'f')
            return;

        while (amount > 0){
            if (hex[1] == 'f'){
                hex[1] = '0';
                hex[0] = incHex(hex[0]);
            }
            else{
                hex[1] = incHex(hex[1]);
            }

            amount--;
            if (hex[0] == 'f' && hex[1] == 'f') break;
        }
    }

    public void decRed(int amount){
        amount = amount % 255;

        if (hex[0] == '0' && hex[1] == '0')
            return;

        while (amount > 0){
            if (hex[1] == '0'){
                hex[1] = 'f';
                hex[0] = decHex(hex[0]);
            }
            else {
                hex[1] = decHex(hex[1]);
            }

            amount--;
            if (hex[0] == '0' && hex[1] == '0') break;
        }
    }

    public void incGreen(int amount) {
        amount = amount % 255;

        if (hex[2] == 'f' && hex[3] == 'f')
            return;

        while (amount > 0){
            if (hex[3] == 'f'){
                hex[3] = '0';
                hex[2] = incHex(hex[2]);
            }
            else{
                hex[3] = incHex(hex[3]);
            }

            amount--;
            if (hex[2] == 'f' && hex[3] == 'f') break;
        }
    }

    public void decGreen(int amount){
        amount = amount % 255;

        if (hex[2] == '0' && hex[3] == '0')
            return;

        while (amount > 0){
            if (hex[3] == '0'){
                hex[3] = 'f';
                hex[2] = decHex(hex[2]);
            }
            else {
                hex[3] = decHex(hex[3]);
            }

            amount--;
            if (hex[2] == '0' && hex[3] == '0') break;
        }
    }

    public void incBlue(int amount) {
        amount = amount % 255;

        if (hex[4] == 'f' && hex[5] == 'f')
            return;

        while (amount > 0){
            if (hex[5] == 'f'){
                hex[5] = '0';
                hex[4] = incHex(hex[4]);
            }
            else{
                hex[5] = incHex(hex[5]);
            }

            amount--;
            if (hex[4] == 'f' && hex[5] == 'f') break;
        }
    }

    public void decBlue(int amount){
        amount = amount % 255;

        if (hex[4] == '0' && hex[5] == '0')
            return;

        while (amount > 0){
            if (hex[5] == '0'){
                hex[5] = 'f';
                hex[4] = decHex(hex[4]);
            }
            else {
                hex[5] = decHex(hex[5]);
            }

            amount--;
            if (hex[4] == '0' && hex[5] == '0') break;
        }
    }

    public char[] getHex(){
        return hex;
    }

    public int getRed() {
        char[] red = new char[2];
        red[0] = hex[0];
        red[1] = hex[1];
        return getColorInt(red);
    }

    public int getGreen() {
        char[] green = new char[2];
        green[0] = hex[2];
        green[1] = hex[3];
        return getColorInt(green);
    }

    public int getBlue() {
        char[] blue = new char[2];
        blue[0] = hex[4];
        blue[1] = hex[5];
        return getColorInt(blue);
    }

    public int getColorInt(char[] hexColors){
        int BASE = 16;
        int total = 0;

        for (int x = 0; x < hexColors.length; ++x)
            total += valueOf(hexColors[(hexColors.length - 1) - x]) * Math.pow(BASE, x);

        return total;
    }

    private int valueOf(char c){
        int num = Character.getNumericValue(c);
        if (num >= 0 && num < 10)
            return num;
        if (c == 'a') return 10;
        if (c == 'b') return 11;
        if (c == 'c') return 12;
        if (c == 'd') return 13;
        if (c == 'e') return 14;
        if (c == 'f') return 15;
        return 0;
    }

    private char incHex(char hex){
        int num = Character.getNumericValue(hex);
        if (num >= 0 && num <= 8) return digitToChar(num + 1);
        if (num ==  9)  return 'a';
        if (hex == 'a') return 'b';
        if (hex == 'b') return 'c';
        if (hex == 'c') return 'd';
        if (hex == 'd') return 'e';
        if (hex == 'e') return 'f';
        if (hex == 'f') return 'f';
        return 'f';
    }

    private char decHex(char hex){
        int num = Character.getNumericValue(hex);
        if (num > 0 && num <= 9) return digitToChar(num - 1);
        if (hex == 'a') return '9';
        if (hex == 'b') return 'a';
        if (hex == 'c') return 'b';
        if (hex == 'd') return 'c';
        if (hex == 'e') return 'd';
        if (hex == 'f') return 'e';
        return '0';
    }

    private char digitToChar(int num){
        switch(num){
            case 0: return '0';
            case 1: return '1';
            case 2: return '2';
            case 3: return '3';
            case 4: return '4';
            case 5: return '5';
            case 6: return '6';
            case 7: return '7';
            case 8: return '8';
            case 9: return '9';
            default: return '0';
        }
    }
}
