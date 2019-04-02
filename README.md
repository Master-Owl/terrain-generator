# Procedural Terrain Generator
The final built version of this project can be downloaded [here](https://drive.google.com/file/d/1iZkzizjlOM2n_ktBl1WKwTf7aLxcTSsX/view?usp=sharing).
Instructions for running the JAR are included in the download.

## General Overview
This project implements the algorithm for [Perlin noise](https://en.wikipedia.org/wiki/Perlin_noise) and overlays three "maps" of white noise: a height map, a temperature map, and a moisture map.
Biomes are based on the resulting overlap of the three maps at any given point, producing a natural looking randomly generated landscape.
