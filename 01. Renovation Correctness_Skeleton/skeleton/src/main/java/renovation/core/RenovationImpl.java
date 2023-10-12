package renovation.core;

import renovation.models.Laminate;
import renovation.models.Tile;
import renovation.models.WoodType;

import java.util.*;
import java.util.stream.Collectors;

public class RenovationImpl implements Renovation {
    private int deliveredTilesArea;
    private final List<Tile> tiles;
    private final HashSet<Integer> tilesHasCodes;
    private final HashSet<Integer> laminateHashCodes;
    private final List<Laminate> laminateTile;
    private final HashMap<WoodType, LinkedList<Laminate>> woodTypeToLaminate;

    public RenovationImpl() {
        this.deliveredTilesArea = 0;
        this.tiles = new LinkedList<>();
        this.laminateTile = new LinkedList<>();
        this.tilesHasCodes = new HashSet<>();
        this.laminateHashCodes = new HashSet<>();
        this.woodTypeToLaminate = new HashMap<>();
        this.woodTypeToLaminate.put(WoodType.OAK, new LinkedList<>());
        this.woodTypeToLaminate.put(WoodType.CHERRY, new LinkedList<>());
        this.woodTypeToLaminate.put(WoodType.WALNUT, new LinkedList<>());
    }

    @Override
    public void deliverTile(Tile tile) {
        if (deliveredTilesArea + tile.width * tile.height > 30) {
            throw new IllegalArgumentException("Tiles should be no more than 30 sq. m.");
        }

        deliveredTilesArea += tile.width * tile.height;
        this.tiles.add(tile);
        this.tilesHasCodes.add(tile.hashCode());
    }

    @Override
    public void deliverFlooring(Laminate laminate) {
        this.laminateHashCodes.add(laminate.hashCode());
        this.laminateTile.add(laminate);
        this.woodTypeToLaminate.get(laminate.woodType).add(laminate);
    }

    @Override
    public double getDeliveredTileArea() {
        return this.deliveredTilesArea;
    }

    @Override
    public boolean isDelivered(Laminate laminate) {
        return this.laminateHashCodes.contains(laminate.hashCode());
    }

    @Override
    public void returnTile(Tile tile) {
        if (!this.tilesHasCodes.contains(tile.hashCode())) {
            throw new IllegalArgumentException("Tile is not present.");
        }

        for (int i = this.tiles.size() - 1; i >= 0 ; i--) {
            if (this.tiles.get(i).equals(tile)) {
                this.tiles.remove(i);
            }
        }
    }

    @Override
    public void returnLaminate(Laminate laminate) {
        if (!this.laminateHashCodes.contains(laminate.hashCode())) {
            throw new IllegalArgumentException("Tile is not present.");
        }

        this.laminateTile.remove(laminate);
    }

    @Override
    public Collection<Laminate> getAllByWoodType(WoodType wood) {
        return this.woodTypeToLaminate.get(wood);
    }

    @Override
    public Collection<Tile> getAllTilesFitting(double width, double height) {
        List<Tile> result = new ArrayList<>();

        for (Tile t: this.tiles) {
            if (t.width <= width && t.height <= height) {
                result.add(t);
            }
        }

        return result;
    }

    @Override
    public Collection<Tile> sortTilesBySize() {
        return this.tiles.stream().sorted(Comparator.comparing(t -> t.height * t.width)).collect(Collectors.toList());
    }

    @Override
    public Iterator<Laminate> layFlooring() {
        return new Iterator<Laminate>() {
            int index = laminateTile.size() - 1;
            @Override
            public boolean hasNext() {
                return index >= 0;
            }

            @Override
            public Laminate next() {
                return laminateTile.get(this.index--);
            }
        };
    }
}
