<template>
  <div>
    <div class="feed-detail-map">
      <div class="feed-detail-map-name"> <i class="fas fa-map-marker-alt"></i> <span class="feed-detail-map-placeName">{{ placeName }}</span> <span>{{ address }}</span> <button @click="showMap"> {{ aboutMap }} </button> </div>
      <NewsFeedMap 
        v-if="aboutMap =='지도 닫기'"
        :latitude="latitude"
        :longitude="longitude"  
      />
    </div>    
  </div>
</template>

<script>
import NewsFeedMap from '../Common/NewsFeedMap.vue';
export default {
  components: { NewsFeedMap },
  name: 'Location',
  props: {
    address: String,
    placeName: String,
    latitude: Number,
    longitude: Number
  },
  data() {
    return {
      aboutMap : '지도 열기'
    };
  },
  mounted() {
    
  },
  methods: {
    showMap () {
      if (this.aboutMap == '지도 열기') {
        this.aboutMap = '지도 닫기'
      } else {
        this.aboutMap = '지도 열기'
      }
    },
  },
};
</script>

<style lang="scss" scoped>

</style>