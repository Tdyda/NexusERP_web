//package pl.doublecodestudio.nexuserp.application.matarial_request.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import pl.doublecodestudio.nexuserp.application.matarial_request.query.GetMaterialRequestQuery;
//import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequest;
//import pl.doublecodestudio.nexuserp.domain.material_request.port.MaterialRequestRepository;
//import pl.doublecodestudio.nexuserp.interfaces.web.page.PageResult;
//
//@Service
//@RequiredArgsConstructor
//public class MaterialRequestService {
//
//    private final MaterialRequestRepository repo;
//
//    public PageResult<MaterialRequest> findPage(GetMaterialRequestQuery query) {
//        return repo.findAll(query.getPageable(), query.getFilters());
//    }
//}
//
//
