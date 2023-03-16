package com.rental.service.impl;

import com.rental.repository.InformationRepository;
import com.rental.service.InformationService;
import com.rental.service.dto.InformationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class InformationServiceImpl implements InformationService {
    @Autowired
    private InformationRepository informationRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<InformationDTO> getOneInfo(Long id) {
        return informationRepository.findById(id).map(
                n -> {
                    n.setIsRead(true);//set da doc
                    return modelMapper.map(n, InformationDTO.class);
                });
    }

    @Override
    public Page<InformationDTO> findAll(Pageable pageable) {
        return informationRepository.findAll(pageable).map(
                i -> {
                    return modelMapper.map(i, InformationDTO.class);
                });
    }

    @Override
    public Integer findALLInfoIsRead() {
        return (int) informationRepository.findAll().stream().filter(
                x -> {
                    if (x.getIsRead() != null)
                        return !x.getIsRead();
                    else
                        return false;
                }
        ).count();
    }
}
