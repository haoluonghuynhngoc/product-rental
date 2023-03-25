package com.rental.service.impl;

import com.rental.domain.enums.InformationStatus;
import com.rental.repository.InformationRepository;
import com.rental.repository.UserRepository;
import com.rental.service.InformationService;
import com.rental.service.dto.InformationDTO;
import com.rental.service.dto.InformationShowDTO;
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
    public PagingResponse<InformationShowDTO> findAllInfoAdmin(Pageable pageable) {
        List<InformationShowDTO> list = new ArrayList<>();
        informationRepository.findAll().stream().filter(i -> {
            return i.getUser().getId() == 1L && i.getStatus().equals(InformationStatus.CENSORSHIP);
        }).sorted((x, y) -> y.getId().compareTo(x.getId())).collect(Collectors.toList()).forEach(
                x -> list.add(modelMapper.map(x, InformationShowDTO.class))
        );
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        Page<InformationShowDTO> pageInfo = new PageImpl<>(list.subList(start, end), pageable, list.size());
        return PagingResponse.<InformationShowDTO>builder()
                .page(pageInfo.getPageable().getPageNumber() + 1)
                .size(pageInfo.getSize())
                .totalPage(pageInfo.getTotalPages())
                .totalItem(pageInfo.getTotalElements())
                .contends(pageInfo.getContent())
                .build();
    }

    @Override
    public PagingResponse<InformationShowDTO> findAllInfoUser(Pageable pageable, Long id) {
        List<InformationShowDTO> list = new ArrayList<>();
        informationRepository.findAllByUser(userRepository.findById(id).orElse(null)).filter(i -> {
            return i.getStatus().equals(InformationStatus.CUSTOMER);
        }).sorted((x, y) -> y.getId().compareTo(x.getId())).collect(Collectors.toList()).forEach(
                i -> {
                    list.add(modelMapper.map(i, InformationShowDTO.class));
                    i.setIsRead(true); // đánh dấu đã đọc
                }
        );
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        Page<InformationShowDTO> pageInfo = new PageImpl<>(list.subList(start, end), pageable, list.size());
        return PagingResponse.<InformationShowDTO>builder()
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
    public Optional<InformationShowDTO> findDetailInfo(Long id) {
        return informationRepository.findById(id).map(i -> {
            i.setIsRead(true);
            return modelMapper.map(i, InformationShowDTO.class);
        });
    }
}
