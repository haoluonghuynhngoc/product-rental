package com.rental.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rental.domain.Brand;
import com.rental.repository.BrandRepository;
import com.rental.service.BrandService;
import com.rental.service.dto.BrandDTO;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {



    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BrandRepository brandRepository;


    @Override
    public BrandDTO save(BrandDTO brandDTO) {
        Brand brand = modelMapper.map(brandDTO, Brand.class);
        brand = brandRepository.save(brand);
        return modelMapper.map(brand, BrandDTO.class);
    }

    @Override
    public BrandDTO update(BrandDTO brandDTO) {
        // Brand brand = brandRepository.findById(brandDTO.getId()).get();
        // modelMapper.map(brandDTO, brand);
        Brand brand = modelMapper.map(brandDTO, Brand.class);
        brand = brandRepository.save(brand);
        return modelMapper.map(brand, BrandDTO.class);
    }

    @Override
    public Optional<BrandDTO> partialUpdate(BrandDTO brandDTO) {

        return brandRepository
                .findById(brandDTO.getId())
                .map(existingBrand -> {
                    modelMapper.map(brandDTO, existingBrand);
                    return existingBrand;
                })
                .map(brandRepository::save)
                .map(b -> {
                    return modelMapper.map(b, BrandDTO.class);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BrandDTO> findAll(Pageable pageable) {
        return brandRepository.findAll(pageable).map(b -> {
            return modelMapper.map(b, BrandDTO.class);
        });
    }

    @Override
    public List<BrandDTO> findAllList() {
        return brandRepository.findAll().stream().map(b -> {
            return modelMapper.map(b, BrandDTO.class);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BrandDTO> findOne(Long id) {

        return brandRepository.findById(id).map(b -> {
            return modelMapper.map(b, BrandDTO.class);
        });
    }

    @Override
    public void delete(Long id) {

        brandRepository.deleteById(id);
    }
}
