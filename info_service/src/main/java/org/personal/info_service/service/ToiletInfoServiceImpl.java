package org.personal.info_service.service;

import lombok.RequiredArgsConstructor;
import org.personal.info_service.domain.ToiletInfo;
import org.personal.info_service.mapper.ToiletInfoMapper;
import org.personal.info_service.repository.ToiletInfoRepository;
import org.personal.info_service.request.RequestCreateInfo;
import org.personal.info_service.response.ToiletInfoResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToiletInfoServiceImpl implements ToiletInfoService {

    private final ToiletInfoRepository toiletInfoRepository;
    private final ToiletInfoMapper toiletInfoMapper;

    @Override
    public ToiletInfoResponse createToiletInfo(RequestCreateInfo toiletInfo) {

        toiletInfoRepository.findByToiletLocationId(toiletInfo.toiletLocationId())
                .ifPresent(existingInfo -> {
                    throw new DuplicateKeyException("화장실 정보가 이미 존재합니다.");
                });

        ToiletInfo requestInfo = toiletInfoMapper.convertRequestCreateInfoToToiletInfo(toiletInfo);

        toiletInfoRepository.save(requestInfo);

        return toiletInfoMapper.convertToiletInfoToResponse(requestInfo);
    }

    @Override
    public ToiletInfoResponse updateToiletInfo(RequestCreateInfo modifyToiletInfo) {

        ToiletInfo savedInfo = toiletInfoRepository.findByToiletLocationId(modifyToiletInfo.toiletLocationId())
                .orElseThrow(() -> new IllegalArgumentException("화장실 정보가 존재하지 않습니다."));

        ToiletInfo updateInfo = toiletInfoMapper.convertRequestCreateInfoToToiletInfo(modifyToiletInfo);
        updateInfo.updateInfoId(savedInfo.getToiletInfoId());
        toiletInfoRepository.save(updateInfo);

        return toiletInfoMapper.convertToiletInfoToResponse(updateInfo);
    }

    @Override
    public void deleteToiletinfo(Long locationId) {
        ToiletInfo savedInfo = toiletInfoRepository.findByToiletLocationId(locationId)
                .orElseThrow(() -> new IllegalArgumentException("화장실 정보가 존재하지 않습니다."));

        savedInfo.updateDeleted(true);
        toiletInfoRepository.save(savedInfo);
    }

    @Override
    public ToiletInfoResponse getToiletInfo(Long locationId) {
        ToiletInfo info = toiletInfoRepository.findToiletInfo(locationId);

        if(info == null){
            throw new IllegalArgumentException("화장실 정보가 없습니다.");
        }

        return toiletInfoMapper.convertToiletInfoToResponse(info);
    }

    @Override
    public List<ToiletInfoResponse> getToiletInfoList(List<Long> idList) {

        List<ToiletInfo> infoList = toiletInfoRepository.findToiletInfoList(idList);

        return infoList.stream()
                .map(toiletInfoMapper::convertToiletInfoToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ToiletInfoResponse> getDisabledToiletList() {
        List<ToiletInfo> infoList = toiletInfoRepository.findAllWithDisabledToilets();

        return infoList.stream()
                .map(toiletInfoMapper::convertToiletInfoToResponse)
                .collect(Collectors.toList());
    }


}
