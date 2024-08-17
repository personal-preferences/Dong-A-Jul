<template>
  <div>
    <h2>Register Toilet</h2>

    <!-- 네이버 지도 검색 -->
    <div>
      <input v-model="searchQuery" placeholder="Search location" />
      <button @click="searchLocation">Search</button>
    </div>

    <!-- 검색 결과 리스트 -->
    <div v-if="searchResults.length">
      <h3>Search Results</h3>
      <ul>
        <li v-for="(place, index) in searchResults" :key="index" @click="selectPlace(place)">
          {{ place.name }} - {{ place.roadAddress || place.jibunAddress }}
        </li>
      </ul>
    </div>

    <!-- 선택한 위치 정보 표시 -->
    <div v-if="selectedPlace">
      <h3>Selected Location</h3>
      <p><strong>Road Name Address:</strong> {{ selectedPlace.roadAddress || 'N/A' }}</p>
      <p><strong>Number Address:</strong> {{ selectedPlace.jibunAddress }}</p>
      <p><strong>Latitude:</strong> {{ selectedPlace.y }}</p>
      <p><strong>Longitude:</strong> {{ selectedPlace.x }}</p>
    </div>

    <!-- 화장실 등록 폼 -->
    <form @submit.prevent="submitForm">
      <div>
        <label for="managementAgency">Management Agency:</label>
        <input id="managementAgency" v-model="form.managementAgency" required />
      </div>
      <div>
        <label for="phoneNumber">Phone Number:</label>
        <input id="phoneNumber" v-model="form.phoneNumber" required />
      </div>
      <div>
        <label for="openingHours">Opening Hours:</label>
        <input id="openingHours" v-model="form.openingHours" required />
      </div>
      <div>
        <label for="openingHoursDetails">Opening Hours Details:</label>
        <input id="openingHoursDetails" v-model="form.openingHoursDetails" />
      </div>
      <div>
        <label for="installationYearMonth">Installation Year/Month:</label>
        <input id="installationYearMonth" v-model="form.installationYearMonth" required />
      </div>
      <div>
        <label for="ownershipType">Ownership Type:</label>
        <input id="ownershipType" v-model="form.ownershipType" required />
      </div>
      <div>
        <label for="wasteDisposalMethod">Waste Disposal Method:</label>
        <input id="wasteDisposalMethod" v-model="form.wasteDisposalMethod" required />
      </div>
      <div>
        <label for="safetyFacilityInstallationIsRequired">Safety Facility Installation Required:</label>
        <input type="checkbox" id="safetyFacilityInstallationIsRequired" v-model="form.safetyFacilityInstallationIsRequired" />
      </div>
      <div>
        <label for="emergencyBellIsInstalled">Emergency Bell Installed:</label>
        <input type="checkbox" id="emergencyBellIsInstalled" v-model="form.emergencyBellIsInstalled" />
      </div>
      <div>
        <label for="emergencyBellLocation">Emergency Bell Location:</label>
        <input id="emergencyBellLocation" v-model="form.emergencyBellLocation" />
      </div>
      <div>
        <label for="entranceCctvIsInstalled">Entrance CCTV Installed:</label>
        <input type="checkbox" id="entranceCctvIsInstalled" v-model="form.entranceCctvIsInstalled" />
      </div>
      <div>
        <label for="diaperChangingTableIsAvailable">Diaper Changing Table Available:</label>
        <input type="checkbox" id="diaperChangingTableIsAvailable" v-model="form.diaperChangingTableIsAvailable" />
      </div>
      <div>
        <label for="diaperChangingTableLocation">Diaper Changing Table Location:</label>
        <input id="diaperChangingTableLocation" v-model="form.diaperChangingTableLocation" />
      </div>
      <div>
        <label for="maleToiletsNumber">Male Toilets Number:</label>
        <input type="number" id="maleToiletsNumber" v-model="form.maleToiletsNumber" required />
      </div>
      <div>
        <label for="maleUrinalsNumber">Male Urinals Number:</label>
        <input type="number" id="maleUrinalsNumber" v-model="form.maleUrinalsNumber" required />
      </div>
      <div>
        <label for="maleDisabledToiletsNumber">Male Disabled Toilets Number:</label>
        <input type="number" id="maleDisabledToiletsNumber" v-model="form.maleDisabledToiletsNumber" required />
      </div>
      <div>
        <label for="maleDisabledUrinalsNumber">Male Disabled Urinals Number:</label>
        <input type="number" id="maleDisabledUrinalsNumber" v-model="form.maleDisabledUrinalsNumber" required />
      </div>
      <div>
        <label for="maleChildToiletsNumber">Male Child Toilets Number:</label>
        <input type="number" id="maleChildToiletsNumber" v-model="form.maleChildToiletsNumber" required />
      </div>
      <div>
        <label for="maleChildUrinalsNumber">Male Child Urinals Number:</label>
        <input type="number" id="maleChildUrinalsNumber" v-model="form.maleChildUrinalsNumber" required />
      </div>
      <div>
        <label for="femaleToiletsNumber">Female Toilets Number:</label>
        <input type="number" id="femaleToiletsNumber" v-model="form.femaleToiletsNumber" required />
      </div>
      <div>
        <label for="femaleDisabledToiletsNumber">Female Disabled Toilets Number:</label>
        <input type="number" id="femaleDisabledToiletsNumber" v-model="form.femaleDisabledToiletsNumber" required />
      </div>
      <div>
        <label for="femaleChildToiletsNumber">Female Child Toilets Number:</label>
        <input type="number" id="femaleChildToiletsNumber" v-model="form.femaleChildToiletsNumber" required />
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
      searchQuery: '',
      searchResults: [],
      selectedPlace: null,
      form: {
        managementAgency: '',
        phoneNumber: '',
        openingHours: '',
        openingHoursDetails: '',
        installationYearMonth: '',
        ownershipType: '',
        wasteDisposalMethod: '',
        safetyFacilityInstallationIsRequired: false,
        emergencyBellIsInstalled: false,
        emergencyBellLocation: '',
        entranceCctvIsInstalled: false,
        diaperChangingTableIsAvailable: false,
        diaperChangingTableLocation: '',
        maleToiletsNumber: 0,
        maleUrinalsNumber: 0,
        maleDisabledToiletsNumber: 0,
        maleDisabledUrinalsNumber: 0,
        maleChildToiletsNumber: 0,
        maleChildUrinalsNumber: 0,
        femaleToiletsNumber: 0,
        femaleDisabledToiletsNumber: 0,
        femaleChildToiletsNumber: 0,
      },
    };
  },
  methods: {
    async searchLocation() {
      try {
        const response = await axios.get(`https://naveropenapi.apigw.ntruss.com/map-place/v1/search`, {
          params: {
            query: this.searchQuery,
            coordinate: '127.1054328,37.3595963', // 기준 좌표 (위도, 경도)
          },
          headers: {
            'X-NCP-APIGW-API-KEY-ID': 'YOUR_NAVER_API_KEY_ID',
            'X-NCP-APIGW-API-KEY': 'YOUR_NAVER_API_KEY_SECRET'
          }
        });
        this.searchResults = response.data.places;
      } catch (error) {
        console.error('Error searching location:', error);
      }
    },
    selectPlace(place) {
      this.selectedPlace = place;
      this.form.roadNameAddress = place.roadAddress || '';
      this.form.numberAddress = place.jibunAddress || '';
      this.form.latitude = place.y;
      this.form.longitude = place.x;
    },
    async submitForm() {
      try {
        const submitData = {
          ...this.form,
          toiletRegistRoadNameAddress: this.form.roadNameAddress,
          toiletRegistNumberAddress: this.form.numberAddress,
          toiletRegistLatitude: this.form.latitude,
          toiletRegistLongitude: this.form.longitude,
        };

        const response = await axios.post('http://localhost:8765/toiletregists', submitData);
        console.log('Registration successful:', response.data);
      } catch (error) {
        console.error('Error submitting form:', error);
      }
    }
  }
};
</script>
