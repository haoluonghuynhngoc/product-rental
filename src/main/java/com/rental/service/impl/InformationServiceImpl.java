package com.rental.service.impl;

import com.rental.domain.enums.InformationStatus;
import com.rental.repository.InformationRepository;
import com.rental.repository.UserRepository;
import com.rental.service.InformationService;
import com.rental.service.dto.InformationDTO;
import com.rental.service.dto.PagingResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class InformationServiceImpl implements InformationService {
    @Autowired
    private InformationRepository informationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

//    @Override
//    public Optional<InformationDTO> getOneInfo(Long id) {
//        return informationRepository.findById(id).map(
//                n -> {
//                    n.setIsRead(true);//set da doc
//                    return modelMapper.map(n, InformationDTO.class);
//                });
//    }

    @Override
    public PagingResponse<InformationDTO> findAllInfoAdmin(Pageable pageable) {
        List<InformationDTO> list = new ArrayList<>();
        informationRepository.findAll().stream().filter(i -> {
            return i.getUser().getId() == 1L && i.getStatus().equals(InformationStatus.CENSORSHIP);
        }).collect(Collectors.toList()).forEach(
                x -> list.add(modelMapper.map(x, InformationDTO.class))
        );
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        Page<InformationDTO> pageInfo = new PageImpl<>(list.subList(start, end), pageable, list.size());
        return PagingResponse.<InformationDTO>builder()
                .page(pageInfo.getPageable().getPageNumber() + 1)
                .size(pageInfo.getSize())
                .totalPage(pageInfo.getTotalPages())
                .totalItem(pageInfo.getTotalElements())
                .contends(pageInfo.getContent())
                .build();
    }

    @Override
    public PagingResponse<InformationDTO> findAllInfoUser(Pageable pageable, Long id) {
        List<InformationDTO> list = new ArrayList<>();
        informationRepository.findAllByUser(userRepository.findById(id).orElse(null)).filter(i -> {
            return i.getStatus().equals(InformationStatus.CUSTOMER);
        }).collect(Collectors.toList()).forEach(
                i -> list.add(modelMapper.map(i, InformationDTO.class)));
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        Page<InformationDTO> pageInfo = new PageImpl<>(list.subList(start, end), pageable, list.size());
        return PagingResponse.<InformationDTO>builder()
                .page(pageInfo.getPageable().getPageNumber() + 1)
                .size(pageInfo.getSize())
                .totalPage(pageInfo.getTotalPages())
                .totalItem(pageInfo.getTotalElements())
                .contends(pageInfo.getContent())
                .build();
    }

    @Override
    public Integer findAllInfoIsReadByUser(Long id, InformationStatus status) {
        return (int) informationRepository.findAllByUser(userRepository.findById(id).orElse(null))
                .filter(x -> {
                    if (x.getIsRead() != null && x.getStatus().equals(status))
                        return !x.getIsRead();
                    else
                        return false;
                })
                .count();
    }

    @Override
    public Optional<InformationDTO> findDetailInfo(Long id) {
        return informationRepository.findById(id).map(i -> {
            i.setIsRead(true);
            return modelMapper.map(i, InformationDTO.class);
        });
    }
}
