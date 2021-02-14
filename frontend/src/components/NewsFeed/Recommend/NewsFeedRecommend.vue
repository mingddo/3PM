<template>
  <div>
    <div class="banner-header">
      {{ title }}
    </div>
    <RecoSplide :Category="Category"/>
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
    };
  },
  mounted () {
    this.setRecoTitle ();
  },
  methods: {
    setRecoTitle () {
      if (this.Category == 4) {
        this.title = '취업 정보, 한눈👁‍🗨에 보기'
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
