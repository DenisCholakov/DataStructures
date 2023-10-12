package craftsmanLab.core;

import craftsmanLab.models.ApartmentRenovation;
import craftsmanLab.models.Craftsman;

import java.util.*;

public class CraftsmanLabImpl implements CraftsmanLab {
    private final HashMap<String, ApartmentRenovation> addressToApartment;
    private final HashMap<String, Craftsman> nameToCraftsman;
    private final PriorityQueue<Craftsman> unassignedCraftsman;
    private final List<ApartmentRenovation> unassignedApartments;
    private final HashMap<ApartmentRenovation, Craftsman> apartmentToCraftsman;

    private Craftsman leastProfitableCraftsman;


    public CraftsmanLabImpl() {
        this.unassignedCraftsman = new PriorityQueue<>(Comparator.comparing(c -> c.hourlyRate));
        this.unassignedApartments = new LinkedList<>();
        this.addressToApartment = new LinkedHashMap<>();
        this.nameToCraftsman = new LinkedHashMap<>();
        this.apartmentToCraftsman = new HashMap<>();
    }

    @Override
    public void addApartment(ApartmentRenovation job) {
        if (this.addressToApartment.containsKey(job.address)) {
            throw new IllegalArgumentException("Job already added.");
        }

        this.addressToApartment.put(job.address, job);
        this.unassignedApartments.add(job);
    }

    @Override
    public void addCraftsman(Craftsman craftsman) {
        if (this.nameToCraftsman.containsKey(craftsman.name)) {
            throw new IllegalArgumentException("Craftsman already added.");
        }

        this.nameToCraftsman.put(craftsman.name, craftsman);
        this.unassignedCraftsman.add(craftsman);
    }

    @Override
    public boolean exists(ApartmentRenovation job) {
        return this.addressToApartment.containsKey(job.address);
    }

    @Override
    public boolean exists(Craftsman craftsman) {
        return this.nameToCraftsman.containsKey(craftsman.name);
    }

    @Override
    public void removeCraftsman(Craftsman craftsman) {
        if (!this.nameToCraftsman.containsKey(craftsman.name)) {
            throw new IllegalArgumentException("Craftsman is not existing.");
        }

        this.nameToCraftsman.remove(craftsman.name);
    }

    @Override
    public Collection<Craftsman> getAllCraftsmen() {
        return this.nameToCraftsman.values();
    }

    @Override
    public void assignRenovations() {
        while (!this.unassignedApartments.isEmpty() && this.unassignedCraftsman.isEmpty()) {
            ApartmentRenovation apToAssign = this.unassignedApartments.removeFirst();
            Craftsman craftToAssign = this.unassignedCraftsman.poll();
            this.apartmentToCraftsman.put(apToAssign, craftToAssign);
        }
    }

    @Override
    public Craftsman getContractor(ApartmentRenovation job) {
        if (!this.apartmentToCraftsman.containsKey(job)) {
            throw new IllegalArgumentException();
        }

        return this.apartmentToCraftsman.get(job);
    }

    @Override
    public Craftsman getLeastProfitable() {
        return null;
    }

    @Override
    public Collection<ApartmentRenovation> getApartmentsByRenovationCost() {
        return null;
    }

    @Override
    public Collection<ApartmentRenovation> getMostUrgentRenovations(int limit) {
        return null;
    }
}
