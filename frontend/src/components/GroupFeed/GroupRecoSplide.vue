<template>
  <splide :options="options">
    <splide-slide v-for="(op, idx) in c" :key="idx">
      <GroupRecommendCard :op="op"/>
    </splide-slide>
  </splide>
</template>

<script>
import "@/assets/css/splide.css";
import GroupRecommendCard from './GroupRecommendCard.vue';
export default {
  components: { GroupRecommendCard },
  name: 'GroupRecoSplide',
  data() {
    return {
      c: [{}, {}, {}],
      options: {
        rewind: true,
        width: 2000,
        perPage: 1,
        gap: "5px",
        type: "loop",
        padding: {
          right: "12.px",
          left: "12.px",
        },
        pagination: true,
        autoplay: true,
        interval: 5000,
        pauseOnHover: true,
        classes: { pagination: "splide__pagination" },
      },
    };
  },
  mounted() {
    
  },
  methods: {
    
  },
};
</script>

<style lang="scss" scoped>

</style>

