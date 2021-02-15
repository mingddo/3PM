<template>
  <div>
    <div class="banner-header">
      {{ title }}
    </div>
    <RecoSplide :Category="Category" :placeName="placeName" @setPlace="setPlace"/>
  </div>
</template>

<script>
import RecoSplide from './RecoSplide.vue';

export default {
  components: {
    RecoSplide
  },
  props: {
    Category: Number,
  },
  data() {
    return {
      title: null,
      placeName: null,
    };
  },
  mounted () {
    this.setRecoTitle ();
  },
  methods: {
    setPlace (place) {
      this.placeName = place
    },
    setRecoTitle () {
      if (this.Category == 4) {
        this.title = '취업 정보, 한눈👁‍🗨에 보기'
      } else if (this.Category == 2) {
        this.title = `핵인싸`
      } else if (this.Category == 3) {
        this.title = `청산별곡`
      } else if (this.Categpry == 1) {
        this.title = `베스트`
      }
    },
  },
};
</script>

<style scoped>
/* .splide {
  padding: 0 45px;
  margin: 15px 0;
} */
.banner-header {
  padding-top : 40px; 
  padding-bottom:40px; 
  text-align:center;
  font-size: 24px;
}

</style>
