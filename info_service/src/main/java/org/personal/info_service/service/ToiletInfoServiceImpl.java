package org.personal.info_service.service;

import org.personal.info_service.domain.ToiletInfo;
import org.personal.info_service.mapper.ToiletInfoMapper;
import org.personal.info_service.repository.ToiletInfoRepository;
import org.personal.info_service.request.RequestCreateInfo;
import org.personal.info_service.response.ToiletInfoResponse;
import org.springframework.stereotype.Service;

@Service
public class ToiletInfoServiceImpl implements ToiletInfoService {

    private final ToiletInfoRepository toiletInfoRepository;
    private final ToiletInfoMapper toiletInfoMapper;

    public ToiletInfoServiceImpl(ToiletInfoRepository toiletInfoRepository, ToiletInfoMapper toiletInfoMapper) {
        this.toiletInfoRepository = toiletInfoRepository;
        this.toiletInfoMapper = toiletInfoMapper;
    }

    @Override
    public ToiletInfoResponse createToiletInfo(RequestCreateInfo toiletInfo) {

        ToiletInfo requestInfo = toiletInfoMapper.convertRequestCreateInfoToToiletInfo(toiletInfo);

        toiletInfoRepository.save(requestInfo);

        return toiletInfoMapper.convertToiletInfoToResponse(requestInfo);
    }

    @Override
    public ToiletInfoResponse updateToiletInfo(RequestCreateInfo modifyToiletInfo) {

        ToiletInfo savedInfo = toiletInfoRepository.findByToiletLocationId(modifyToiletInfo.toiletLocationId());

        if(savedInfo == null){
            throw new IllegalArgumentException("화장실 정보가 존재하지 않습니다.");
        }

        ToiletInfo updateInfo = toiletInfoMapper.convertRequestCreateInfoToToiletInfo(modifyToiletInfo);
        updateInfo.setToiletInfoId(savedInfo.getToiletInfoId());
        toiletInfoRepository.save(updateInfo);

        return toiletInfoMapper.convertToiletInfoToResponse(updateInfo);
    }

    @Override
    public void deleteToiletinfo(Long locationId) {
        ToiletInfo savedInfo = toiletInfoRepository.findByToiletLocationId(locationId);

        if(savedInfo == null){
            throw new IllegalArgumentException();
        }

        savedInfo.setDeleted(true);
        toiletInfoRepository.save(savedInfo);
    }
}
