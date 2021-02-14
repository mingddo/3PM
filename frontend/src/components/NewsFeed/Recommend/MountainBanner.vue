<template>
  <div>
    <div class="banner-header">
      요즘 가장 핫한 도시 "{{ placeName }}" 으로 떠납니다
    </div>
    <div class="reco-body">
      <NewsFeedMap :latitude="latitude" :longitude="longitude"/>
    </div>
    <div>
      관련된 N개의 피드들이 있습니다.
    </div>
  </div>
</template>

<script>
import NewsFeedMap from '../Common/NewsFeedMap.vue';
export default {
  components: { NewsFeedMap },
  name: 'MountainBanner',
  data() {
    return {
      placeName: '대전',
      latitude: 36.353793856820566,
      longitude:127.33999670291793,
    };
  },
  mounted() {
    
  },
  methods: {
    
  },
};
</script>

<style lang="scss" scoped>
.reco-body {
  width: 100%;
}
</style>