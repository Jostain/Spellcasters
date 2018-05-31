package com.example.erik.spellcasters;

import static com.example.erik.spellcasters.SharedData.random;

/**
 * Created by Erik on 2017-05-16.
 */

public class Demon {
    static String[] aztecQuads = new String[]{"ahui","atet","macu","ilto","chtl","macu","ilxo","chit","mama","xtli","Atla","coya","Cent","zonm","imix","coat","cent","zonh","uitz","nahu","cent","zont","otoc","htin","iezc","atzo","ncat","tlil","tolt","ecat","maya","huel","pate","catl","tepo","ztec","cint","eteo","izta","cuhca","tlat","lauh","cint","eotl","citl","alic","cipa","cton","cihu","atet","civa","tate","cihu","acoa","chal","chiu","toto","chal","chiu","htli","cueChan","tico","chim","alma","chic","omec","oatl","coat","licu","coyo","lxau","hqui","ehec","atl","ehec","atot","ontl","mict","lanp","ache","catl","cihu","atec","ayot","tlal","ocay","vitz","tlam","paeh","ecat","Huit","zilo","poch","hueh","ueco","yotl","hueh","uete","Huix","toci","huat","Itzp","apal","otlt","otec","Itzp","apal","otlc","ihua","Inaq","uizc","aotl","Huit","zilo","poch","tliv","ixtl","ilto","macu","ilxo","chit","mali","nalx","ochi","maya","huel","metz","mict","lant","ecuh","mict","ecac","ihua","mixc","oatl","opoc","htli","omet","eotl","omet","cuht","omec","ihua","oxom","pilt","zint","ecuh","pate","catl","pain","quet","zalc","oatl","tema","zcal","teci","tepe","yoll","tezc","atli","poca","tezc","atzo","ncat","tian","quiz","tlaz","olte","tlal","tecu","htli","tlal","cihu","tlah","uizc","alpa","ntec","uhtl","tlal","toci","tona","tiuh","tona","cate","cuht","tona","caci","huat","Tzit","zimi","xant","xilo","chic","omec","oatl","xipe","tote","xiuh","tecu","htli","xoch","ipil","xoch","ique","tzal","xolo","yaca","tecu","htli","zaca","tzon"};
    static String[] aztecEnds = new String[]{"atl","l","i","otl","l","l","n","otl","l","i","tli","tl","li","c","l","li","otl","ico","me","eo","i","l","oc","nen","tli","al","o","tl","li","tl","tl","li","tli","tli","tli","lin","otl","a","l","hua","l","atl","ca","al","o","tl","eo","ue"};

    public String name = "???";
    public int health = 101;
    public int mental = 102;
    public int shield = 0;
    public int singleShield = 0;
    public int temperature = 0;
    public int wet = 0;
    public boolean visible = true;
    public boolean ignore = false;
    public boolean blind = false;
    public double speed = 1;
    public double longitude = 0;
    public double latitude = 0;
    public int evasive = 0;
    public boolean hideSpell = false;
    public String lastCastSpell = "none";
    public String forcedSpell = "";
    public String spellbook = "Magic Missile";
    public String availableSpells = "Magic Missile";
    public Long timeStamp = (long)0;

    public static Demon summonDemon(String type) {

        Demon demon = new Demon();
        demon.name = generateRandomName();
        return demon;
    }

    private static String generateRandomName() {

            String name = "";
            int quads = random.nextInt(4)+1;
            for(int i=0;i < quads;i++)
            {
                name = name+aztecQuads[random.nextInt(aztecQuads.length)];
            }
            name = name+aztecEnds[random.nextInt(aztecEnds.length)];
            return name;

    }
}
