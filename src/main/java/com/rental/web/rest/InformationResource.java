package com.rental.web.rest;

import com.rental.repository.InformationRepository;
import com.rental.service.InformationService;
import com.rental.service.dto.InformationDTO;
import com.rental.service.dto.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/information")
public class InformationResource {
    @Autowired
    private InformationRepository informationRepository;
    @Autowired
    private InformationService informationService;

    @GetMapping("/getAll")
    public ResponseEntity<PagingResponse<InformationDTO>> getAllNotifications(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<InformationDTO> pageInfo = informationService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                PagingResponse.<InformationDTO>builder()
                        .page(pageInfo.getPageable().getPageNumber() + 1)
                        .size(pageInfo.getSize())
                        .totalPage(pageInfo.getTotalPages())
                        .totalItem(pageInfo.getTotalElements())
                        .contends(pageInfo.getContent())
                        .build()
        );
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<InformationDTO> getNotification(@PathVariable Long id) {
        if (!informationRepository.existsById(id))
            throw new IllegalArgumentException("Không thể tìm thấy thông báo có id là " + id + " trong dữ liệu ");
        return informationService.getOneInfo(id).map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
                .orElseThrow(() -> new IllegalArgumentException("Không thể tìm thấy bất kỳ thông báo đơn hàng trong dữ liệu"));
    }

    @GetMapping("/getCountIsRead")
    public ResponseEntity<Integer> getCountRead() {
        return ResponseEntity.status(HttpStatus.OK).body(informationService.findALLInfoIsRead());
    }
}
