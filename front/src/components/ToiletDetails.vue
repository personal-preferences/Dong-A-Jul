<template>
  <div>
    <h2>Toilet Details</h2>
    <div v-if="toilet">
      <p><strong>Toilet Name:</strong> {{ toilet.toiletRegistToiletName }}</p>
      <p><strong>Road Name Address:</strong> {{ toilet.toiletRegistRoadNameAddress }}</p>
      <p><strong>Number Address:</strong> {{ toilet.toiletRegistNumberAddress }}</p>
      <p><strong>Latitude:</strong> {{ toilet.toiletRegistLatitude }}</p>
      <p><strong>Longitude:</strong> {{ toilet.toiletRegistLongitude }}</p>
      <p><strong>Management Agency:</strong> {{ toilet.toiletRegistManagementAgency }}</p>
      <p><strong>Phone Number:</strong> {{ toilet.toiletRegistPhoneNumber }}</p>
      <p><strong>Opening Hours:</strong> {{ toilet.toiletRegistOpeningHours }}</p>
      <p><strong>Opening Hours Details:</strong> {{ toilet.toiletRegistOpeningHoursDetails }}</p>
      <p><strong>Installation Year/Month:</strong> {{ toilet.toiletRegistInstallationYearMonth }}</p>
      <p><strong>Ownership Type:</strong> {{ toilet.toiletRegistOwnershipType }}</p>
      <p><strong>Waste Disposal Method:</strong> {{ toilet.toiletRegistWasteDisposalMethod }}</p>
      <p><strong>Safety Facility Installation Required:</strong> {{ toilet.toiletInfoSafetyFacilityInstallationIsRequired ? 'Yes' : 'No' }}</p>
      <p><strong>Emergency Bell Installed:</strong> {{ toilet.toiletRegistEmergencyBellIsInstalled ? 'Yes' : 'No' }}</p>
      <p><strong>Emergency Bell Location:</strong> {{ toilet.toiletRegistEmergencyBellLocation }}</p>
      <p><strong>Entrance CCTV Installed:</strong> {{ toilet.toiletRegistEntranceCctvIsInstalled ? 'Yes' : 'No' }}</p>
      <p><strong>Diaper Changing Table Available:</strong> {{ toilet.toiletRegistDiaperChangingTableIsAvailable ? 'Yes' : 'No' }}</p>
      <p><strong>Diaper Changing Table Location:</strong> {{ toilet.toiletRegistDiaperChangingTableLocation }}</p>
      <p><strong>Male Toilets Number:</strong> {{ toilet.toiletRegistMaleToiletsNumber }}</p>
      <p><strong>Male Urinals Number:</strong> {{ toilet.toiletRegistMaleUrinalsNumber }}</p>
      <p><strong>Male Disabled Toilets Number:</strong> {{ toilet.toiletRegistMaleDisabledToiletsNumber }}</p>
      <p><strong>Male Disabled Urinals Number:</strong> {{ toilet.toiletRegistMaleDisabledUrinalsNumber }}</p>
      <p><strong>Male Child Toilets Number:</strong> {{ toilet.toiletRegistMaleChildToiletsNumber }}</p>
      <p><strong>Male Child Urinals Number:</strong> {{ toilet.toiletRegistMaleChildUrinalsNumber }}</p>
      <p><strong>Female Toilets Number:</strong> {{ toilet.toiletRegistFemaleToiletsNumber }}</p>
      <p><strong>Female Disabled Toilets Number:</strong> {{ toilet.toiletRegistFemaleDisabledToiletsNumber }}</p>
      <p><strong>Female Child Toilets Number:</strong> {{ toilet.toiletRegistFemaleChildToiletsNumber }}</p>
      <p><strong>User Email:</strong> {{ toilet.userEmail }}</p>
      <p><strong>Confirmed Date:</strong> {{ toilet.toiletRegistConfirmedDate || 'Not Confirmed' }}</p>

      <div v-if="toilet.toiletRegistConfirmedDate === null">
        <button @click="approveToilet(true)">Approve</button>
        <button @click="approveToilet(false)">Reject</button>
      </div>
    </div>
    <div v-else>
      <p>Loading...</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  props: ['id'],
  data() {
    return {
      toilet: null
    }
  },
  methods: {
    async fetchToiletDetails() {
      try {
        const response = await axios.get(`http://localhost:8765/approves/${this.id}`)
        this.toilet = response.data
      } catch (error) {
        console.error('Error fetching toilet details:', error)
      }
    },
    async approveToilet(isApproved) {
      try {
        const request = {
          toiletRegistId: Number(this.id), // 숫자형으로 변환
          isApproved: isApproved
        }
        const headers = {
          'X-USER-ID': 'admin' // 필요한 경우 사용자 ID 헤더를 설정합니다.
        }
        const response = await axios.patch(`http://localhost:8765/approves`, request, { headers })
        console.log(`Toilet ${this.id} ${isApproved ? 'approved' : 'rejected'}`, response.data)
        // 승인 또는 반려 후 처리 (예: 메시지 표시 또는 페이지 리프레시)
      } catch (error) {
        console.error(`Error ${isApproved ? 'approving' : 'rejecting'} toilet:`, error)
      }
    }
  },
  mounted() {
    this.fetchToiletDetails()
  }
}
</script>

<style scoped>
button {
  margin: 10px;
}
</style>
