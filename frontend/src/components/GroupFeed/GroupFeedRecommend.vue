<template>
  <splide :options="options">
    <splide-slide v-for="(op, idx) in c" :key="idx">
      추천 카드
      <!-- <RecommendCard /> -->
    </splide-slide>
  </splide>
</template>

<script>
import "@/assets/css/splide.css";
// import RecommendCard from "./RecommendCard.vue";

export default {
  name: "GroupFeedRecommend",
  // components: { RecommendCard },
  data() {
    return {
      c: [{}, {}, {}, {}, {}, {}, {}],
      options: {
        rewind: true,
        // width: 1000,
        // perPage: 2,
        // gap: "5px",
        type: "loop",
        padding: {
          right: "12.5rem",
          left: "12.5rem",
        },
        pagination: true,
        autoplay: true,
        interval: 5000,
        pauseOnHover: true,
        classes: { pagination: "splide__pagination" },
      },
    };
  },
};
</script>

<style scoped>
.splide {
  padding: 0 45px;
  margin: 15px 0;
}
</style>
