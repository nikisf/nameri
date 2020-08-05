package com.softuni.model.entity.Enum;


import java.util.HashMap;
import java.util.Map;

public enum Region {

    Burgas("Бургас"),
    Varna("Варна"),
    VelikoTarnovo("Велико Търново"),
    Vidin("Видин"),
    Vraca("Враца"),
    Gabrovo("Габрово"),
    Dobrich("Добрич"),
    Dupnica("Дупница"),
    Kurdjali("Кърджали"),
    Kiustendil("Кюстендил"),
    Lovech("Ловеч"),
    Montana("Монтана"),
    Pazardjik("Пазарджик"),
    Pernik("Перник"),
    Pleven("Плевен"),
    Plovdiv("Пловдив"),
    Razgrad("Разград"),
    Ruse("Русе"),
    Silistra("Силистра"),
    Sliven("Сливен"),
    Smolqn("Смолян"),
    Sofia("София"),
    StaraZagora("Стара Загора"),
    Tyrgovishte("Търговище"),
    Haskovo("Хасково"),
    Shumen("Шумен"),
    Jambol("Ямбол"),
    OutOfTheCountry("Извън страната");


    private String region;


    Region(String region) {
        this.region = region;
    }

    public String getRegion() {
        return this.region;
    }

    private static final Map<String,Region> map;
    static {
        map = new HashMap<>();
        for (Region v : Region.values()) {
            map.put(v.region, v);
        }
    }
    public static Region findByString(String name) {
        return map.get(name);
    }
}

