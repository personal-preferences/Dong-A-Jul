<template>
  <div>
    <h2>Register Toilet</h2>

    <!-- 화장실 등록 폼 -->
    <form @submit.prevent="submitForm">
      <div>
        <label for="toiletRegistToiletName">Toilet Name:</label>
        <input id="toiletRegistToiletName" v-model="form.toiletRegistToiletName" required />
      </div>
      <div>
        <label for="toiletRegistRoadNameAddress">Road Name Address:</label>
        <input id="toiletRegistRoadNameAddress" v-model="form.toiletRegistRoadNameAddress" required />
      </div>
      <div>
        <label for="toiletRegistNumberAddress">Number Address:</label>
        <input id="toiletRegistNumberAddress" v-model="form.toiletRegistNumberAddress" required />
      </div>
      <div>
        <label for="toiletRegistLatitude">Latitude:</label>
        <input type="number" step="any" id="toiletRegistLatitude" v-model="form.toiletRegistLatitude" required />
      </div>
      <div>
        <label for="toiletRegistLongitude">Longitude:</label>
        <input type="number" step="any" id="toiletRegistLongitude" v-model="form.toiletRegistLongitude" required />
      </div>
      <!-- 선택 입력 필드들 -->
      <div>
        <label for="toiletRegistManagementAgency">Management Agency:</label>
        <input id="toiletRegistManagementAgency" v-model="form.toiletRegistManagementAgency" />
      </div>
      <div>
        <label for="toiletRegistPhoneNumber">Phone Number:</label>
        <input id="toiletRegistPhoneNumber" v-model="form.toiletRegistPhoneNumber" />
      </div>
      <div>
        <label for="toiletRegistOpeningHours">Opening Hours:</label>
        <input id="toiletRegistOpeningHours" v-model="form.toiletRegistOpeningHours" />
      </div>
      <div>
        <label for="toiletRegistOpeningHoursDetails">Opening Hours Details:</label>
        <input id="toiletRegistOpeningHoursDetails" v-model="form.toiletRegistOpeningHoursDetails" />
      </div>
      <div>
        <label for="toiletRegistInstallationYearMonth">Installation Year/Month:</label>
        <input id="toiletRegistInstallationYearMonth" v-model="form.toiletRegistInstallationYearMonth" />
      </div>
      <div>
        <label for="toiletRegistOwnershipType">Ownership Type:</label>
        <input id="toiletRegistOwnershipType" v-model="form.toiletRegistOwnershipType" />
      </div>
      <div>
        <label for="toiletRegistWasteDisposalMethod">Waste Disposal Method:</label>
        <input id="toiletRegistWasteDisposalMethod" v-model="form.toiletRegistWasteDisposalMethod" />
      </div>
      <div>
        <label for="toiletInfoSafetyFacilityInstallationIsRequired">Safety Facility Installation Required:</label>
        <input type="checkbox" id="toiletInfoSafetyFacilityInstallationIsRequired" v-model="form.toiletInfoSafetyFacilityInstallationIsRequired" />
      </div>
      <div>
        <label for="toiletRegistEmergencyBellIsInstalled">Emergency Bell Installed:</label>
        <input type="checkbox" id="toiletRegistEmergencyBellIsInstalled" v-model="form.toiletRegistEmergencyBellIsInstalled" />
      </div>
      <div>
        <label for="toiletRegistEmergencyBellLocation">Emergency Bell Location:</label>
        <input id="toiletRegistEmergencyBellLocation" v-model="form.toiletRegistEmergencyBellLocation" />
      </div>
      <div>
        <label for="toiletRegistEntranceCctvIsInstalled">Entrance CCTV Installed:</label>
        <input type="checkbox" id="toiletRegistEntranceCctvIsInstalled" v-model="form.toiletRegistEntranceCctvIsInstalled" />
      </div>
      <div>
        <label for="toiletRegistDiaperChangingTableIsAvailable">Diaper Changing Table Available:</label>
        <input type="checkbox" id="toiletRegistDiaperChangingTableIsAvailable" v-model="form.toiletRegistDiaperChangingTableIsAvailable" />
      </div>
      <div>
        <label for="toiletRegistDiaperChangingTableLocation">Diaper Changing Table Location:</label>
        <input id="toiletRegistDiaperChangingTableLocation" v-model="form.toiletRegistDiaperChangingTableLocation" />
      </div>
      <div>
        <label for="toiletRegistMaleToiletsNumber">Male Toilets Number:</label>
        <input type="number" id="toiletRegistMaleToiletsNumber" v-model="form.toiletRegistMaleToiletsNumber" />
      </div>
      <div>
        <label for="toiletRegistMaleUrinalsNumber">Male Urinals Number:</label>
        <input type="number" id="toiletRegistMaleUrinalsNumber" v-model="form.toiletRegistMaleUrinalsNumber" />
      </div>
      <div>
        <label for="toiletRegistMaleDisabledToiletsNumber">Male Disabled Toilets Number:</label>
        <input type="number" id="toiletRegistMaleDisabledToiletsNumber" v-model="form.toiletRegistMaleDisabledToiletsNumber" />
      </div>
      <div>
        <label for="toiletRegistMaleDisabledUrinalsNumber">Male Disabled Urinals Number:</label>
        <input type="number" id="toiletRegistMaleDisabledUrinalsNumber" v-model="form.toiletRegistMaleDisabledUrinalsNumber" />
      </div>
      <div>
        <label for="toiletRegistMaleChildToiletsNumber">Male Child Toilets Number:</label>
        <input type="number" id="toiletRegistMaleChildToiletsNumber" v-model="form.toiletRegistMaleChildToiletsNumber" />
      </div>
      <div>
        <label for="toiletRegistMaleChildUrinalsNumber">Male Child Urinals Number:</label>
        <input type="number" id="toiletRegistMaleChildUrinalsNumber" v-model="form.toiletRegistMaleChildUrinalsNumber" />
      </div>
      <div>
        <label for="toiletRegistFemaleToiletsNumber">Female Toilets Number:</label>
        <input type="number" id="toiletRegistFemaleToiletsNumber" v-model="form.toiletRegistFemaleToiletsNumber" />
      </div>
      <div>
        <label for="toiletRegistFemaleDisabledToiletsNumber">Female Disabled Toilets Number:</label>
        <input type="number" id="toiletRegistFemaleDisabledToiletsNumber" v-model="form.toiletRegistFemaleDisabledToiletsNumber" />
      </div>
      <div>
        <label for="toiletRegistFemaleChildToiletsNumber">Female Child Toilets Number:</label>
        <input type="number" id="toiletRegistFemaleChildToiletsNumber" v-model="form.toiletRegistFemaleChildToiletsNumber" />
      </div>
      <div>
        <label for="userEmail">User Email:</label>
        <input type="email" id="userEmail" v-model="form.userEmail" required />
      </div>

      <button type="submit">Submit</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      form: {
        toiletRegistLatitude: '',
        toiletRegistLongitude: '',
        toiletRegistToiletName: '',
        toiletRegistRoadNameAddress: '',
        toiletRegistNumberAddress: '',
        toiletRegistManagementAgency: '',
        toiletRegistPhoneNumber: '',
        toiletRegistOpeningHours: '',
        toiletRegistOpeningHoursDetails: '',
        toiletRegistInstallationYearMonth: '',
        toiletRegistOwnershipType: '',
        toiletRegistWasteDisposalMethod: '',
        toiletInfoSafetyFacilityInstallationIsRequired: false,
        toiletRegistEmergencyBellIsInstalled: false,
        toiletRegistEmergencyBellLocation: '',
        toiletRegistEntranceCctvIsInstalled: false,
        toiletRegistDiaperChangingTableIsAvailable: false,
        toiletRegistDiaperChangingTableLocation: '',
        toiletRegistMaleToiletsNumber: null,
        toiletRegistMaleUrinalsNumber: null,
        toiletRegistMaleDisabledToiletsNumber: null,
        toiletRegistMaleDisabledUrinalsNumber: null,
        toiletRegistMaleChildToiletsNumber: null,
        toiletRegistMaleChildUrinalsNumber: null,
        toiletRegistFemaleToiletsNumber: null,
        toiletRegistFemaleDisabledToiletsNumber: null,
        toiletRegistFemaleChildToiletsNumber: null,
        userEmail: ''
      },
    };
  },
  methods: {
    async submitForm() {
      try {
        console.log('Submitting data:', this.form);  // 데이터 확인
        const response = await axios.post(
          'http://localhost:8765/toiletregists',
          JSON.stringify(this.form),
          {
            headers: {
              'Content-Type': 'application/json',
              'X-USER-ID': 'user123',
            },
          }
        );
        console.log('Registration successful:', response.data);
      } catch (error) {
        console.error('Error submitting form:', error);
      }
    }
  }
};
</script>
