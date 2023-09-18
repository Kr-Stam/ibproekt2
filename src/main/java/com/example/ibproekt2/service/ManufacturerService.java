package com.example.ibproekt2.service;

import com.example.ibproekt2.entity.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {

    List<Manufacturer> getALlManufacturers();

    Optional<Manufacturer> findById(long id);

    void deleteById(long id);

    void save(Manufacturer manufacturer);
}
