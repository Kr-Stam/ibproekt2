package com.example.ibproekt2.service.impl;

import com.example.ibproekt2.entity.Manufacturer;
import com.example.ibproekt2.repository.ManufacturerRepository;
import com.example.ibproekt2.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Manufacturer> getALlManufacturers() {
        return manufacturerRepository.findAll();
    }

    @Override
    public Optional<Manufacturer> findById(long id) {
        return manufacturerRepository.findById(id);
    }

    @Override
    public void deleteById(long id) {
        manufacturerRepository.deleteById(id);
    }

    @Override
    public void save(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
    }
}
