package terrain;


import terrain.enums.Biome;
import terrain.enums.Elevation;
import terrain.enums.Moisture;
import terrain.enums.Temperature;

public class MapInterpreter {
    // All levels are from range 0.0f to 1.0f

    // Elevation cut-offs
    private static final float SEA_LEVEL      = 0.17f;
    private static final float LOW_LEVEL      = 0.25f;
    private static final float VALLEY_LEVEL   = 0.40f;
    private static final float AVERAGE_LEVEL  = 0.55f;
    private static final float HILL_LEVEL     = 0.70f;
    private static final float MOUNTAIN_LEVEL = 0.90f;

    // Moisture cut-offs
    private static final float BARE      = 0.15f;
    private static final float DRY       = 0.30f;
    private static final float TEMPERATE = 0.55f;
    private static final float MOIST     = 0.70f;
    private static final float HUMID     = 0.85f;

    // Temperature cut-offs
    private static final float FREEZING  = 0.10f;
    private static final float COLD      = 0.40f;
    private static final float AVERAGE   = 0.65f;
    private static final float WARM      = 0.75f;
    private static final float HOT       = 0.90f;

    public static Biome GetBiome(float elevation, float temperature, float moisture){
        switch(GetElevation(elevation)){
            case SEA_LEVEL:
                return Biome.OCEAN;

            case LOW:
                switch(GetTemperature(temperature)){
                    case FREEZING:
                    case COLD:
                        switch(GetMoisture(moisture)){
                            case BARE:
                            case DRY:
                                return Biome.TUNDRA;
                            default:
                                return Biome.BEACH;
                        }
                    default:
                        return Biome.BEACH;
                }

            case VALLEY:
                switch(GetTemperature(temperature)){
                    case FREEZING:
                        return Biome.TUNDRA;
                    case COLD:
                        switch(GetMoisture(moisture)){
                            case BARE:
                            case DRY:
                                return Biome.TUNDRA;
                            default:
                                return Biome.TAIGA;
                        }
                    case AVERAGE:
                        switch(GetMoisture(moisture)){
                            case BARE:
                                return Biome.DESERT;
                            case DRY:
                                return Biome.PLAINS;
                            case TEMPERATE:
                                return Biome.GRASSLAND;
                            case MOIST:
                                return Biome.FOREST;
                            default:
                                return Biome.RAIN_FOREST;
                        }
                    case WARM:
                        switch(GetMoisture(moisture)){
                            case BARE:
                            case DRY:
                                return Biome.DESERT;
                            case TEMPERATE:
                            case MOIST:
                                return Biome.FOREST;
                            default:
                                return Biome.RAIN_FOREST;
                        }
                    case HOT:
                        switch(GetMoisture(moisture)){
                            case BARE:
                                return Biome.SCORCHED_DESERT;
                            case DRY:
                                return Biome.DESERT;
                            case TEMPERATE:
                                return Biome.TEMPERATE_DESERT;
                            case MOIST:
                                return Biome.SHRUBLAND;
                            default:
                                return Biome.MARSH;
                        }
                    default:
                        switch(GetMoisture(moisture)){
                            case BARE:
                            case DRY:
                                return Biome.SCORCHED_DESERT;
                            default:
                                return Biome.TEMPERATE_DESERT;
                        }
                }

            case AVERAGE:
                switch(GetTemperature(temperature)){
                    case FREEZING:
                        return Biome.TUNDRA;
                    case COLD:
                        switch(GetMoisture(moisture)){
                            case BARE:
                            case DRY:
                                return Biome.TUNDRA;
                            case TEMPERATE:
                            case MOIST:
                                return Biome.TAIGA;
                            default:
                                return Biome.SNOW;
                        }
                    case AVERAGE:
                    case WARM:
                        switch(GetMoisture(moisture)){
                            case BARE:
                                return Biome.DESERT;
                            case DRY:
                                return Biome.PLAINS;
                            case TEMPERATE:
                                return Biome.GRASSLAND;
                            case MOIST:
                                return Biome.SHRUBLAND;
                            default:
                                return Biome.MARSH;
                        }
                    case HOT:
                        switch(GetMoisture(moisture)){
                            case BARE:
                                return Biome.SCORCHED_DESERT;
                            case DRY:
                                return Biome.DESERT;
                            case TEMPERATE:
                                return Biome.GRASSLAND;
                            case MOIST:
                                return Biome.SHRUBLAND;
                            default:
                                return Biome.MARSH;
                        }
                    default:
                        switch(GetMoisture(moisture)){
                            case BARE:
                            case DRY:
                                return Biome.SCORCHED_DESERT;
                            default:
                                return Biome.TEMPERATE_DESERT;
                        }
                }

            case HILL:
                switch(GetTemperature(temperature)){
                    case FREEZING:
                        switch(GetMoisture(moisture)){
                            case BARE:
                            case DRY:
                            case TEMPERATE:
                                return Biome.TUNDRA;
                            default:
                                return Biome.SNOW;
                        }
                    case COLD:
                        switch(GetMoisture(moisture)){
                            case BARE:
                            case DRY:
                                return Biome.TAIGA;
                            default:
                                return Biome.SNOW;
                        }
                    case AVERAGE:
                        switch(GetMoisture(moisture)){
                            case BARE:
                            case DRY:
                                return Biome.GRASSLAND;
                            default:
                                return Biome.FOREST;
                        }
                    case WARM:
                        switch(GetMoisture(moisture)){
                            case BARE:
                            case DRY:
                                return Biome.DESERT;
                            case TEMPERATE:
                            case MOIST:
                                return Biome.FOREST;
                            default:
                                return Biome.RAIN_FOREST;
                        }
                    case HOT:
                        switch(GetMoisture(moisture)){
                            case BARE:
                            case DRY:
                                return Biome.SCORCHED_DESERT;
                            case TEMPERATE:
                                return Biome.FOREST;
                            default:
                                return Biome.RAIN_FOREST;
                        }
                    default:
                        switch(GetMoisture(moisture)){
                            case BARE:
                            case DRY:
                                return Biome.CRAG;
                            default:
                                return Biome.FOREST;
                        }
                }

            case MOUNTAIN:
                switch(GetTemperature(temperature)){
                    case FREEZING:
                        switch(GetMoisture(moisture)){
                            case BARE:
                            case DRY:
                                return Biome.TUNDRA;
                            default:
                                return Biome.SNOW;
                        }
                    case COLD:
                        switch(GetMoisture(moisture)){
                            case BARE:
                            case DRY:
                                return Biome.TAIGA;
                            default:
                                return Biome.SNOW;
                        }
                    default:
                        switch(GetMoisture(moisture)){
                            case BARE:
                            case DRY:
                                return Biome.CRAG;
                            default:
                                return Biome.FOREST;
                        }
                }

            case PEAK:
                switch(GetTemperature(temperature)){
                    case FREEZING:
                    case COLD:
                    case AVERAGE:
                        return Biome.SNOW;
                    default:
                        switch(GetMoisture(moisture)){
                            case BARE:
                            case DRY:
                                return Biome.CRAG;
                            default:
                                return Biome.FOREST;
                        }
                }
        }

        return Biome.LAKES;
    }

