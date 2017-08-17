package terrain.enums;

import java.awt.Color;

public enum Biome {
    OCEAN	(new Color(0, 	0, 	 140)),
    BEACH	(new Color(252, 213, 106)),
    DESERT	(new Color(255, 177, 89)),
    TEMPERATE_DESERT (new Color(186, 135, 68)),
    SCORCHED_DESERT  (new Color(122, 60,  13)),
    GRASSLAND (new Color(100, 142, 4)),
    SHRUBLAND (new Color(123, 188, 49)),
    CRAG	(new Color(94,  94,  94)),
    LAKES	(new Color(28,  55,  175)),
    MARSH	(new Color(14,  79,  79)),
    PLAINS	(new Color(127, 160, 43)),
    FOREST	(new Color(25,  117, 22)),
    RAIN_FOREST	(new Color(4, 96, 44)),
    TAIGA	(new Color(31,  137, 95)),
    TUNDRA	(new Color(19,  221, 188)),
    SNOW	(new Color(215, 224, 222));
	
	private final Color biomeColor;
	
	private Biome(Color biomeColor){
		this.biomeColor = biomeColor;
	}
	
    public String toString(){
        switch(this){
            case OCEAN:
                return "Ocean";
            case BEACH:
                return "Beach";
            case DESERT:
                return "Desert";
            case TEMPERATE_DESERT:
                return "Temperate Desert";
            case SCORCHED_DESERT:
                return "Scorched Desert";
            case GRASSLAND:
                return "Grassland";
            case SHRUBLAND:
                return "Shrubland";
            case CRAG:
                return "Crag";
            case LAKES:
                return "Lakes";
            case MARSH:
                return "Marsh";
            case PLAINS:
                return "Plains";
            case FOREST:
                return "Forest";
            case RAIN_FOREST:
                return "Rain Forest";
            case TAIGA:
                return "Taiga";
            case TUNDRA:
                return "Tundra";
            case SNOW:
                return "Snow";
        }

        return "err";
    }

    public Color color() { return biomeColor; }
}
