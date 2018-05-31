package com.example.erik.spellcasters;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by minty on 2017-01-06.
 */

public abstract class NameGenerator {
    private static String[] color = new String[]{"Alice Blue","Antique White","Aqua","Aquamarine","Azure","Beige","Bisque","Black","Blanched Almond","Blue","Blue Violet","Brown","Burly Wood","Cadet Blue","Chartreuse","Chocolate","Coral","Cornflower Blue","CornSilk","Crimson","Cyan","Deep Pink","DeepSkyBlue","Dim Gray","Dim Grey","Dodger Blue","Fire Brick Red","Floral White","Forest Green","Fuchsia","Gainsboro","Ghost White","Gold","Golden Rod","Gray","Grey","Green","Green Yellow","Honeydew","Hot Pink","Indian Red","Indigo","Ivory","Khaki","Lavender","LavenderBlue","Lawn Green","Lemon Chiffon","Lime","Lime Green","Linen","Magenta","Maroon","Midnight Blue","Mint Cream","Misty Rose","Moccasin","NavajoWhite","Navy","OldLace","Olive","OliveDrab","Orange","OrangeRed","Orchid","PapayaWhip","PeachPuff","Peru","Pink","Plum","Powder Blue","Purple","Rebecca Purple","Red","Rosy Brown","Royal Blue","Saddle Brown","Salmon","Sandy Brown","Sea Green","Sea Shell","Sienna","Silver","Sky Blue","Slate Blue","Slate Gray","Slate Grey","Snow","Spring Green","Steel Blue","Tan","Teal","Thistle","Tomato","Turquoise","Violet","Wheat","White","WhiteSmoke","Yellow","YellowGreen"};
    public static String generate()
    {
        String name = "";
        String[] start = new String[]{"Goth","Ari","Aiwen","Ala","Curu","Ilma","Mai","Mel","Oss","Pall","Sal","Tili","Dior","El","Arw","Ell","Eldar","rince","cobble"};
        String[] end = new String[]{"en","endil","tar","mo","umo","mog","mare","ron","ian","alf","rin","ando","lando","mar","sal","ion","nen","wing","ros","rion","dan","adan","rond","ond","wind","pot",""};

        name = name + (start[new Random().nextInt(start.length)]);
        name = name + (end[new Random().nextInt(end.length)]);
        name = name +" The ";
        name = name + (color[new Random().nextInt(color.length)]);

        return name;
    }
    public static String colorHex(String colorName)
    {
        int pos = Arrays.asList(color).indexOf(colorName);
        String[] hex = new String[]{"F0F8FF","FAEBD7","00FFF","7FFFD4","F0FFFF","F5F5DC","FFE4C4","000000","FFEBCD","0000FF","8A2BE2","A52A2A","DEB887","5F9EA0","7FFF00","D2691E","FF7F50","6495ED","FFF8DC","DC143C","00FFFF","FF1493","00BFFF","696969","696969","1E90FF","B22222","FFAF0","228B22","FF00FF","DCDCDC","F8F8FF","FFD700","DAA520","808080","808080","008000","ADFF2F","F0FFF0","FF69B4","CD5C5C","4B0082","FFFFF0","F0E68C","E6E6FA","FFF0F5","7CFC00","FFFACD","00FF00","32CD32","FAF0E6","FF00FF","800000","191970","F5FFFA","FFE4E1","FFE2B5","FFDEAD","000080","FDF5E6","808000","6B8E23","FFA500","FF4500","DA70D6","FFEFD5","FFDAB9","CD853F","FFC0CB","DDA0DD","B0E0E6","800080","663399","FF0000","BD8F8F","4169E1","8B4513","FA8072","F4A460","2E8B57","FFF5EE","A0522D","C0C0C0","87CEEB","6A5ACD","708090","708090","FFFAFA","00FF7F","4682B4","D2B48C","008080","D8BFD8","FF6347","40E0D0","EE82EE","F5DEB3","FFFFFF","F5F5F5","FFFF00","9ACD32"};
        //return hex[pos];
        return "FFFFFF";
    }
}