    private static Elevation GetElevation(float e){
        if (e < SEA_LEVEL)      return Elevation.SEA_LEVEL;
        if (e < LOW_LEVEL)      return Elevation.LOW;
        if (e < VALLEY_LEVEL)   return Elevation.VALLEY;
        if (e < AVERAGE_LEVEL)  return Elevation.AVERAGE;
        if (e < HILL_LEVEL)     return Elevation.HILL;
        if (e < MOUNTAIN_LEVEL) return Elevation.MOUNTAIN;
        else                    return Elevation.PEAK;
    }

    private static Temperature GetTemperature(float t){
        if (t < FREEZING)   return Temperature.FREEZING;
        if (t < COLD)       return Temperature.COLD;
        if (t < AVERAGE)    return Temperature.AVERAGE;
        if (t < WARM)       return Temperature.WARM;
        if (t < HOT)        return Temperature.HOT;
        else                return Temperature.SCORCHED;
    }

    private static Moisture GetMoisture(float m){
        if (m < BARE)       return Moisture.BARE;
        if (m < DRY)        return Moisture.DRY;
        if (m < TEMPERATE)  return Moisture.TEMPERATE;
        if (m < MOIST)      return Moisture.MOIST;
        if (m < HUMID)      return Moisture.HUMID;
        else                return Moisture.WET;
    }
}
