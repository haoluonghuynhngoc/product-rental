package com.rental.web.rest;

import com.rental.domain.enums.InformationStatus;
import com.rental.repository.InformationRepository;
import com.rental.service.InformationService;
import com.rental.service.dto.InformationDTO;
import com.rental.service.dto.PagingResponse;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(deprecated = true)
    public ResponseEntity<PagingResponse<InformationDTO>> getAllInfoAdmin(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(informationService.findAllInfoAdmin(pageable));
    }
    @GetMapping("/getAllUser/{id}")
    @Operation(deprecated = true)
    public ResponseEntity<PagingResponse<InformationDTO>> getAllInfoUser(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable,@PathVariable  Long id) {
        if (!informationRepository.existsById(id))
            throw new IllegalArgumentException("Không thể tìm thấy thông báo đơn hàng có id :" + id);
        return ResponseEntity.status(HttpStatus.OK).body(informationService.findAllInfoUser(pageable,id));
    }

    @GetMapping("/getCountIsReadByUser/{id}")
    @Operation(deprecated = true)
    public ResponseEntity<Integer> getCountUserRead(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                informationService.findAllInfoIsReadByUser(id, InformationStatus.CUSTOMER));
    }
    @GetMapping("/getCountIsReadByAdmin")
    @Operation(deprecated = true)
    public ResponseEntity<Integer> getCountAdminRead() {
        return ResponseEntity.status(HttpStatus.OK).body(
                informationService.findAllInfoIsReadByUser(1L,InformationStatus.CENSORSHIP));
    }
    @GetMapping("/getDetailInfo/{id}")
    @Operation(deprecated = true)
    public ResponseEntity<InformationDTO> getInfoById(@PathVariable Long id) {
        if (!informationRepository.existsById(id))
            throw new IllegalArgumentException("Không thể tìm thấy thông báo đơn hàng có id :" + id);
        return ResponseEntity.status(HttpStatus.OK).body(
                informationService.findDetailInfo(id).orElse(null));
    }
}
