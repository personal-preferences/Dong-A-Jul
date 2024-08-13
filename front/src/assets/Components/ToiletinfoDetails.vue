<template>
  <div class="toilet-details">
    <h2>부가정보</h2>

    <div class="info-group">
      <h3>시설 정보</h3>
      <div>
        <label>개방시간:</label>
        <span>{{ toiletInfo.toiletInfoOpeningHours || '정보 없음' }}</span>
      </div>
      <div>
        <label>개방시간상세:</label>
        <span>{{ toiletInfo.toiletInfoOpeningHoursDetails || '정보 없음' }}</span>
      </div>
      <div>
        <label>관리기관명:</label>
        <span>{{ toiletInfo.toiletInfoManagementAgency || '정보 없음' }}</span>
      </div>
      <div>
        <label>전화번호:</label>
        <span>{{ toiletInfo.toiletInfoPhoneNumber || '정보 없음' }}</span>
      </div>
      <div>
        <label>설치연월:</label>
        <span>{{ toiletInfo.toiletInfoInstallationYearMonth || '정보 없음' }}</span>
      </div>
      <div>
        <label>화장실소유구분:</label>
        <span>{{ toiletInfo.toiletInfoOwnershipType || '정보 없음' }}</span>
      </div>
      <div>
        <label>오물처리방식:</label>
        <span>{{ toiletInfo.toiletInfoWasteDisposalMethod || '정보 없음' }}</span>
      </div>
    </div>

    <div class="info-group">
      <h3>안전 및 시설</h3>
      <div>
        <label>안전관리시설설치대상여부:</label>
        <span>{{
          toiletInfo.toiletInfoSafetyFacilityInstallationIsRequired ? '예' : '아니오'
        }}</span>
      </div>
      <div>
        <label>비상벨설치여부:</label>
        <span>{{ toiletInfo.toiletInfoEmergencyBellIsInstalled ? '예' : '아니오' }}</span>
      </div>
      <div>
        <label>비상벨설치장소:</label>
        <span>{{ toiletInfo.toiletInfoEmergencyBellLocation || '정보 없음' }}</span>
      </div>
      <div>
        <label>화장실입구CCTV설치유무:</label>
        <span>{{ toiletInfo.toiletInfoEntranceCCTVIsInstalled ? '예' : '아니오' }}</span>
      </div>
      <div>
        <label>기저귀교환대유무:</label>
        <span>{{ toiletInfo.toiletInfoDiaperChangingTableIsAvailable ? '예' : '아니오' }}</span>
      </div>
      <div>
        <label>기저귀교환대장소:</label>
        <span>{{ toiletInfo.toiletInfoDiaperChangingTableLocation || '정보 없음' }}</span>
      </div>
    </div>

    <div class="info-group">
      <h3>화장실 수</h3>
      <div>
        <label>남성용 대소변기 수:</label>
        <span>{{ getMaleToiletsNumber() }}</span>
      </div>
      <div>
        <label>여성용 대변기 수:</label>
        <span>{{ getFemaleToiletsNumber() }}</span>
      </div>
      <div>
        <label>어린이용:</label>
        <span>{{ getChildrenToiletsNumber() }}</span>
      </div>
      <div>
        <label>장애인용:</label>
        <span>{{ getDisabledToiletsNumber() }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import axios from 'axios'

const toiletInfo = ref(null)
const locationId = 1

onMounted(async () => {
  await searchInfo()
})

const searchInfo = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/info/${locationId}`)
    toiletInfo.value = response.data
  } catch (error) {
    console.error('정보 조회에 실패했습니다:', error)
    alert('정보 조회에 실패했습니다. 다시 시도해주세요.')
  }
}

// 남성용 대소변기 수 계산
const getMaleToiletsNumber = () => {
  const maleToilets = toiletInfo.value.toiletInfoMaleToiletsNumber
  const maleUrinals = toiletInfo.value.toiletInfoMaleUrinalsNumber
  if (maleToilets === null && maleUrinals === null) return '정보 없음'
  return (maleToilets || 0) + (maleUrinals || 0)
}

// 여성용 대변기 수 계산
const getFemaleToiletsNumber = () => {
  const femaleToilets = toiletInfo.value.toiletInfoFemaleToiletsNumber
  return femaleToilets === null ? '정보 없음' : femaleToilets
}

// 어린이용 대변기 수 계산
const getChildrenToiletsNumber = () => {
  const maleChildToilets =
    (toiletInfo.value.toiletInfoMaleChildToiletsNumber || 0) +
    (toiletInfo.value.toiletInfoMaleChildUrinalsNumber || 0)
  const femaleChildToilets = toiletInfo.value.toiletInfoFemaleChildToiletsNumber || 0
  const total = maleChildToilets + femaleChildToilets
  return total === 0 ? '정보 없음' : total
}

// 장애인용 대변기 수 계산
const getDisabledToiletsNumber = () => {
  const maleDisabledToilets = toiletInfo.value.toiletInfoMaleDisabledToiletsNumber
  const femaleDisabledToilets = toiletInfo.value.toiletInfoFemaleDisabledToiletsNumber
  if (maleDisabledToilets === null && femaleDisabledToilets === null) return '정보 없음'
  return (maleDisabledToilets || 0) + (femaleDisabledToilets || 0)
}
</script>
